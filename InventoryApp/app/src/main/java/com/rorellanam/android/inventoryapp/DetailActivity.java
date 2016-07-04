package com.rorellanam.android.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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

public class DetailActivity extends AppCompatActivity {

    private EditText editText;
    private int id;
    private InventoryDAO dao;
    ImageView targetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Inventory item = (Inventory) intent.getSerializableExtra("item");

        editText = (EditText)findViewById(R.id.product_name);
        editText.setText(item.getProductName());

        editText = (EditText)findViewById(R.id.quantity);
        editText.setText(String.valueOf(item.getQuantity()));

        editText = (EditText)findViewById(R.id.price);
        editText.setText(item.getPrice());

        //Sets the image
        targetImage = (ImageView) findViewById(R.id.target_imageView);
        Bitmap imageBitmap = InventoryDAO.getImage(item.getImage());
        targetImage.setImageBitmap(imageBitmap);

        id = item.getIdInventory();


        //Update
        Button update = (Button)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText;

                dao = new InventoryDAO(DetailActivity.this);

                //Get product name
                editText = (EditText) findViewById(R.id.product_name);
                String productName = editText.getText().toString();

                //Get quantity
                editText = (EditText) findViewById(R.id.quantity);
                String quantity = editText.getText().toString();

                editText = (EditText) findViewById(R.id.price);
                String price = editText.getText().toString();

                ImageView imageView = (ImageView)findViewById(R.id.target_imageView);

                if(productName.equals("") || quantity.equals("") || price.equals("") || imageView.getDrawable() == null) {
                    Toast.makeText(DetailActivity.this, "Complete all fields", Toast.LENGTH_LONG).show();
                } else {

                    Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                    byte[] imageToInsert = InventoryDAO.getBytes(bm);

                    //Preparing object to update
                    Inventory item = new Inventory(id, productName, quantity, price, imageToInsert);

                    dao.open();

                    dao.update(item);

                    Toast.makeText(DetailActivity.this, "Product updated", Toast.LENGTH_LONG).show();

                    dao.close();

                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });

        //Delete
        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationDialog().show();
            }
        });


        Button order = (Button)findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get product name
                editText = (EditText) findViewById(R.id.product_name);
                String productName = editText.getText().toString();

                editText = (EditText) findViewById(R.id.price);
                String price = editText.getText().toString();

                editText = (EditText) findViewById(R.id.quantity);
                int quantity = Integer.parseInt(editText.getText().toString());

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));

                intent.putExtra(Intent.EXTRA_SUBJECT, "Just order of " + productName);
                intent.putExtra(Intent.EXTRA_TEXT, "Details can be founded here:\n" +
                "quantity:" + quantity + "\nprice: " + price);

                if (intent.resolveActivity(getPackageManager())!= null) {
                    startActivity(intent);
                }
            }
        });

        //quantity
        Button less = (Button)findViewById(R.id.less);
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editText = (EditText) findViewById(R.id.quantity);
                int quantity = Integer.parseInt(editText.getText().toString());

                if (quantity > 0) {
                    quantity -= 1;
                }

                editText.setText(String.valueOf(quantity));

            }
        });

        Button more = (Button)findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText = (EditText) findViewById(R.id.quantity);
                int quantity = Integer.parseInt(editText.getText().toString());
                quantity += 1;
                editText.setText(String.valueOf(quantity));
            }
        });

        Button buttonLoadImage = (Button) findViewById(R.id.changeImage);
        targetImage = (ImageView) findViewById(R.id.target_imageView);

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

    private AlertDialog confirmationDialog() {
        AlertDialog dialogBox = new AlertDialog.Builder(this)
            //set message, title, and icon
            .setTitle("Delete")
            .setMessage("Do you want to Delete this product")
            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dao = new InventoryDAO(DetailActivity.this);

                    //Preparing object to save
                    Inventory item = new Inventory(id);

                    dao.open();

                    dao.delete(item);
                    Toast.makeText(DetailActivity.this, "Product successfully deleted", Toast.LENGTH_LONG).show();

                    dao.close();

                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);

                    dialog.dismiss();
                }

            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            }).create();
        return dialogBox;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
