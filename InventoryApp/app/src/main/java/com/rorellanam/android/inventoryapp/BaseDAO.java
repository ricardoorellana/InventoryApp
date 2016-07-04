package com.rorellanam.android.inventoryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

public class BaseDAO extends SQLiteOpenHelper {

	public static final String TABLE = "Inventory";
	public static final String ID = "_id";
	public static final String PRODUCT_NAME = "product_name";
	public static final String QUANTITY = "quantity";
	public static final String PRICE = "price_item";
	public static final String IMAGE = "prod_img";

	public static final String DATABASE_NAME = "InventoryDB";
	public static final int DATABASE_VERSION = 1;

	public static final String CREATE_INVENTORY = "CREATE TABLE " + TABLE + " ( " +
			ID              + " integer primary key autoincrement not null, " +
			PRODUCT_NAME    + " text, " +
			QUANTITY        + " text, " +
			PRICE           + " text, " +
			IMAGE           + " BLOB );" ;

	public BaseDAO(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		System.out.println("database created" + this.toString());
		database.execSQL(CREATE_INVENTORY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + TABLE);
		onCreate(database);
	}

}
