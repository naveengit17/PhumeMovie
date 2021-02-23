package com.example.phumemovie.data.db;

import androidx.room.*;
import com.example.phumemovie.model.Movie;
import com.example.phumemovie.model.MovieDetails;
import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface DetailsMovieDao {

    @Query("select * from movie_details")
    Single<List<MovieDetails>> getMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMovies(MovieDetails movie);

    @Delete
    Completable deleteSingleMovie(MovieDetails movie);
}
