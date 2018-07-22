package com.example.android.legostore;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.legostore.data.LegoContract.LegoEntry;
import com.example.android.legostore.data.LegoDBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private ArrayList<Product> allProducts;
    private LegoDBHelper mDBHelper = null;
    private GridView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDBHelper = new LegoDBHelper(this);
        displayData();

        // Setup when clicked on list item to open EditorActivity with item details
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent review = new Intent(MainActivity.this, EditorActivity.class);
                Uri item = ContentUris.withAppendedId(LegoEntry.CONTENT_URI, id);
                review.setData(item);
                startActivity(review);
            }
        });
    }

    private void displayData() {

        db = mDBHelper.getReadableDatabase();

        //Projection string
        String[] projection = {
                LegoEntry.COLUMN_ID,
                LegoEntry.COLUMN_PRODUCT_NAME,
                LegoEntry.COLUMN_PRICE,
                LegoEntry.COLUMN_QUANTITY,
        };

        cursor = db.query(LegoEntry.TABLE_NAME, projection, null, null, null, null, null);
        listView = findViewById(R.id.list_view);
        allProducts = new ArrayList<>();

        try {
            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                allProducts.add(getProduct());
                ProductAdapter Adapter = new ProductAdapter(this, allProducts);
                listView.setAdapter(Adapter);
            }
        } finally {
            cursor.close();
        }
    }

    private Product getProduct() {
        int mID = cursor.getInt(cursor.getColumnIndex(LegoEntry.COLUMN_ID));
        String mProductName = cursor.getString(cursor.getColumnIndex(LegoEntry.COLUMN_PRODUCT_NAME));
        String mPrice = cursor.getString(cursor.getColumnIndex(LegoEntry.COLUMN_PRICE));
        String mQuantity = cursor.getString(cursor.getColumnIndex(LegoEntry.COLUMN_QUANTITY));

        return new Product(mID, mProductName, mPrice, mQuantity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_main.xml file.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_deleteAll:
                deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAll() {
        db = mDBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + LegoEntry.TABLE_NAME + "'");
        db.delete(LegoEntry.TABLE_NAME, null, null);
        cursor.close();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}