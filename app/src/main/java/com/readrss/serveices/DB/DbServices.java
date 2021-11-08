package com.readrss.serveices.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
// Xử lý sqlte
public class DbServices {
    private SQLiteDatabase db ;
    public DbServices(Context context)
    {

        db  = DbHelper.getInstance(context).getWritableDatabase();
    }
    public FeedReaderModel Create(FeedReaderModel model){

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReader.FeedEntry.COLUMN_NAME_TITLE, model.getTitle());
        values.put(FeedReader.FeedEntry.COLUMN_NAME_CONTENT, model.getContent());
        values.put(FeedReader.FeedEntry.COLUMN_NAME_IMG, model.getImg());
        values.put(FeedReader.FeedEntry.COLUMN_NAME_URL, model.getUrl());
        values.put(FeedReader.FeedEntry.COLUMN_NAME_DATE, model.getDate());
// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedReader.FeedEntry.TABLE_NAME, null, values);
        model.setId(newRowId);
        return  model;
    }
    public ArrayList<FeedReaderModel> Get()
    {
        ArrayList<FeedReaderModel> _return = new ArrayList<FeedReaderModel>();
        String[] projection = {
                BaseColumns._ID,
                FeedReader.FeedEntry.COLUMN_NAME_TITLE,
                FeedReader.FeedEntry.COLUMN_NAME_CONTENT,
                FeedReader.FeedEntry.COLUMN_NAME_URL,
                FeedReader.FeedEntry.COLUMN_NAME_IMG,
                FeedReader.FeedEntry.COLUMN_NAME_DATE,
        };

// Filter results WHERE "title" = 'My Title'
        String selection = FeedReader.FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "My Title" };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReader.FeedEntry.COLUMN_NAME_TITLE + " DESC";
        String selectQuery = "SELECT  * FROM " + FeedReader.FeedEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);
        while(cursor.moveToNext()) {

            FeedReaderModel item = new FeedReaderModel(cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5));
            _return.add(item);
        }
        cursor.close();
        return  _return;
    }
}
