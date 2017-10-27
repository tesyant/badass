package com.lab.tesyant.favouriteprovider;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lab.tesyant.favouriteprovider.adapter.FavAdapter;
import com.lab.tesyant.favouriteprovider.db.DatabaseContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FavAdapter favAdapter;
    RecyclerView rvMovie;
    Cursor cursor;

    private final int LOAD_FAV_ID = 110;
    public static final String AUTHORITY = "com.lab.tesyant.moviebadass";
    private static final String BASE_PATH = "favourite";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Favourite Movie");

        rvMovie = (RecyclerView) findViewById(R.id.recycler_view);
        favAdapter = new FavAdapter(this, null);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMovie.setLayoutManager(llm);
        rvMovie.setAdapter(favAdapter);

        getSupportLoaderManager().initLoader(LOAD_FAV_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_FAV_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, DatabaseContract.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_FAV_ID);
    }
}
