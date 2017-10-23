package com.lab.tesyant.moviebadass;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lab.tesyant.moviebadass.adapter.CustomItemClickListener;
import com.lab.tesyant.moviebadass.adapter.FavAdapter;
import com.lab.tesyant.moviebadass.db.FavouriteHelper;
import com.lab.tesyant.moviebadass.model.Results;

import java.util.ArrayList;

public class FavouriteActivity extends Activity implements View.OnClickListener {

    RecyclerView rvFav;
    FavAdapter favAdapter;
    FavouriteHelper favHelper;

    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        rvFav = (RecyclerView)findViewById(R.id.recycler_view);

        favHelper = new FavouriteHelper(this);
        favHelper.open();

        rvFav.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        ArrayList<Results> result = new ArrayList<>();
        result = favHelper.getAllData();

        final String[] results = new String[result.size()];

        FavAdapter adapter = new FavAdapter(getApplicationContext(), result, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String id = results[position];
                Intent intent = new Intent(FavouriteActivity.this, DetailListActivity.class);
                intent.putExtra("favId", id);
                startActivity(intent);
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvFav.setLayoutManager(llm);
        rvFav.setAdapter(adapter);
    }

}