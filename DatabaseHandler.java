package com.test.ben.hyperproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_NAME = "itemsManager";


    private static final String TABLE_ITEMS = "items";


    private static final String KEY_ITEM_NAME = "item_name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_ITEM_DESCRIPTION = "item_description";
    private static final String KEY_ITEM_IMAGE="image";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    String CREATE_ITEMS_TABLE;

    @Override
    public void onCreate(SQLiteDatabase db) {
        CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ITEM_NAME + " TEXT PRIMARY KEY," + KEY_PRICE + " DOUBLE,"
                + KEY_ITEM_DESCRIPTION + " TEXT" + KEY_ITEM_IMAGE + "TEXT" + ")";
        db.execSQL(CREATE_ITEMS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CREATE_ITEMS_TABLE);


        onCreate(db);
    }



    void addItems(Entries item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_NAME, item.get_item_name()); // Item Name
        values.put(KEY_PRICE, item.get_price()); // Item Price
        values.put(KEY_ITEM_DESCRIPTION,item.get_desc());
        values.put(KEY_ITEM_IMAGE,item.get_imagePath());

        // Inserting Row
        db.insert(TABLE_ITEMS, null, values);
        db.close();
    }


}
