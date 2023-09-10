package com.store.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class Manager extends Staff{


    public Manager(String firstName, String lastName, int age, String employerID, String email, String address) {
        super(firstName, lastName, age, employerID, email, address);
    }

}
