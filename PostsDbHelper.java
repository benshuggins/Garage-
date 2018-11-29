package com.test.ben.hyperproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class PostsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "posts.db";



    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Posts.PostEntry.TABLE_NAME + " (" +
                    Posts.PostEntry._ID + " INTEGER PRIMARY KEY," +
                    Posts.PostEntry.COLUMN_TITLE + " TEXT" + "," +
                    Posts.PostEntry.COLUMN_DESCRIPTION + " TEXT" + "," +
                    Posts.PostEntry.COLUMN_PRICE + " TEXT" +","+
                    Posts.PostEntry.COLUMN_IMAGE+" TEXT"+"," +
                    Posts.PostEntry.COLUMN_LATITUDE+" TEXT"+"," +
                    Posts.PostEntry.COLUMN_LONGITUDE+" TEXT"+"," +
                    Posts.PostEntry.COLUMN_LOCATION+" TEXT"+
                    " )";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Posts.PostEntry.TABLE_NAME;

    public PostsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


}