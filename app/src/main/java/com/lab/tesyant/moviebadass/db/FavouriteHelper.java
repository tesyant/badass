package com.lab.tesyant.moviebadass.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lab.tesyant.moviebadass.model.Results;

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

    public void delete(int id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.FIELD_MOVIE_ID + " = ' " + id + " '", null);
    }

}
