package com.lab.tesyant.favouriteprovider.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lab.tesyant.favouriteprovider.R;
import com.lab.tesyant.favouriteprovider.db.DatabaseContract;

import static com.lab.tesyant.favouriteprovider.db.DatabaseContract.FavColumn.COVER;
import static com.lab.tesyant.favouriteprovider.db.DatabaseContract.FavColumn.RELEASE;
import static com.lab.tesyant.favouriteprovider.db.DatabaseContract.FavColumn.TITLE;

/**
 * Created by tesyant on 10/24/17.
 */

public class FavAdapter extends CursorAdapter {

    Activity activity;
    CustomItemClickListener listener;

    public FavAdapter(Context context, Cursor c, boolean autoRequery, CustomItemClickListener listener) {
        super(context, c, autoRequery);
        this.listener = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_movie, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, getCursor().getPosition());
            }
        });
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            TextView tvTitle = (TextView)view.findViewById(R.id.tv_title);
            TextView tvRelease = (TextView)view.findViewById(R.id.tv_release_date);
            ImageView imgCover = (ImageView)view.findViewById(R.id.img_cover);

            tvTitle.setText(DatabaseContract.getColumnString(cursor, TITLE));
            tvRelease.setText(DatabaseContract.getColumnString(cursor, RELEASE));
            Glide.with(activity).load("http://image.tmdb.org/t/p/w185" + COVER).into(imgCover);
        }
    }
}