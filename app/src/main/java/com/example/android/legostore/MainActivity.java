package com.example.android.legostore;

import android.content.ContentUris;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.android.legostore.data.LegoContract.LegoEntry;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LEGO_LOADER = 0;
    private ProductCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the ListView which will be populated with the data
        GridView listView = findViewById(R.id.list_view);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        TextView emptyView = findViewById(R.id.empty_view);
        //Setting default text When there is no information to display in the database
        emptyView.setText(R.string.default_text);
        emptyView.setBackgroundResource(R.drawable.lego_empty2);
        listView.setEmptyView(emptyView);

        //Setting the cursor adapter
        adapter = new ProductCursorAdapter(this, null);
        listView.setAdapter(adapter);

        // Prepare the loader.  Either re-connect with an existing one or start a new one.
        getLoaderManager().initLoader(LEGO_LOADER, null, this);

        // Setup when clicked on list item to open EditorActivity with item details
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent updateProduct = new Intent(MainActivity.this, EditorActivity.class);
                Uri product = ContentUris.withAppendedId(LegoEntry.CONTENT_URI, id);
                updateProduct.setData(product);
                startActivity(updateProduct);
            }
        });

        // FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_main.xml file.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Inactivate Delete All option in the menu when the database is empty
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Shows delete all products action at the app bar
        switch (item.getItemId()) {
            case R.id.action_deleteAll:
                deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Method that delete all the products from the database
    private void deleteAll() {
        getContentResolver().delete(LegoEntry.CONTENT_URI, null, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define a projection that specifies the columns from the table
        String[] projection = {
                LegoEntry.COLUMN_ID,
                LegoEntry.COLUMN_PRODUCT_NAME,
                LegoEntry.COLUMN_PRICE,
                LegoEntry.COLUMN_QUANTITY,
        };
        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this, LegoEntry.CONTENT_URI, projection, null, null, null);
    }

    // Update {@link LegoCursorAdapter} with this new cursor containing updated Lego data
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    // Callback called when the data needs to be deleted
    @Override
    public void onLoaderReset(Loader<Cursor> loader) { adapter.swapCursor(null);
    }
}