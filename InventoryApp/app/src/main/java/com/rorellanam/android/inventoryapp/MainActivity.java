package com.rorellanam.android.inventoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Inventory> inventoryList;
    private InventoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addNewProduct = (Button)findViewById(R.id.add_new_product);
        addNewProduct.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent intent = new Intent(MainActivity.this, AddItem.class);

                // Start the new activity
                startActivity(intent);
            }
        });

        InventoryDAO dao = new InventoryDAO(this);
        dao.open();

        //read all
        inventoryList = dao.readAll();

        adapter = new InventoryAdapter(this, inventoryList);
        final ListView listView = (ListView) findViewById(R.id.product_list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Inventory item = adapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("item", item);
                finish();
                startActivity(intent);

            }
        });
    }
}
