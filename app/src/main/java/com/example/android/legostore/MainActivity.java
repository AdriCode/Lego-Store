package com.example.android.legostore;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.example.android.legostore.data.LegoContract.LegoEntry;
import com.example.android.legostore.data.LegoDBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private ArrayList<Product> allProducts;
    private LegoDBHelper mDBHelper;

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

        ListView listView = findViewById(R.id.list_view);
        allProducts = new ArrayList<>();
        displayData();

        ProductAdapter Adapter = new ProductAdapter(this, allProducts);
        listView.setAdapter(Adapter);
    }

    private void displayData(){

        db = mDBHelper.getReadableDatabase();

        //Projection string
        String[] projection = {
                LegoEntry.COLUMN_ID,
                LegoEntry.COLUMN_PRODUCT_NAME,
                LegoEntry.COLUMN_PRICE,
                LegoEntry.COLUMN_QUANTITY,
        };

        cursor = db.query(LegoEntry.TABLE_NAME, projection, null, null, null, null, null);

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
        String mProductName = cursor.getString(cursor.getColumnIndex(LegoEntry.COLUMN_PRODUCT_NAME));
        String mPrice = cursor.getString(cursor.getColumnIndex(LegoEntry.COLUMN_PRICE));
        String mQuantity = cursor.getString(cursor.getColumnIndex(LegoEntry.COLUMN_QUANTITY));

        return new Product(mProductName, mPrice, mQuantity);
    }
}
