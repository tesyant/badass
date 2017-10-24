package com.lab.tesyant.moviebadass.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.lab.tesyant.moviebadass.model.Results;
import com.lab.tesyant.moviebadass.model.detail.Detail;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.lab.tesyant.moviebadass.db.DatabaseContract.FavColumn.BACKDROP;
import static com.lab.tesyant.moviebadass.db.DatabaseContract.FavColumn.COVER;
import static com.lab.tesyant.moviebadass.db.DatabaseContract.FavColumn.MOVIE_ID;
import static com.lab.tesyant.moviebadass.db.DatabaseContract.FavColumn.OVERVIEW;
import static com.lab.tesyant.moviebadass.db.DatabaseContract.FavColumn.RATE;
import static com.lab.tesyant.moviebadass.db.DatabaseContract.FavColumn.RELEASE;
import static com.lab.tesyant.moviebadass.db.DatabaseContract.FavColumn.TITLE;

/**
 * Created by tesyant on 10/17/17.
 */

public class FavouriteHelper {

    public static String URL_IMAGE = "http://image.tmdb.org/t/p/w185";

    private static final String DATABASE_TABLE = DatabaseHelper.TABLE_NAME;
    private Context context;
    DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public FavouriteHelper(Context context) {
        this.context = context;
    }

    public FavouriteHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public Cursor queryAllData() {
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " ORDER BY "
                + DatabaseHelper.FIELD_MOVIE_ID + " DESC ", null);
    }

    public ArrayList<Results> query() {
        ArrayList<Results> arrayList = new ArrayList<Results>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, MOVIE_ID + " DESC ", null);
        cursor.moveToFirst();
        Results results;
        if (cursor.getCount()>0) {
            do {
                results = new Results();
                results.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
                results.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                results.setPopularity(cursor.getDouble(cursor.getColumnIndexOrThrow(RATE)));
                results.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)));
                results.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                results.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(COVER)));
                results.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));

                arrayList.add(results);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Results> getAllData() {
        ArrayList<Results> arrayList = new ArrayList<Results>();
        Cursor cursor = queryAllData();
        cursor.moveToFirst();
        Results results;
        if (cursor.getCount() > 0) {
            do {
                results = new Results();
                results.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_MOVIE_ID)));
                results.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_TITLE)));
                results.setPopularity(Double.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_RATE))));
                results.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_RELEASE)));
                results.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_OVERVIEW)));
                results.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_COVER)));
                results.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_BACKDROP)));
                arrayList.add(results);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Results results) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseHelper.FIELD_MOVIE_ID, results.getId());
        initialValues.put(DatabaseHelper.FIELD_TITLE, results.getTitle());
        initialValues.put(DatabaseHelper.FIELD_RATE, results.getPopularity());
        initialValues.put(DatabaseHelper.FIELD_RELEASE, results.getReleaseDate());
        initialValues.put(DatabaseHelper.FIELD_OVERVIEW, results.getOverview());
        initialValues.put(DatabaseHelper.FIELD_COVER, results.getPosterPath());
        initialValues.put(DatabaseHelper.FIELD_BACKDROP, String.valueOf(results.getBackdropPath()));
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public void checkdata () {
        Cursor checkdata = database.rawQuery("SELECT * FROM " + DATABASE_TABLE , null);
        if (checkdata.getCount() > 0) {
            Log.e("Check" , "Data are exist");
        }
        else {
            Log.e("Check", "Dta doesn't exist");
        }
    }


    public void insertTransaction(Detail details) {
        String sql = "INSERT INTO " + DATABASE_TABLE + " ( "
                + DatabaseHelper.FIELD_MOVIE_ID + ", "
                + DatabaseHelper.FIELD_TITLE + ", "
                + DatabaseHelper.FIELD_RATE + ", "
                + DatabaseHelper.FIELD_RELEASE + ", "
                + DatabaseHelper.FIELD_OVERVIEW + ", "
                + DatabaseHelper.FIELD_COVER + ", "
                + DatabaseHelper.FIELD_BACKDROP + ") VALUES (?, ?, ?, ?, ?, ?, ?);";
        database.beginTransaction();

        SQLiteStatement statement = database.compileStatement(sql);
            statement.bindString(1, String.valueOf(details.getId()));
            statement.bindString(2, details.getTitle());
            statement.bindString(3, String.valueOf(details.getPopularity()));
            statement.bindString(4, details.getReleaseDate());
            statement.bindString(5, details.getOverview());
            statement.bindString(6, details.getPosterPath());
            statement.bindString(7, String.valueOf(details.getBackdropPath()));

        statement.execute();
        statement.clearBindings();


        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void delete(int id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.FIELD_MOVIE_ID + " = ' " + id + " '", null);
    }

    public Boolean checkid (String id) {
        Cursor check = database.rawQuery("SELECT " + DatabaseHelper.FIELD_MOVIE_ID + " FROM " + DATABASE_TABLE + " WHERE " + DatabaseHelper.FIELD_MOVIE_ID + " = " + id, null);
        if (check.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public Cursor queryById (String id) {
        return database.rawQuery("SELECT " + DatabaseHelper.FIELD_MOVIE_ID + " FROM " + DATABASE_TABLE + " WHERE " + id + " = " + id, null);
    }

    public Cursor queryByIdProvider(String id) {
            return database.query(DATABASE_TABLE, null, MOVIE_ID + " = ?", new String[]{id}, null, null, null, null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE, null, null, null, null, null, MOVIE_ID + " DESC ");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, MOVIE_ID + " = ? ", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ? ", new String[]{id});
    }
}