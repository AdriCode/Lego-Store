package com.example.android.legostore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.legostore.data.LegoContract.LegoEntry;

public class LegoDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "dataLego";

    /**
     * Constructs a new instance of {@link LegoDBHelper}.
     *
     * @param context of the app
     */
    public LegoDBHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =  "CREATE TABLE " + LegoEntry.TABLE_NAME + "("
                + LegoEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LegoEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + LegoEntry.COLUMN_PRICE + " LONG NOT NULL, "
                + LegoEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + LegoEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL, "
                + LegoEntry.COLUMN_SUPPLIER_PHONE + " LONG NOT NULL )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LegoEntry.TABLE_NAME);
        onCreate(db);
    }
}
