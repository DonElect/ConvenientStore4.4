package com.store.services;

import com.store.models.Cashier;

public interface ManagerServices {
    boolean hire(Cashier cashier);
    boolean fire(Cashier cashier);
}
