package com.lab.tesyant.moviebadass.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lab.tesyant.moviebadass.db.DatabaseContract;
import com.lab.tesyant.moviebadass.db.FavouriteHelper;

import static com.lab.tesyant.moviebadass.db.DatabaseContract.AUTHORITY;
import static com.lab.tesyant.moviebadass.db.DatabaseContract.CONTENT_URI;

/**
 * Created by tesyant on 10/20/17.
 */

public class FavProvider extends ContentProvider{

    private static final int FAV = 1;
    private static final int FAV_ID = 2;

    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_NAME, FAV);
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_NAME + "/#", FAV_ID);
    }

    private FavouriteHelper favouriteHelper;

    @Override
    public boolean onCreate() {
        favouriteHelper = new FavouriteHelper(getContext());
        favouriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAV :
                cursor = favouriteHelper.queryProvider();
                break;
            case FAV_ID :
                cursor = favouriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default :
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case FAV :
                added = favouriteHelper.insertProvider(values);
                break;
            default :
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case FAV_ID :
                updated = favouriteHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default :
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAV_ID :
                deleted = favouriteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default :
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }
}
