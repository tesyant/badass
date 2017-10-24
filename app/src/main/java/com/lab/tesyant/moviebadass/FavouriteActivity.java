package com.lab.tesyant.moviebadass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lab.tesyant.moviebadass.adapter.CustomItemClickListener;
import com.lab.tesyant.moviebadass.adapter.FavAdapter;
import com.lab.tesyant.moviebadass.db.FavouriteHelper;
import com.lab.tesyant.moviebadass.model.Results;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {

    RecyclerView rvFav;
    FavAdapter favAdapter;
    FavouriteHelper favHelper;
    ArrayList<Results> result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        rvFav = (RecyclerView)findViewById(R.id.recycler_view);

        favHelper = new FavouriteHelper(this);
        favHelper.open();

        result = favHelper.getAllData();

        rvFav.setLayoutManager(new LinearLayoutManager(this));
        FavAdapter adapter = new FavAdapter(getApplicationContext(), result, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String id = String.valueOf(result.get(position).getId());
                Intent intent = new Intent(FavouriteActivity.this, DetailListActivity.class);
                intent.putExtra("movieId", id);
                startActivity(intent);
            }
        }, FavouriteActivity.this);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvFav.setLayoutManager(llm);
        rvFav.setAdapter(adapter);
    }

}