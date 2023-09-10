package com.store.implementations;

import com.store.models.Cashier;
import com.store.models.CustomerModel;
import com.store.models.Manager;
import com.store.services.CashierServices;
import com.store.services.ManagerServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    ManagerServices manager;
    Cashier cashier;
    CashierServices cashierImp;
    Customer customer1;
    Customer customer2;
    @BeforeEach
    void SetUp(){
        Manager manager1 = new Manager("Donatus", "Okpala", 29,
                "D55B78", "donatus.okpala@decagon.dev", "Decagon Institute, Ohen");
        manager = new ManagerImp(manager1);
        cashier = new Cashier("Jane", "Mary",
                25, "D0002B02", "jamemary@gmail.com",
                "Ohen", "Stand 1");
        cashierImp = new CashierImp(cashier);
        customer1 = new Customer(new CustomerModel("Mike"));
        customer1.addMe();
        customer2 = new Customer(new CustomerModel("James"));
        customer2.addMe();

        manager.hire(cashier);

        cashierImp.addProduct();
    }
    // Creating the necessary instances


    @Test
    @DisplayName("Add To Cart SUCCESSFULLY")
    void addToCart_SUCCESSFULLY() {
        // Making a valid purchase
        assertSame("DONE", customer1.addToCart("Mango", 25));
        assertSame("DONE", customer1.addToCart("Bitter Leaf", 25));
        assertSame("DONE", customer1.addToCart("Hammer", 25));
        assertSame("DONE", customer1.addToCart("Miksi", 25));
    }

    @Test
    @DisplayName("Add To Cart OUT_OF_STOCK")
    void addToCart_OUT_OF_STOCK() {
        customer1.addToCart("Mango", 250);
        // Total amount of Mango=250. Customer1 already added 250 to his cart. So if customer2 tries to take more than the
        // remain, it won't work.
        assertSame("OUT-OF-STOCK", customer2.addToCart("Mango", 3));
    }

    @Test
    @DisplayName("Add To Cart NOT-IN-STORE")
    void addToCart_NOT_IN_STORE() {
        // Try to buy what is not in store
        assertSame("NOT-IN-STORE", customer1.addToCart("Garri", 3));
    }

    @Test
    @DisplayName("Remove From Cart SUCCESSFULLY")
    void removeFromCart() {
        customer1.addToCart("Orange", 3);
        customer1.addToCart("Mango", 5);
        customer1.addToCart("Apple", 10);

        // Added 3 oranges to cart and removed all 3 oranges, should return "done"
        assertSame("DONE", customer1.removeFromCart("Orange", 3));
    }

    @Test
    @DisplayName("Remove From Cart NOT_IN_CART")
    void removeFromCart_NOT_IN_CART() {
        // Trying to remove an item that is not in cart should return not-in-cart
        assertSame("NOT_IN_CART", customer1.removeFromCart("PawPaw", 3));
    }

    @Test
    void buy() {
        customer1.addToCart("Orange", 3);
        customer1.addToCart("Mango", 5);
        customer1.addToCart("Apple", 10);

        assertTrue(customer1.buy());
    }
}