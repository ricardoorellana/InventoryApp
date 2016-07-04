package com.rorellanam.android.inventoryapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rorellanam on 6/28/16.
 */
public class InventoryAdapter extends ArrayAdapter<Inventory> {

        public InventoryAdapter(Context context, List<Inventory> inventoryList) {
            super(context, 0, inventoryList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item, parent, false);
            }

            Inventory currentItem = getItem(position);

            TextView productName = (TextView) listItemView.findViewById(R.id.product_name);
            productName.setText(currentItem.getProductName());

            TextView quantity = (TextView) listItemView.findViewById(R.id.quantity);
            quantity.setText(currentItem.getQuantity());

            TextView price = (TextView) listItemView.findViewById(R.id.price);
            price.setText(currentItem.getPrice());

            ImageView image = (ImageView) listItemView.findViewById(R.id.image_place);
            Bitmap imageBitmap = InventoryDAO.getImage(currentItem.getImage());
            image.setImageBitmap(imageBitmap);

            return listItemView;
        }

}
