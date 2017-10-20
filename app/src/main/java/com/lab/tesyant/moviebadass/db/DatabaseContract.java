package com.lab.tesyant.moviebadass.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by tesyant on 10/20/17.
 */

public class DatabaseContract {

    public static String TABLE_NAME = "favourite";

    public static final class FavColumn implements BaseColumns {

        public static String FIELD_MOVIE_ID = "movieId";
        public static String FIELD_TITLE = "title";
        public static String FIELD_RELEASE = "release";
        public static String FIELD_RATE = "rate";
        public static String FIELD_OVERVIEW = "overview";
        public static String FIELD_COVER = "cover";
        public static String FIELD_BACKDROP = "backdrop";
    }

    public static final String AUTHORITY = "com.lab.tesyant.moviebadass";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static String getColumnString (Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt (Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong (Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }


}
