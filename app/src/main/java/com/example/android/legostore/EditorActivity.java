package com.example.android.legostore;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.legostore.data.LegoContract.LegoEntry;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LEGO_LOADER = 0;
    private EditText mProductName;
    private EditText mPrice;
    private EditText mQuantity;
    private EditText mSupplierName;
    private EditText mSupplierPhone;
    private Uri currentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        currentUri = intent.getData();

        if (currentUri == null) {
            setTitle(getString(R.string.add_lego_title));
        } else {
            setTitle(getString(R.string.edit_lego_title));
            getLoaderManager().initLoader(LEGO_LOADER, null, this);
        }

        //Views relate to the inputs about the product
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
                saveData();
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        // Create a ContentValues object where column names are the keys,
        // and product attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(LegoEntry.COLUMN_PRODUCT_NAME, mProductName.getText().toString().trim());
        values.put(LegoEntry.COLUMN_PRICE, mPrice.getText().toString().trim());
        values.put(LegoEntry.COLUMN_QUANTITY, mQuantity.getText().toString().trim());
        values.put(LegoEntry.COLUMN_SUPPLIER_NAME, mSupplierName.getText().toString().trim());
        values.put(LegoEntry.COLUMN_SUPPLIER_PHONE, mSupplierPhone.getText().toString().trim());

        if (currentUri == null) {
            //Validate that the input fields are not empty
            if (TextUtils.isEmpty(mProductName.getText().toString()) ||
                    TextUtils.isEmpty(mPrice.getText().toString()) ||
                    TextUtils.isEmpty(mQuantity.getText().toString()) ||
                    TextUtils.isEmpty(mSupplierName.getText().toString()) ||
                    TextUtils.isEmpty(mSupplierPhone.getText().toString())) {
                Toast.makeText(this, R.string.fields_empty, Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            } else {

                // Insert a new product into the provider, returning the content URI for the new item.
                Uri newUri = getContentResolver().insert(LegoEntry.CONTENT_URI, values);

                // Show a toast message depending on whether or not the insertion was successful
                if (newUri == null) {
                    // If the new content URI is null, then there was an error with insertion.
                    Toast.makeText(this, getString(R.string.save_lego_failed),
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the insertion was successful and we can display a toast.
                    Toast.makeText(this, getString(R.string.save_lego_successful),
                            Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            //Update product after edition
            String selection = LegoEntry.COLUMN_ID + "=?";
            String[] selectionArgs = new String[] { String.valueOf(ContentUris.parseId(currentUri)) };
            int updated = getContentResolver().update(currentUri, values, selection, selectionArgs);

            // Show a toast message depending on whether or not the update was successful.
            if (updated == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.update_lego_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.update_lego_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getViews() {
        // Find all relevant views that we will need to read user input from
        mProductName = (EditText) findViewById(R.id.productName);
        mPrice = (EditText) findViewById(R.id.price);
        mQuantity = (EditText) findViewById(R.id.quantity);
        mSupplierName = (EditText) findViewById(R.id.supplierName);
        mSupplierPhone = (EditText) findViewById(R.id.supplierPhone);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                LegoEntry.COLUMN_ID,
                LegoEntry.COLUMN_PRODUCT_NAME,
                LegoEntry.COLUMN_PRICE,
                LegoEntry.COLUMN_QUANTITY,
                LegoEntry.COLUMN_SUPPLIER_NAME,
                LegoEntry.COLUMN_SUPPLIER_PHONE,
        };
        return new CursorLoader(this, currentUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Proceed with moving to the first row of the cursor and reading data from it
        if (data.moveToFirst()) {
            //Update the views on the screen with the values from the database
            mProductName.setText(data.getString(data.getColumnIndexOrThrow(LegoEntry.COLUMN_PRODUCT_NAME)));
            mPrice.setText(String.valueOf(data.getLong(data.getColumnIndexOrThrow(LegoEntry.COLUMN_PRICE))));
            mQuantity.setText(String.valueOf(data.getInt(data.getColumnIndexOrThrow(LegoEntry.COLUMN_QUANTITY))));
            mSupplierName.setText(data.getString(data.getColumnIndexOrThrow(LegoEntry.COLUMN_SUPPLIER_NAME)));
            mSupplierPhone.setText(String.valueOf(data.getLong(data.getColumnIndexOrThrow(LegoEntry.COLUMN_SUPPLIER_PHONE))));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loader.reset();
        mProductName.setText("");
        mPrice.setText("");
        mQuantity.setText("");
        mSupplierName.setText("");
        mSupplierPhone.setText("");
    }
}
