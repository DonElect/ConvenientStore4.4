package com.store.implementations;

import com.store.models.Cashier;
import com.store.models.CustomerModel;
import com.store.models.ProductDetails;
import com.store.services.CATEGORY;
import com.store.services.CashierServices;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.*;

@NoArgsConstructor
public class CashierImp implements CashierServices {
    Products products = new Products();
    private Cashier cashier;
    @Getter
    public static Queue<CustomerModel> priorityCart = new PriorityQueue<>();
    @Getter
    public static Queue<CustomerModel> fifoCart = new LinkedList<>();

    private final String lock1 = "FIFOLock";
    private final String lock2 = "PriorityLock";

    public CashierImp(Cashier cashier) {
        this.cashier = cashier;
    }

    @Override
    public String sellByFIFO() {
        if (cashier.isHired()) {
            String result = "";
            if (fifoCart.isEmpty())
                return "";
            CustomerModel newFIFO = fifoCart.poll();
            synchronized (lock1) {
                if (dispenseReceipt(newFIFO.getFullName(), newFIFO.getCart())) {
                    System.out.println("Thank You for Shopping with us. "+Thread.currentThread().getName());
                    System.out.println();
                    result = "SUCCESSFUL";
                } else {
                    result = "UNSUCCESSFUL";
                }
            }
            return result;

        } else {
            System.out.println("You are no longer a staff here. And so cannot dispense receipt");
            return "NOT-A-STAFF";
        }
    }

    @Override
    public String sellByPriority() {
        if (cashier.isHired()) {
            String result = "";
            if (priorityCart.isEmpty())
                return "";
            CustomerModel newPriority = priorityCart.poll();
            synchronized (lock2) {
                if (dispenseReceipt(newPriority.getFullName(), newPriority.getCart())) {
                    System.out.println("Thank You for Shopping with us. " + Thread.currentThread().getName());
                    System.out.println();
                    result = "SUCCESSFUL";
                } else {
                    result = "UNSUCCESSFUL";
                }
            }
            return result;

        } else {
            System.out.println("You are no longer a staff here. And so cannot dispense receipt");
            return "NOT-A-STAFF";
        }
    }

    private boolean dispenseReceipt(String customer_name, Map<String, ProductDetails> cart) {
        //System.out.println(cart.size());
        if (!cart.isEmpty()) {
            System.out.println("*********************************************************");
            System.out.println(customer_name + " PURCHASE RECEIPT");
            System.out.println("Products                  Price(₦\u200E)              Quantity");

            Set<Map.Entry<String, ProductDetails>> customerCart = new HashSet<>(cart.entrySet());
            int totalAmount = customerCart.stream().peek(
                    item -> {
                        System.out.printf("%-25s %-25s %-25s", item.getKey(), item.getValue().getPrice(), item.getValue().getQuantity());
                        System.out.println();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            System.out.println("Interrupt Exception thrown. " + e);
                        }
                    }
            ).map(item -> item.getValue().getPrice() * item.getValue().getQuantity()).reduce(0, Integer::sum);

//            for (Map.Entry<String, ProductDetails> items : cart.entrySet()) {
//                System.out.printf("%-25s %-25s %-25s", items.getKey(), items.getValue().getPrice(), items.getValue().getQuantity());
//                System.out.println();
//                sum += items.getValue().getPrice() * (items.getValue().getQuantity());
//            }
            // Formatting currency to Nigeria naira and adding decimals.
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "ng"));
            String result = formatter.format(totalAmount);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Interrupt Exception thrown. " + e);
            }

            System.out.println("Total: " + result);
            // Clear the user cart to avoid buy repetition
            cart.clear();
            //If receipt was successfully dispensed
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean addProduct() {
        if (cashier.isHired()) {
            if (products.addProductsToShelve())
                // If products were successfully added
                return true;
            else {
                System.out.println("Products not successfully added.");
                return false;
            }
        } else {
            System.out.println("You are no longer a staff here. And so cannot add products to store");
            return false;
        }
    }

    @Override
    public void view(CATEGORY CAT) {
        products.view.accept(CAT);
    }

    @Override
    public boolean updateStoreRecord() {
        if (cashier.isHired()) {
            if (products.updateStock()) {
                System.out.println("Store Record Updated");
                System.out.println();
                return true;
            } else return false;
        } else {
            System.out.println("Your are no longer a Cashier here.");
            return false;
        }
    }

    public void addToFIFO(CustomerModel customer) {
        fifoCart.add(customer);
    }

    public void addToPriority(CustomerModel customer) {
        priorityCart.add(customer);
    }

//    public void check(){
//        System.out.println(fifoCart.size());
//        System.out.println(priorityCart.size());
//    }

    public Queue<CustomerModel> getFIFO(){
        return fifoCart;
    }
}
