package com.test.ben.hyperproject;

import android.provider.BaseColumns;



public class Posts {

    public Posts() {}


    public static abstract class PostEntry implements BaseColumns {
        public static final String TABLE_NAME = "posts";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "decription";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_IMAGE="image";
        public static final String COLUMN_LATITUDE="latitude";
        public static final String COLUMN_LONGITUDE="longitude";
        public static final String COLUMN_LOCATION="location";

    }
}