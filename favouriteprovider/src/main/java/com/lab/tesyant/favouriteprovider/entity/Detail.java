package com.lab.tesyant.favouriteprovider.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.lab.tesyant.favouriteprovider.db.DatabaseContract;

import static com.lab.tesyant.favouriteprovider.db.DatabaseContract.getColumnString;

/**
 * Created by tesyant on 10/26/17.
 */

public class Detail implements Parcelable {

    private String id, title, release, rate, overview, cover, backdrop;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.release);
        dest.writeString(this.rate);
        dest.writeString(this.overview);
        dest.writeString(this.cover);
        dest.writeString(this.backdrop);
    }

    public Detail() {
    }

    public Detail(Cursor cursor) {
        this.id = getColumnString(cursor, DatabaseContract.FavColumn.MOVIE_ID);
        this.title = getColumnString(cursor, DatabaseContract.FavColumn.TITLE);
        this.release = getColumnString(cursor, DatabaseContract.FavColumn.RELEASE);
        this.rate = getColumnString(cursor, DatabaseContract.FavColumn.RATE);
        this.overview = getColumnString(cursor, DatabaseContract.FavColumn.OVERVIEW);
        this.cover = getColumnString(cursor, DatabaseContract.FavColumn.COVER);
        this.backdrop = getColumnString(cursor, DatabaseContract.FavColumn.BACKDROP);
    }

    protected Detail (Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.release = in.readString();
        this.rate = in.readString();
        this.overview = in.readString();
        this.cover = in.readString();
        this.backdrop = in.readString();
    }

    private static final Parcelable.Creator<Detail> CREATOR = new Parcelable.Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel source) {
            return new Detail(source);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };
}
