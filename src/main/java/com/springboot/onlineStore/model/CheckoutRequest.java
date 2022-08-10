package com.springboot.onlineStore.model;

import java.util.Collections;
import java.util.List;
/**
 * Represents a request for purchasing items from the online store by a user.
 */
public class CheckoutRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String shippingAddress;
    private List<ProductInfo> products = Collections.EMPTY_LIST;

    private String creditCard;

    public CheckoutRequest(String name, String lastName, String email, String shippingAddress, List<ProductInfo> products, String creditCard) {
        this.firstName = name;
        this.lastName = lastName;
        this.email = email;
        this.creditCard = creditCard;
        this.shippingAddress = shippingAddress;
        this.products = products;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public List<ProductInfo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfo> products) {
        this.products = products;
    }

    public void setName(String name) {
        this.firstName = name;
    }

    public static class ProductInfo {
        private long productId;
        private long quantity;

        public ProductInfo(long productId, long quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public long getQuantity() {
            return quantity;
        }

        public void setQuantity(long quantity) {
            this.quantity = quantity;
        }
    }
}
