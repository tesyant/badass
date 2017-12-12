package com.lab.tesyant.moviebadass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lab.tesyant.moviebadass.db.FavouriteHelper;
import com.lab.tesyant.moviebadass.model.detail.Detail;
import com.lab.tesyant.moviebadass.service.Client;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailListActivity extends Activity implements View.OnClickListener{

    private TextView tvTitle, tvRate, tvRelease, tvOverview;
    private ImageView imgCover, imgHeader;
    private ImageButton btnFav;

    private FavouriteHelper favouriteHelper;

    Detail MovieDetail;

    boolean IsFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        favouriteHelper = new FavouriteHelper(getApplicationContext());
        favouriteHelper.open();

        String MOVIE_ID = "";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build());

        Retrofit retrofit = builder.build();

        tvTitle = (TextView)findViewById(R.id.detail_title);
        tvRelease = (TextView)findViewById(R.id.detail_release_date);
        tvRate = (TextView)findViewById(R.id.detail_rate);
        tvOverview = (TextView)findViewById(R.id.detail_overview);

        imgCover = (ImageView) findViewById(R.id.img_cover);
        imgHeader = (ImageView) findViewById(R.id.imgView_banner);

        btnFav = (ImageButton) findViewById(R.id.btn_fav);
        btnFav.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            MOVIE_ID = (String) bundle.get("movieId").toString();
            Log.e("MEV", "Mov ID :" + MOVIE_ID);
        }

        IsFavorite = favouriteHelper.checkid(MOVIE_ID);

        if (IsFavorite) {
            btnFav.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
        }
        else {
            btnFav.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
        }

        Client client = retrofit.create(Client.class);
        Call<Detail> call = client.getDetail(MOVIE_ID, MainActivity.API_KEY);

        call.enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                MovieDetail = response.body();
                SetText(MovieDetail.getOriginalTitle(), String.valueOf(MovieDetail.getPopularity()),
                        MovieDetail.getReleaseDate(), MovieDetail.getOverview());
                SetImage(String.valueOf(MovieDetail.getPosterPath()), String.valueOf(MovieDetail.getBackdropPath()));
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                Log.e("error", "y : " + t);
            }
        });
    }

    private void SetText(String title, String rate, String releaseDate, String overview){
        tvTitle.setText(title);
        tvRate.setText(rate);
        tvRelease.setText(releaseDate);
        tvOverview.setText(overview);
    }

    public void SetImage(String cover, String header) {
        Glide.with(this).load("http://image.tmdb.org/t/p/w185" + cover).into(imgCover);
        Glide.with(this).load("http://image.tmdb.org/t/p/w185" + header).into(imgHeader);
    }

    @Override
    public void onClick(View view) {
        if (IsFavorite) {
            favouriteHelper.delete(MovieDetail.getId());
            Toast.makeText(DetailListActivity.this, MovieDetail.getTitle() + " has been Deleted", Toast.LENGTH_SHORT).show();
            btnFav.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
            IsFavorite = false;
        }

        else {
            favouriteHelper.insertTransaction(MovieDetail);
            Toast.makeText(DetailListActivity.this, MovieDetail.getTitle() + " has been Added", Toast.LENGTH_SHORT).show();
            btnFav.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
            IsFavorite = true;
        }
    }
}