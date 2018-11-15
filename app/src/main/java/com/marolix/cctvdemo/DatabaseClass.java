package com.marolix.cctvdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseClass extends SQLiteOpenHelper {

    public String tableName = "video_names";
    public String tableCountry = "country_names";
    public String fieldId = "id";
    public String videoName = "name";
    public String videoCountry = "name";
    public String videoState = "name";
    public String videoUrl = "url";


    public DatabaseClass(Context context) {
        super(context, "CCTV", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";

        sql += "CREATE TABLE " + tableName;
        sql += " ( ";
        sql += fieldId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += videoCountry + " TEXT, ";
        sql += videoState + " TEXT, ";
        sql += videoName + " TEXT, ";
        sql += videoUrl + " TEXT ";
        sql += " ) ";

        db.execSQL(sql);


        String sqlCountry = "";
        sqlCountry += "CREATE TABLE " + tableCountry;
        sqlCountry += " ( ";
        sqlCountry += fieldId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sqlCountry += videoCountry + " TEXT ";
        sqlCountry += " ) ";
        db.execSQL(sqlCountry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(sql);

        onCreate(db);
    }

    public boolean storeData(String country, String state, String name, String url) {

        boolean createSuccessful = false;

        if (!checkIfExists(name)) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(videoCountry, country);
            values.put(videoState, state);
            values.put(videoName, name);
            values.put(videoUrl, url);
            createSuccessful = db.insert(tableName, null, values) > 0;

            db.close();
            if (createSuccessful) {
                Log.e("Database", name + " " + url + " created.");
            }
        }

        return createSuccessful;
    }


    public boolean checkIfExists(String objectName) {

        boolean recordExists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + fieldId + " FROM " + tableName + " WHERE " + videoName + " = '" + objectName + "'", null);

        if (cursor != null) {

            if (cursor.getCount() > 0) {
                recordExists = true;
            }
        }

        cursor.close();
        db.close();

        return recordExists;
    }

    public ArrayList<String> getVideos(String searchTerm) {

        ArrayList<String> names = new ArrayList<>();
        // select query
        if (searchTerm.equals(""))
            return names;
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + videoName + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + fieldId + " ASC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String objectName = cursor.getString(cursor.getColumnIndex(videoName));
                Log.e("Database class", "objectName: " + objectName);

                names.add(cursor.getString(cursor.getColumnIndex(videoName)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return names;

    }


    public String getVideoUrl(String videoName) {
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + this.videoName + " LIKE '" + videoName + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        String names = "";
        if (cursor.moveToFirst()) {
            do {
                names = cursor.getString(cursor.getColumnIndex(videoUrl));
                Log.e("Database class", "objectName: " + names);

//                names = (cursor.getString(cursor.getColumnIndex(videoName)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return names;
    }

    public boolean storeCountry(String country) {

        boolean createSuccessful = false;

        /*if (!checkIfExists(country)) {*/

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(videoCountry, country);

            createSuccessful = db.insert(tableCountry, null, values) > 0;

            db.close();
            if (createSuccessful) {
                Log.e("Database",   videoCountry+" created");
            }
//        }

        return createSuccessful;
    }
}
