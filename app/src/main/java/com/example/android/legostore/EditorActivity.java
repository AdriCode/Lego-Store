package com.example.android.legostore;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.example.android.legostore.data.LegoContract.LegoEntry;
import com.example.android.legostore.data.LegoDBHelper;

public class EditorActivity extends AppCompatActivity {

    private LegoDBHelper mDBHelper;
    private EditText mProductName;
    private EditText mPrice;
    private EditText mQuantity;
    private EditText mSupplierName;
    private EditText mSupplierPhone;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Create database helper
        mDBHelper = new LegoDBHelper(this);
        getViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertData();
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertData() {
        // Gets the database in write mode
        db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LegoEntry.COLUMN_PRODUCT_NAME, mProductName.getText().toString().trim());
        values.put(LegoEntry.COLUMN_PRICE, mPrice.getText().toString().trim());
        values.put(LegoEntry.COLUMN_QUANTITY, mQuantity.getText().toString().trim());
        values.put(LegoEntry.COLUMN_SUPPLIER_NAME, mSupplierName.getText().toString().trim());
        values.put(LegoEntry.COLUMN_SUPPLIER_PHONE, mSupplierPhone.getText().toString().trim());

        Long newRowId = db.insert(LegoEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving lego", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Lego saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    private void getViews(){
        // Find all relevant views that we will need to read user input from
        mProductName = (EditText) findViewById(R.id.productName);
        mPrice = (EditText) findViewById(R.id.price);
        mQuantity = (EditText) findViewById(R.id.quantity);
        mSupplierName = (EditText) findViewById(R.id.supplierName);
        mSupplierPhone = (EditText) findViewById(R.id.supplierPhone);
    }

}
