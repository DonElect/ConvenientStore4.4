package com.store.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
public class CustomerModel implements Comparable<CustomerModel>{
    private String fullName;
    private int ID;
    private static int count = 1;
    private Map<String, ProductDetails> cart;
    private int totalPrice;


    public CustomerModel(String fullName) {
        this.fullName = fullName;
        this.ID = count++;
    }

    public CustomerModel(String fullName, Map<String, ProductDetails> cart) {
        this.fullName = fullName;
        this.cart = cart;
        this.totalPrice = totalPriceOfItem.apply(cart);
    }

    private Function<Map<String, ProductDetails>, Integer> totalPriceOfItem = cart ->
            cart.values().stream().map(customer-> (customer.getQuantity()*customer.getPrice())).reduce(0, Integer::sum);

    @Override
    public int compareTo(CustomerModel o) {
        return Integer.compare(o.totalPrice, this.totalPrice);
    }
}
