package com.lab.tesyant.moviebadass;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.lab.tesyant.moviebadass.db.FavouriteHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by tesyant on 10/29/17.
 */

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Bitmap> mWidgetitems = new ArrayList<>();
    private Context mcontext;
    private int mAppWidgetId;

    FavouriteHelper favouriteHelper;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mcontext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        favouriteHelper = new FavouriteHelper(mcontext);
        favouriteHelper.open();
        favouriteHelper.getAllData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetitems.size();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mcontext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imgView_banner, mWidgetitems.get(position));
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mcontext)
                    .load("http://image.tmdb.org/t/p/w185" + favouriteHelper.getBanner())
                    .asBitmap()
                    .error(new ColorDrawable(mcontext.getResources().getColor(R.color.colorPrimary)))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        }
        catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }

        Bundle extras = new Bundle();
        extras.putInt(FavouriteAf.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        rv.setImageViewBitmap(R.id.imgView_widgetbanner, bitmap);
        return rv;

    }
}
