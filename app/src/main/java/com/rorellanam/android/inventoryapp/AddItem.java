package com.rorellanam.android.inventoryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class AddItem extends AppCompatActivity {
    private InventoryDAO dao;
    private ImageView targetImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Button save = (Button)findViewById(R.id.save_product);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText;

                dao = new InventoryDAO(AddItem.this);

                //Get product name
                editText = (EditText) findViewById(R.id.product_name);
                String productName = editText.getText().toString();

                //Get quantity
                editText = (EditText) findViewById(R.id.quantity);
                String quantity = editText.getText().toString();

                editText = (EditText) findViewById(R.id.price);
                String price = editText.getText().toString();

                //Get image
                ImageView imageView = (ImageView)findViewById(R.id.target_imageView_add);

                if(productName.equals("") || quantity.equals("") || price.equals("") || imageView.getDrawable() == null) {
                    Toast.makeText(AddItem.this, "Complete all fields", Toast.LENGTH_LONG).show();
                } else {

                    Bitmap bm =((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    byte [] imageToInsert = InventoryDAO.getBytes(bm);
                    //Preparing object to save
                    Inventory item = new Inventory(productName, quantity, price, imageToInsert);
                    dao.open();

                    Long i = dao.create(item);

                    if (i != -1) {
                        Toast.makeText(AddItem.this, "Product saved successfully", Toast.LENGTH_LONG).show();
                    }

                    dao.close();

                    Intent intent = new Intent(AddItem.this, MainActivity.class);
                    finish();
                    startActivity(intent);


                }

            }
        });


        Button buttonLoadImage = (Button) findViewById(R.id.addImage);
        targetImage = (ImageView) findViewById(R.id.target_imageView_add);

        buttonLoadImage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = null;
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                targetImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(AddItem.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
