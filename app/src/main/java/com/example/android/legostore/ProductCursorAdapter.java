package com.example.android.legostore;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
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
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate
        ImageView imageView = view.findViewById(R.id.image);
        TextView NameView = view.findViewById(R.id.product_name);
        TextView PriceView = view.findViewById(R.id.price);
        TextView QuantityView = view.findViewById(R.id.quantity);

        // Extract Properties from cursor
        imageView.setImageResource(R.drawable.logo);
        NameView.setText(cursor.getString(cursor.getColumnIndexOrThrow(LegoEntry.COLUMN_PRODUCT_NAME)));
        PriceView.setText(cursor.getString(cursor.getColumnIndexOrThrow(LegoEntry.COLUMN_PRICE)));
        QuantityView.setText(cursor.getString(cursor.getColumnIndexOrThrow(LegoEntry.COLUMN_QUANTITY)));
    }
}
