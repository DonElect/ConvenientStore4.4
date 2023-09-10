package com.store.implementations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductsTest {
    Products products = new Products();

    @Test
    void addProductsToShelve() {
        assertTrue(products.addProductsToShelve());
    }

    @Test
    void updateStock() {
        assertTrue(products.updateStock());
    }

}