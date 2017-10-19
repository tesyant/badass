package com.lab.tesyant.moviebadass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lab.tesyant.moviebadass.model.detail.DetailActivity;
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

//    public static String EXTRA_FAV = "extra_fav";
//    public static String EXTRA_POSITION = "extra_position";
//
//    private boolean isEdit = false;
//
//    public static int REQUEST_ADD = 100;
//    public static int RESULT_ADD = 100;
//    public static int REQUEST_UPDATE = 200;
//    public static int RESULT_UPDATE = 201;
//    public static int RESULT_DELETE = 301;
//
//    private final int ALERT_DIALOG_CLOSE = 10;
//    private final int ALERT_DIALOG_DELETE = 20;
//
//    private Results results;
//    private int position;
//    private FavouriteHelper favouriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

        Client client = retrofit.create(Client.class);
        Call<DetailActivity> call = client.getDetail(MOVIE_ID, MainActivity.API_KEY);

        call.enqueue(new Callback<DetailActivity>() {
            @Override
            public void onResponse(Call<DetailActivity> call, Response<DetailActivity> response) {
                DetailActivity MovieDetail = response.body();
                SetText(MovieDetail.getOriginalTitle(), String.valueOf(MovieDetail.getPopularity()),
                        MovieDetail.getReleaseDate(), MovieDetail.getOverview());
                SetImage(String.valueOf(MovieDetail.getPosterPath()), String.valueOf(MovieDetail.getBackdropPath()));
            }

            @Override
            public void onFailure(Call<DetailActivity> call, Throwable t) {

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
        Log.e("coba", "ya");
        Glide.with(this).load("http://image.tmdb.org/t/p/w185" + cover).into(imgCover);
        Glide.with(this).load("http://image.tmdb.org/t/p/w185" + header).into(imgHeader);
    }

    @Override
    public void onClick(View view) {
        boolean isFavourite = readState();

        if (isFavourite) {
            btnFav.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
            isFavourite = false;
            saveState(isFavourite);

//
//
//            String title = detail.getTitle().toString().trim();
//            String rate = String.valueOf(detail.getPopularity()).toString().trim();
//            String release = detail.getReleaseDate();
//            String overview = detail.getOverview();
//            String cover = detail.getPosterPath();
//            String backdrop = detail.getBackdropPath();

//            boolean isEmpty = false;
//
//            if (DatabaseHelper.FIELD_MOVIE_ID == null) {
//                isEmpty = true;
//                Log.e("DB", "null");
//            }
//
//            if (!isEmpty) {
//
//                DetailActivity detail = new DetailActivity();
//                String id = String.valueOf(detail.getId()).toString().trim();
//
//            }

        }

        else {
            btnFav.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
            isFavourite = true;
            saveState(isFavourite);
        }


    }

    private void saveState(boolean isFavourite) {
        SharedPreferences sharedPreference = this.getSharedPreferences("Favourite", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferenceEdit = sharedPreference.edit();
        sharedPreferenceEdit.putBoolean("State", isFavourite);
        sharedPreferenceEdit.commit();
    }

    private boolean readState() {
        SharedPreferences sharedPreference = this.getSharedPreferences("Favourite", Context.MODE_PRIVATE);
        return sharedPreference.getBoolean("State", true);
    }
}
