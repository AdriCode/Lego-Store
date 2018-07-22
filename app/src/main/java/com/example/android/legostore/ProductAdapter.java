package com.example.android.legostore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Product currentProduct;

    public ProductAdapter(@NonNull Context context, ArrayList<Product> products) {
        super(context, 0, products);
    }

    /**
     * Provides a view for an AdapterView
     *
     * @param position    The position in the list of data
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the object located at this position in the list
        currentProduct = getItem(position);

        // Find the TextView in the list_item.xml layout with the product ID
        TextView ID = listItemView.findViewById(R.id.ID);
        // Get the date from the current event object and set this text on the Product ID TextView
        ID.setText(String.valueOf(currentProduct.getID()));

        // Find the TextView in the list_item.xml layout with the Product Name
        TextView NameView = listItemView.findViewById(R.id.product_name);
        // Get the date from the current event object and set this text on the Product Name TextView
        NameView.setText(currentProduct.getProductName());

        // Find the TextView in the list_item.xml layout with the Price
        TextView PriceView = listItemView.findViewById(R.id.price);
        // Get the title from the current event object and set this text on the Price TextView
        PriceView.setText(currentProduct.getPrice());

        // Find the TextView in the list_item.xml layout with the Quantity
        TextView QuantityView = listItemView.findViewById(R.id.quantity);
        // Get the title from the current event object and set this text on the Quantity TextView
        QuantityView.setText(currentProduct.getQuantity());

        return listItemView;
    }

}
