package com.example.android.legostore.data;

import android.provider.BaseColumns;

public class LegoContract {

    //Empty constructor
    private LegoContract() {}

    //Inner class that defines the Lego table
    public static final class LegoEntry implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "lego";

        // Column names
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_PRODUCT_NAME = "productName";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplierName";
        public static final String COLUMN_SUPPLIER_PHONE = "supplierPhone";
    }
}