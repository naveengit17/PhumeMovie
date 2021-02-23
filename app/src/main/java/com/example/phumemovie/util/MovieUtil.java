package com.example.phumemovie.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;
import com.example.phumemovie.model.Movie;
import com.example.phumemovie.model.MovieDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieUtil extends IOException{

    public static Boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static List<Movie> getMovieFromDetails(List<MovieDetails> movieDetailslist) {
        List<Movie> list = new ArrayList<Movie>();
        for(int i=0;i<movieDetailslist.size();i++) {
            MovieDetails movieDetails = movieDetailslist.get(i);
            Movie movie = new Movie();
            movie.id = movieDetails.id;
            movie.title = movieDetails.title;
            movie.name = movieDetails.name;
            movie.poster_path = movieDetails.poster_path;
            movie.release_date = movieDetails.release_date;
            movie.first_air_date = movieDetails.first_air_date;
            list.add(movie);

        }
        return list;
    }
}
