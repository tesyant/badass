package com.lab.tesyant.moviebadass.service;

import com.lab.tesyant.moviebadass.model.search.SearchMovie;
import com.lab.tesyant.moviebadass.model.detail.Detail;
import com.lab.tesyant.moviebadass.model.nowPlaying.NowPlaying;
import com.lab.tesyant.moviebadass.model.upcoming.UpcomingMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by tesyant on 9/19/17.
 */

public interface Client {
    @GET("3/search/movie")
    Call<SearchMovie> getList(@Query("api_key") String api_key, @Query("language") String language, @Query("query") String query);

    @GET("3/movie/{mov_id}")
    Call<Detail> getDetail(@Path("mov_id") String mov_id, @Query("api_key") String api_key);

    @GET("3/movie/upcoming")
    Call<UpcomingMovie> getUpcoming(@Query("api_key") String api_key, @Query("language") String language);

    @GET("3/movie/now_playing")
    Call<NowPlaying> getPlaying(@Query("api_key") String api_key, @Query("language") String language);
}


