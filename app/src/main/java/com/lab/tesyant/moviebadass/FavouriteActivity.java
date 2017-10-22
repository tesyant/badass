package com.lab.tesyant.moviebadass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.lab.tesyant.moviebadass.adapter.FavAdapter;
import com.lab.tesyant.moviebadass.db.FavouriteHelper;
import com.lab.tesyant.moviebadass.model.Results;

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

        favouriteHelper = new FavouriteHelper(this);
        favouriteHelper.open();

        list = new LinkedList<>();

        favAdapter = new FavAdapter(this);
        favAdapter.setListFav(list);
        rvFav.setAdapter(favAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
    }

}