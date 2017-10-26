package com.lab.tesyant.favouriteprovider.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lab.tesyant.favouriteprovider.R;
import com.lab.tesyant.favouriteprovider.entity.Detail;

/**
 * Created by tesyant on 10/26/17.
 */

public class FavAdapter extends CursorRecyclerViewAdapter<FavAdapter.ViewHolder> {

    private Activity activity;

    public FavAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvRelease;
        public ImageView imgCover;
        public ViewHolder(View view) {
            super(view);
            tvTitle = (TextView)view.findViewById(R.id.tv_title);
            tvRelease = (TextView)view.findViewById(R.id.tv_release_date);
            imgCover = (ImageView)view.findViewById(R.id.img_cover);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_movie, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        Detail detail = Detail.fromCursor(cursor);
        viewHolder.tvTitle.setText(detail.getTitle());
        viewHolder.tvRelease.setText(detail.getRelease());
        Glide.with(activity).load("http://image.tmdb.org/t/p/w185" + detail.getCover()).asBitmap().into(viewHolder.imgCover);

    }


}
