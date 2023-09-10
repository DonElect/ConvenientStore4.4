package com.store.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Staff {
    @Getter @Setter
    protected String firstName;
    @Getter @Setter
    protected String lastName;
    @Getter @Setter
    protected int age;
    @Getter @Setter
    protected String employerID;
    @Getter @Setter
    protected String email;
    @Getter @Setter
    protected String address;

}
