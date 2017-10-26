package com.lab.tesyant.favouriteprovider.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.lab.tesyant.favouriteprovider.R;

/**
 * Created by tesyant on 10/26/17.
 */

public class FavAdapter extends CursorAdapter {

    public FavAdapter(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_movie, viewGroup, false);
        return view;
    }

    @Override
    public Cursor getCursor() {

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
