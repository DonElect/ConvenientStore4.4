package com.store.models;

import com.store.services.CATEGORY;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails {
    private String nameOfProduct;
    private CATEGORY CAT;
    private int price;
    private int quantity;
}
