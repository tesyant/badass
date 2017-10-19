package com.lab.tesyant.moviebadass.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.lab.tesyant.moviebadass.model.Results;

import java.util.ArrayList;

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

    public void insertTransaction(ArrayList<Results> resultses) {
        String sql = "INSERT INTO " + DATABASE_TABLE + " ( "
                + DatabaseHelper.FIELD_MOVIE_ID + ", "
                + DatabaseHelper.FIELD_TITLE + ", "
                + DatabaseHelper.FIELD_RATE + ", "
                + DatabaseHelper.FIELD_RELEASE + ", "
                + DatabaseHelper.FIELD_OVERVIEW + ", "
                + DatabaseHelper.FIELD_COVER + ", "
                + DatabaseHelper.FIELD_BACKDROP + ") VALUES (?, ?);";
        database.beginTransaction();

        SQLiteStatement statement = database.compileStatement(sql);
        for (int i = 0; i < resultses.size(); i++) {
            statement.bindString(1, String.valueOf(resultses.get(i).getId()));
            statement.bindString(2, resultses.get(i).getTitle());
            statement.bindString(3, String.valueOf(resultses.get(i).getPopularity()));
            statement.bindString(4, resultses.get(i).getReleaseDate());
            statement.bindString(5, resultses.get(i).getOverview());
            statement.bindString(6, resultses.get(i).getPosterPath());
            statement.bindString(7, String.valueOf(resultses.get(i).getBackdropPath()));
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void delete(int id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.FIELD_MOVIE_ID + " = ' " + id + " '", null);
    }
}