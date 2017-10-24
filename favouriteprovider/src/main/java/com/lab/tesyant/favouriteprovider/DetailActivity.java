package com.lab.tesyant.favouriteprovider;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lab.tesyant.favouriteprovider.entity.Detail;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle, tvRate, tvRelease, tvOverview;
    private ImageView imgCover, imgHeader;
    private ImageButton btnFav;

    public static String EXTRA_FAV_ITEM = "extra_fav_item";
    private Detail detail = null;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = (TextView)findViewById(R.id.detail_title);
        tvRelease = (TextView)findViewById(R.id.detail_release_date);
        tvRate = (TextView)findViewById(R.id.detail_rate);
        tvOverview = (TextView)findViewById(R.id.detail_overview);

        imgCover = (ImageView) findViewById(R.id.img_cover);
        imgHeader = (ImageView) findViewById(R.id.imgView_banner);

        btnFav = (ImageButton) findViewById(R.id.btn_fav);
        btnFav.setOnClickListener(this);

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) detail = new Detail(cursor);
                cursor.close();
            }
        }
    }

    @Override
    public void onClick(View view) {

    }
}
