package com.store.implementations;

import com.store.models.Cashier;
import com.store.models.CustomerModel;
import com.store.models.Manager;
import com.store.services.ManagerServices;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerImp implements ManagerServices {
    private static List<Cashier> cashierList = new ArrayList<>();
    private Manager manager;
    private  @Getter
    static Map<Integer, CustomerModel> listCustomers = new HashMap<>();
    public ManagerImp(Manager manager) {
        this.manager = manager;
    }

    public ManagerImp(){

    }

    @Override
    public boolean hire(Cashier cashier) {
        if (!cashier.isHired()) {
            cashier.setHired(true);
            cashierList.add(cashier);
            System.out.println("Welcome to Roban Store Miss "+cashier.getFirstName()+
                    ". \nMy name is "+manager.getFirstName()+" i am the manager.");
            System.out.println();
            return true;
        }
        else {
            System.out.println("This cashier is already hired");
            System.out.println();
            return false;
        }
    }

    @Override
    public boolean fire(Cashier cashier) {
        if(cashierList.contains(cashier)){
            cashier.setHired(false);
            cashierList.remove(cashier);
            System.out.println(cashier.getFirstName()+" has been fired.");
            System.out.println();
            return true;
        }
        else {
            System.out.println("Cashier already fired.");
            System.out.println();
            return false;
        }
    }

    public boolean viewCustomers(){
        if (!listCustomers.isEmpty()) {
            System.out.println("Customer_ID" + "             " + "Customer_name");
            listCustomers.forEach((key, value) -> {
                System.out.printf("%-24s %-12s", key, value.getFullName());
                System.out.println();
            });
            System.out.println();
            return true;
        }
        else
            return false;
    }
}
