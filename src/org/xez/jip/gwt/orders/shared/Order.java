package org.xez.jip.gwt.orders.shared;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String userName;
    private String orderId;
    private List<Product> products = new LinkedList<Product>();

    public Order() {
    }

    public Order(
            String userId,
            String userName,
            String orderId,
            List<Product> products) {
        this.userId = userId;
        this.userName = userName;
        this.orderId = orderId;
        this.products = products;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getSum(){
        int sum = 0;
        for (Product p: products) {
            sum += p.getPrice() * p.getAmount();
        }
        return sum;
    }

    public static class Product implements Serializable {
        private String productId;
        private int price;
        private int amount;

        public Product (){}
        public Product (String productId, int price, int amount){
            if (amount < 1)
                throw new IllegalArgumentException("Amount of product must be 1 or more");
            if (price < 100)
                throw new IllegalArgumentException("Price can't be lower than 1.00");
            this.productId = productId;
            this.price = price;
            this.amount = amount;
        }

        public String getProductId() {
            return productId;
        }

        public int getPrice() {
            return price;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            if (amount < 1)
                throw new IllegalArgumentException("Amount of product must be 1 or more");
            this.amount = amount;
        }
    }
}