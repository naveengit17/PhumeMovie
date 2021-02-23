package com.example.phumemovie.data.network;

import com.example.phumemovie.model.MovieDetails;
import com.example.phumemovie.model.MovieResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//?api_key=ecfadb1cd6df1cf7a7ec8aa64b6d1312
public interface RetrofitServer {

    public static String BASE_URL = "https://api.themoviedb.org/3/";
    @GET("trending/all/day")
    Observable<MovieResponse> getTrendingMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Observable<MovieResponse> getPlayingNowMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Observable<MovieDetails> getMovieWithId(@Path("id") int id, @Query("api_key") String apiKey);
//https://api.themoviedb.org/3/search/movie?api_key=ecfadb1cd6df1cf7a7ec8aa64b6d1312&query=Jack+Reacher
    @GET("search/movie")
    Observable<MovieResponse> getSearchMovies(@Query("api_key") String apiKey, @Query("query") String searchString);
}
