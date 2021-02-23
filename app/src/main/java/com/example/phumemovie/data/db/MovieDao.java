package com.example.phumemovie.data.db;

import androidx.room.*;
import com.example.phumemovie.model.Movie;
import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("select * from movie where movieType = :type")
    Single<List<Movie>> getMovies(int type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMovies(List<Movie> list);

    @Query("delete from movie where movieType =:type")
    Completable deleteMovies(int type);
}
