package com.springboot.onlineStore.model.wrappers;

import com.springboot.onlineStore.model.Product;

import java.util.Collections;
import java.util.List;

/**
 * Wraps a list of products to prevent returning a JSON Array which is not a good practice
 */
public class ProductsWrapper {
    private List<Product> products = Collections.EMPTY_LIST;

    public ProductsWrapper(List<Product> products) {
        this.products = Collections.unmodifiableList(products);
    }

    public List<Product> getProducts() {
        return products;
    }
}
