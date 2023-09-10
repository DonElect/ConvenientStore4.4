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

class CashierImpTest {
    ManagerServices manager;
    Cashier cashier1;
    CashierServices cashierImp1;
    Cashier cashier2;
    CashierServices cashierImp2;
    Customer customer1;
    Customer customer2;
    @BeforeEach
    public void setUp(){
        Manager manager1 = new Manager("Donatus", "Okpala", 29,
                "D55B78", "donatus.okpala@decagon.dev", "Decagon Institute, Ohen");
        manager = new ManagerImp(manager1);
        cashier1 = new Cashier("Jane", "Mary",
                25, "D0002B02", "jamemary@gmail.com",
                "Ohen", "Stand 1");
        cashierImp1 = new CashierImp(cashier1);
        cashier2 = new Cashier("Mike", "John",
                26, "D0003B02", "mikejohn@gmail.com",
                "GRA", "Stand 2");
        cashierImp2 = new CashierImp(cashier2);

        customer1 = new Customer(new CustomerModel("Mike"));
        customer1.addMe();
        customer2 = new Customer(new CustomerModel("James"));
        customer2.addMe();

        manager.hire(cashier1);
        manager.hire(cashier2);

        cashierImp1.addProduct();
        customer1.addToCart("Mango", 3);
        customer1.addToCart("Orange", 15);
        customer1.addToCart("Apple", 5);

        customer2.addToCart("Soldering iron", 8);
        customer2.addToCart("Hammer", 5);
        customer2.addToCart("Screw driver", 5);

//        customer1.buy();
//        customer2.buy();
    }

    // A cashier has to be hired before they can add or dispense receipt to customers
    @Test
    @DisplayName("Sell By FIFO SUCCESSFUL Test.")
    public void SellByFIFO_SUCCESSFUL() {
        // Only print's receipt if there is something in your cart

        new CashierImp().getFIFO().clear();
        customer1.addToCart("Mango", 3);
        customer1.addToCart("Orange", 15);
        customer1.addToCart("Apple", 5);

        //customer2.buy();
        customer1.buy();
        assertEquals("SUCCESSFUL", cashierImp2.sellByFIFO());
    }

    @Test
    @DisplayName("Sell By FIFO UNSUCCESSFUL Test.")
    void SellByFIFO_UNSUCCESSFUL(){
        // Empty cart test
        customer1.getCart().clear();
        customer2.getCart().clear();
        customer1.buy();
        customer2.buy();
        assertEquals("UNSUCCESSFUL", cashierImp2.sellByFIFO());
    }

    @Test
    @DisplayName("Sell By FIFO NOT_A_STAFF Test.")
    void SellByFIFO_NOT_A_STAFF(){
        // Trying to Sell to a customer when you are not a cashier
        manager.fire(cashier2);
        assertEquals("NOT-A-STAFF", cashierImp2.sellByFIFO());
    }

    @Test
    @DisplayName("Sell By Priority SUCCESSFUL Test.")
    public void SellByPriority_SUCCESSFUL() {
        // Only print's receipt if there is something in your cart
        customer1.addToCart("Mango", 3);
        customer1.addToCart("Orange", 15);
        customer1.addToCart("Apple", 5);

        customer2.addToCart("Soldering iron", 8);
        customer2.addToCart("Hammer", 5);
        customer2.addToCart("Screw driver", 5);

        customer1.buy();
        customer2.buy();
        assertEquals("SUCCESSFUL", cashierImp2.sellByPriority());
    }

    @Test
    @DisplayName("Sell By Priority UNSUCCESSFUL Test.")
    public void SellByPriority_UNSUCCESSFUL() {
        // Empty cart test
        customer1.getCart().clear();
        customer2.getCart().clear();
        customer1.buy();
        customer2.buy();
        cashierImp1.sellByPriority();
        assertEquals("UNSUCCESSFUL", cashierImp2.sellByPriority());
    }

    @Test
    @DisplayName("Sell By Priority NOT_A_STAFF Test.")
    public void SellByPriority_NOT_A_STAFF() {
        // Trying to Sell to a customer when you are not a cashier
        manager.fire(cashier2);
        assertEquals("NOT-A-STAFF", cashierImp2.sellByPriority());
    }

    @Test
    @DisplayName("Add Products SUCCESSFULLY")
    void addProduct_SUCCESSFU() {
        manager.hire(cashier1);
        assertTrue(cashierImp1.addProduct());
    }

    @Test
    @DisplayName("Add Products UNSUCCESSFULLY")
    void addProduct_UNSUCCESSFUL() {
        manager.fire(cashier1);
        assertFalse(cashierImp1.addProduct());
    }

    @Test
    @DisplayName("Successfully Update store Record")
    void updateStoreRecord_SUCCESS(){
        assertTrue(cashierImp2.updateStoreRecord());
    }

    @Test
    @DisplayName("UnSuccessfully Update store Record")
    void updateStoreRecord_UNSUCCESSFUL(){
        manager.fire(cashier2);
        assertFalse(cashierImp2.updateStoreRecord());
    }
}