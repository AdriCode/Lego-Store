package com.example.android.legostore;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import com.example.android.legostore.data.LegoContract.LegoEntry;
import com.example.android.legostore.data.LegoDBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LegoDBHelper mDBHelper;
    private SQLiteDatabase db;
    private String mProductName;
    private String mPrice;
    private String mQuantity;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDBHelper = new LegoDBHelper(this);
        displayData();
    }

    private void displayData(){
        //Projection string
        String[] projection = {
                LegoEntry.COLUMN_ID,
                LegoEntry.COLUMN_PRODUCT_NAME,
                LegoEntry.COLUMN_PRICE,
                LegoEntry.COLUMN_QUANTITY,
        };

        cursor = db.query(LegoEntry.TABLE_NAME, projection, null, null, LegoEntry.COLUMN_QUANTITY, null, LegoEntry.COLUMN_QUANTITY);
        List<Product> allProducts = new ArrayList<>();

        if (cursor != null){
            try{
                if (cursor.moveToFirst()){
                    allProducts.add(getProduct());
                    cursor.moveToNext();
                }
            } finally{
                cursor.close();
            }
        }
    }

    private Product getProduct(){
        mProductName = cursor.getString(cursor.getColumnIndex(LegoEntry.COLUMN_PRODUCT_NAME));
        mPrice = cursor.getString(cursor.getColumnIndex(LegoEntry.COLUMN_PRICE));
        mQuantity = cursor.getString(cursor.getColumnIndex(LegoEntry.COLUMN_QUANTITY));

        Product lego = new Product(mProductName, mPrice, mQuantity);
        return lego;
    }
}
