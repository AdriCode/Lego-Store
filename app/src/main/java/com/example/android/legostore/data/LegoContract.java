package com.example.android.legostore.data;

import android.net.Uri;
import android.provider.BaseColumns;
import android.content.ContentResolver;

public class LegoContract {

    static final String PATH_ENTERPRISES = "enterprises";

    public static final String PATH_TRANSACTIONS = "transactions";

    //Empty constructor
    public LegoContract() {}

    //Inner class that defines the Lego table
    public static final class LegoEntry implements BaseColumns {

        // The Content authority is a name for the entire content provider.
        public static final String CONTENT_AUTHORITY = "com.example.android.legostore";

        // Create the base of all URI's which apps will use to contact the content provider.
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        // String for adding the table name to content uri.
        public static final String PATH = "lego";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

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