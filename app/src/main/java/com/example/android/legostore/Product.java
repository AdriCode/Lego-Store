package com.example.android.legostore;

public class Product {
    private int mID;
    private String mProductName;
    private String mPrice;
    private String mQuantity;

    public Product(int ID, String product_name, String price, String quantity) {
        this.mID = ID;
        this.mProductName = product_name;
        this.mPrice = price;
        this.mQuantity = quantity;
    }

    public int getID() { return mID; }

    public String getProductName() { return mProductName; }

    public String getPrice() {
        return mPrice;
    }

    public String getQuantity() {
        return mQuantity;
    }
}
