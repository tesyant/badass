package com.lab.tesyant.moviebadass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lab.tesyant.moviebadass.adapter.CustomItemClickListener;
import com.lab.tesyant.moviebadass.adapter.NowPlayAdapter;
import com.lab.tesyant.moviebadass.model.nowPlaying.NowPlaying;
import com.lab.tesyant.moviebadass.model.nowPlaying.Result;
import com.lab.tesyant.moviebadass.service.Client;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lab.tesyant.moviebadass.MainActivity.API_KEY;
import static com.lab.tesyant.moviebadass.MainActivity.LANG;

public class NowPlayActivity extends Activity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_play);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build());

        Retrofit retrofit = builder.build();

        Client client = retrofit.create(Client.class);
        Call<NowPlaying> call = client.getPlaying(API_KEY, LANG);

        call.enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                NowPlaying nowplaying = response.body();
                int size = nowplaying.getResults().size();
                final String[] list_movie_id = new String[size];
                for (int i = 0; i<size; i++) {
                    list_movie_id[i] = String.valueOf(nowplaying.getResults().get(i).getId());
                }


                List<Result> result = nowplaying.getResults();
                NowPlayAdapter listAdapter = new NowPlayAdapter(result, NowPlayActivity.this, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        String id = list_movie_id[position];
                        Intent intent = new Intent(NowPlayActivity.this, DetailListActivity.class);
                        intent.putExtra("movieId", id);
                        startActivity(intent);
                    }
                });
                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(listAdapter);


            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
            }
        });
    }
}
