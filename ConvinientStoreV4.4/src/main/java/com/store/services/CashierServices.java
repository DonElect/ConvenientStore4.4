package com.store.services;

import com.store.implementations.Customer;

public interface CashierServices {
    String sellByPriority();
    String sellByFIFO();
    boolean addProduct();
    boolean updateStoreRecord();
    void view(CATEGORY CAT);
}
