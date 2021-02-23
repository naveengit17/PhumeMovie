package com.example.phumemovie.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.phumemovie.model.Movie;
import com.example.phumemovie.model.MovieDetails;

@Database(entities = {Movie.class, MovieDetails.class}, version = 1, exportSchema = false)
public abstract class MovieDataBase extends RoomDatabase {

    public abstract MovieDao getDao();

    public abstract DetailsMovieDao getDetaisDao();
}
