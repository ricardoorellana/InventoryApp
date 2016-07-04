package com.rorellanam.android.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rorellanam on 6/28/16.
 */
public class InventoryDAO {
    private SQLiteDatabase database;
    private BaseDAO baseDAO;

    private String[] columns = {
            BaseDAO.ID,
            BaseDAO.PRODUCT_NAME,
            BaseDAO.QUANTITY,
            BaseDAO.PRICE,
            BaseDAO.IMAGE
    };


    public InventoryDAO(Context context) {
        baseDAO = new BaseDAO(context);
    }


    public void open() throws SQLException {
        database = baseDAO.getWritableDatabase();
    }


    public void close() {
        baseDAO.close();
    }


    public long create(Inventory inventory) {
        ContentValues values = new ContentValues();

        values.put(BaseDAO.PRODUCT_NAME, inventory.getProductName());
        values.put(BaseDAO.QUANTITY, inventory.getQuantity());
        values.put(BaseDAO.PRICE, inventory.getPrice());
        values.put(BaseDAO.IMAGE, inventory.getImage());

        return database.insert(BaseDAO.TABLE, null, values);
    }


    public Inventory read(int id) {
        Cursor cursor = database.query(BaseDAO.TABLE, columns, BaseDAO.ID + " = " + id, null, null, null, null);
        Inventory habit = new Inventory();
        if  (cursor.moveToFirst()) {
            habit.setIdInventory(cursor.getInt(0));
            habit.setProductName(cursor.getString(1));
            habit.setQuantity(cursor.getString(2));
            habit.setPrice(cursor.getString(3));
        }
        cursor.close();
        return habit;
    }


    public List<Inventory> readAll() {
        List<Inventory> inventories = new ArrayList<Inventory>();
        Cursor cursor = database.query(BaseDAO.TABLE, columns, null, null, null, null, null);
        if  (cursor.moveToFirst()) {
            if(cursor.moveToFirst()){
                do{
                    Inventory inventory = new Inventory(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getBlob(4));
                    inventories.add(inventory);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return inventories;
    }


    public int update(Inventory inventory) {
        int id = inventory.getIdInventory();
        ContentValues values = new ContentValues();

        values.put(BaseDAO.PRODUCT_NAME, inventory.getProductName());
        values.put(BaseDAO.QUANTITY, inventory.getQuantity());
        values.put(BaseDAO.PRICE, inventory.getPrice());
        values.put(baseDAO.IMAGE, inventory.getImage());
        return database.update(BaseDAO.TABLE, values, BaseDAO.ID + " = " + id, null);
    }


    public void delete(Inventory inventory) {
        long id = inventory.getIdInventory();

        database.delete(BaseDAO.TABLE, BaseDAO.ID + " = " + id, null);
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
