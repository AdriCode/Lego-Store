package com.example.android.legostore;

public class Product {
    private String mProductName;
    private String mPrice;
    private String mQuantity;

    public Product(String product_name, String price, String quantity) {
        this.mProductName = product_name;
        this.mPrice = price;
        this.mQuantity = quantity;
    }

    public String getProductName() {
        return mProductName;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getQuantity() {
        return mQuantity;
    }
}
