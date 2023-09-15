package com.store;

import com.store.implementations.CashierImp;
import com.store.implementations.Customer;
import com.store.implementations.ManagerImp;
import com.store.models.Cashier;
import com.store.models.CustomerModel;
import com.store.models.Manager;
import com.store.services.CATEGORY;
import com.store.services.CashierServices;


public class Store {

    public static void main(String[] args) {
        //Creating the manager and customer instances and hiring two new cashiers
        Manager manager = new Manager("Donatus", "Okpala", 29,
                "D55B78", "donatus.okpala@decagon.dev", "Decagon Institute, Ohen");
        ManagerImp managerImp = new ManagerImp(manager);

        Cashier cashier1 = new Cashier("Stella", "Louis",
                25, "D0001B2", "stellalouis@gmail.com", "Ikorrodu, Lagos", "Stand 1");
        Cashier cashier2 = new Cashier("Marry", "John",
                25, "D0002B2", "maryjohn@gmail.com", "Makurdi, Benue", "Stand 2");
        Cashier cashier3 = new Cashier("Jacinta", "Mark",
                25, "D0003B3", "jacintamark@gmail.com", "Calabar", "Stand 3");
        CashierServices cashierImp1 = new CashierImp(cashier1);
        CashierServices cashierImp2 = new CashierImp(cashier2);
        CashierServices cashierImp3 = new CashierImp(cashier3);

        managerImp.hire(cashier1);
        managerImp.hire(cashier2);
        managerImp.hire(cashier3);

        Customer customer1 = new Customer(new CustomerModel("Mike"));
        customer1.addMe();
        Customer customer2 = new Customer(new CustomerModel("Femi"));
        customer2.addMe();
        Customer customer3 = new Customer(new CustomerModel("John"));
        customer3.addMe();
        Customer customer4 = new Customer(new CustomerModel("Mathew"));
        customer4.addMe();
        Customer customer5 = new Customer(new CustomerModel("Luke"));
        customer5.addMe();
        Customer customer6 = new Customer(new CustomerModel("Lana"));
        customer6.addMe();
        Customer customer7 = new Customer(new CustomerModel("Clark"));
        customer7.addMe();
        Customer customer8 = new Customer(new CustomerModel("James"));
        customer8.addMe();
        Customer customer9 = new Customer(new CustomerModel("Peter"));
        customer9.addMe();
        Customer customer10 = new Customer(new CustomerModel("Paul"));
        customer10.addMe();

        // Manager Viewing all customers
        managerImp.viewCustomers();

        // Add items to store
        cashierImp1.addProduct();

        // View products in store before purchase begins
        cashierImp1.view(CATEGORY.FRUITS);
        cashierImp1.view(CATEGORY.VEGETABLES);
        cashierImp1.view(CATEGORY.PROVISIONS);
        cashierImp1.view(CATEGORY.TOOLS);


        // Customer1 Buying items from store
        customer1.addToCart("Mango", 25);
        customer1.addToCart("Orange", 65);
        customer1.addToCart("Banana", 2);
        customer1.addToCart("miksi", 1);
        customer1.addToCart("soldering iron", 1);


        // Customer2 buying items
        customer2.addToCart("Mango", 11);
        customer2.addToCart("Orange", 25);
        customer2.addToCart("Banana", 10);
        customer2.addToCart("miksi", 1);
        customer2.addToCart("soldering iron", 2);
        customer2.addToCart("Bitter leaf", 30);
        customer2.addToCart("Water leaf", 20);

        customer3.addToCart("Mango", 13);
        customer3.addToCart("Orange", 21);
        customer3.addToCart("Banana", 4);

        customer4.addToCart("Mango", 3);
        customer4.addToCart("Orange", 5);
        customer4.addToCart("Banana", 11);

        customer5.addToCart("Mango", 2);
        customer5.addToCart("Orange", 7);
        customer5.addToCart("Banana", 9);

        customer6.addToCart("Mango", 1);
        customer6.addToCart("Orange", 7);
        customer6.addToCart("Banana", 3);

        customer7.addToCart("Mango", 11);
        customer7.addToCart("Orange", 25);
        customer7.addToCart("Banana", 10);

        customer8.addToCart("Mango", 9);
        customer8.addToCart("Orange", 5);
        customer8.addToCart("Banana", 17);

        customer9.addToCart("Mango", 19);
        customer9.addToCart("Orange", 2);
        customer9.addToCart("Banana", 10);

        customer10.addToCart("Mango", 3);
        customer10.addToCart("Orange", 15);
        customer10.addToCart("Banana", 12);

        customer1.buy();
        customer2.buy();
        customer3.buy();
        customer4.buy();
        customer5.buy();
        customer6.buy();
        customer7.buy();
        customer8.buy();
        customer9.buy();
        customer10.buy();


        while (!CashierImp.getPriorityCart().isEmpty()) {
            Thread thread1 = new Thread(cashierImp1::sellByPriority, "Cashier: " + cashier1.getFirstName());
            thread1.start();
            Thread thread2 = new Thread(cashierImp2::sellByPriority, "Cashier: " + cashier2.getFirstName());
            thread2.start();
            Thread thread3 = new Thread(cashierImp3::sellByPriority, "Cashier: " + cashier3.getFirstName());
            thread3.start();

            try {
                thread1.join();
                thread2.join();
                thread3.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupt Thrown " + e);
            }
        }

//         View product left after customer purchase
        cashierImp1.view(CATEGORY.FRUITS);
        cashierImp1.view(CATEGORY.VEGETABLES);
        cashierImp1.view(CATEGORY.PROVISIONS);
        cashierImp1.view(CATEGORY.TOOLS);

        cashierImp2.updateStoreRecord();
    }
}
