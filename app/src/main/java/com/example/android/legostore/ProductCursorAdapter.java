package com.example.android.legostore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.legostore.data.LegoContract.LegoEntry;

public class ProductCursorAdapter extends CursorAdapter{

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        Button sale = view.findViewById(R.id.sale);
        final int id = cursor.getInt(cursor.getColumnIndex(LegoEntry.COLUMN_ID));

        // Find fields to populate
        ImageView imageView = view.findViewById(R.id.image);
        TextView NameView = view.findViewById(R.id.product_name);
        TextView PriceView = view.findViewById(R.id.price);
        final TextView QuantityView = view.findViewById(R.id.quantity);

        // Extract Properties from cursor
        imageView.setImageResource(R.drawable.logo);
        NameView.setText(cursor.getString(cursor.getColumnIndexOrThrow(LegoEntry.COLUMN_PRODUCT_NAME)));
        PriceView.setText(cursor.getString(cursor.getColumnIndexOrThrow(LegoEntry.COLUMN_PRICE)));
        QuantityView.setText(cursor.getString(cursor.getColumnIndexOrThrow(LegoEntry.COLUMN_QUANTITY)));

        //Update the quantity of the product when the sale button is clicked
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productQuantity = Integer.parseInt(QuantityView.getText().toString());

                if (productQuantity > 0){
                    //Getting the URI of the product in the database
                    Uri productUri = ContentUris.withAppendedId(LegoEntry.CONTENT_URI, id);

                    //Update the quantity of the product in the database
                    ContentValues values = new ContentValues();
                    values.put(LegoEntry.COLUMN_QUANTITY, productQuantity - 1);
                    String selection = LegoEntry.COLUMN_ID + "=?";
                    String[] selectionArgs = new String[] { String.valueOf(ContentUris.parseId(productUri)) };
                    context.getContentResolver().update(productUri, values, selection, selectionArgs);
                } else {
                    Toast.makeText(context, R.string.negative, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
