package com.example.android.legostore.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class LegoContract {

    //Empty constructor
    private LegoContract() {}

    //Inner class that defines the Lego table
    public static final class LegoEntry implements BaseColumns {

        // The Content authority is a name for the entire content provider.
        private static final String CONTENT_AUTHORITY = "com.example.android.legostore";

        // String for adding the table name to content uri.
        private static final String PATH = "lego";

        // Create the base of all URI's which apps will use to contact the content provider.
        private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        // The content URI to access the lego data in the provider.
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);

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