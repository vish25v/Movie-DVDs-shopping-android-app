package com.example.vish.homestore.Database;

/**
 * Created by VISH on 3/23/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CartItems_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create DBModels table
        db.execSQL(DBModel.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DBModel.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertItem(String title, String price, String imgsrc) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(DBModel.COLUMN_TITLE, title);
        values.put(DBModel.COLUMN_PRICE, price);
        values.put(DBModel.COLUMN_IMGSRC, imgsrc);


        // insert row
        long id = db.insert(DBModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public DBModel getDBModel(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DBModel.TABLE_NAME,
                new String[]{DBModel.COLUMN_ID, DBModel.COLUMN_TITLE, DBModel.COLUMN_PRICE, DBModel.COLUMN_IMGSRC},
                DBModel.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare DBModel object
        assert cursor != null;
        DBModel DBModel = new DBModel(
                cursor.getInt(cursor.getColumnIndex(com.example.vish.homestore.Database.DBModel.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(com.example.vish.homestore.Database.DBModel.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(com.example.vish.homestore.Database.DBModel.COLUMN_PRICE)),
                cursor.getString(cursor.getColumnIndex(com.example.vish.homestore.Database.DBModel.COLUMN_IMGSRC)));


        // close the db connection
        cursor.close();

        return DBModel;
    }

    public List<DBModel> getAllDBModels() {
        List<DBModel> DBModels = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModel.TABLE_NAME + " ORDER BY " +
                DBModel.COLUMN_TITLE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DBModel DBModel = new DBModel();
                DBModel.setId(cursor.getInt(cursor.getColumnIndex(com.example.vish.homestore.Database.DBModel.COLUMN_ID)));
                DBModel.setTitle(cursor.getString(cursor.getColumnIndex(com.example.vish.homestore.Database.DBModel.COLUMN_TITLE)));
                DBModel.setPrice(cursor.getString(cursor.getColumnIndex(com.example.vish.homestore.Database.DBModel.COLUMN_PRICE)));
                DBModel.setImgsrc(cursor.getString(cursor.getColumnIndex(com.example.vish.homestore.Database.DBModel.COLUMN_IMGSRC)));


                DBModels.add(DBModel);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return DBModels list
        return DBModels;
    }

    public int getDBModelsCount() {
        String countQuery = "SELECT  * FROM " + DBModel.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateDBModel(DBModel DBModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBModel.COLUMN_TITLE, DBModel.getTitle());
        values.put(DBModel.COLUMN_PRICE, DBModel.getPrice());
        values.put(DBModel.COLUMN_IMGSRC, DBModel.getImgsrc());

        // updating row
        return db.update(DBModel.TABLE_NAME, values, DBModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(DBModel.getId())});
    }

    public void deleteDBModel(DBModel DBModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBModel.TABLE_NAME, DBModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(DBModel.getId())});
        db.close();
    }
}


