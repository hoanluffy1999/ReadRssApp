package com.readrss.serveices.DB;

import android.provider.BaseColumns;

public final class FeedReader {
    private FeedReader() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final  String COLUMN_NAME_URL = "url";
        public static final  String COLUMN_NAME_IMG = "img";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                        FeedEntry.COLUMN_NAME_CONTENT + " TEXT" +
                        FeedEntry.COLUMN_NAME_CONTENT + " TEXT" +
                        FeedEntry.COLUMN_NAME_URL + " TEXT" +
                        FeedEntry.COLUMN_NAME_IMG + " TEXT" +
                        FeedEntry.COLUMN_NAME_DATE + " TEXT" +
                        ")";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    }
}
