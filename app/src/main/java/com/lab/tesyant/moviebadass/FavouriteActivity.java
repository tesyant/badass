package com.lab.tesyant.moviebadass;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.lab.tesyant.moviebadass.adapter.FavAdapter;
import com.lab.tesyant.moviebadass.db.FavouriteHelper;
import com.lab.tesyant.moviebadass.model.Results;

import java.util.ArrayList;
import java.util.LinkedList;

public class FavouriteActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvFav;
    ProgressBar progressBar;

    private LinkedList<Results> list;
    private FavAdapter favAdapter;
    private FavouriteHelper favouriteHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        getSupportActionBar().setTitle("Favourite");

        rvFav = (RecyclerView) findViewById(R.id.recycler_view);
        rvFav.setLayoutManager(new LinearLayoutManager(this));
        rvFav.setHasFixedSize(true);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        favouriteHelper = new FavouriteHelper(this);
        favouriteHelper.open();

        list = new LinkedList<>();

        favAdapter = new FavAdapter(this);
        favAdapter.setListFav(list);
        rvFav.setAdapter(favAdapter);

        new LoadFavAsync().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
    }

    public class LoadFavAsync extends AsyncTask<Void, Void, ArrayList<Results>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

            if (list.size() > 0) {
                list.clear();
            }
        }

        @Override
        protected ArrayList<Results> doInBackground(Void... voids) {
            return favouriteHelper.query();
        }

        @Override
        protected void onPostExecute(ArrayList<Results> resultses) {
            super.onPostExecute(resultses);
            progressBar.setVisibility(View.GONE);
            list.addAll(resultses);
            favAdapter.setListFav(list);
            favAdapter.notifyDataSetChanged();

            if (list.size() == 0) {
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvFav, message, Snackbar.LENGTH_SHORT).show();
    }
}