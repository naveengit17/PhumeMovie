package com.example.phumemovie.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.phumemovie.model.Constant;
import com.example.phumemovie.model.Movie;
import com.example.phumemovie.repository.MovieRepository;

import java.util.List;


public class HomeActivityViewModel extends ViewModel {

    MovieRepository mMovieRepository;

    public HomeActivityViewModel(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    LiveData<List<Movie>> getMovies(int type) {
        if(type == Constant.BOOKMARK) {
            return mMovieRepository.getBoolMarkMovies();
        } else {

            return mMovieRepository.getMovies(type);
        }
    }

    void fetchMovies(int type) {
        mMovieRepository.fetchData(type);
    }

    LiveData<List<Movie>> getMovies(String query) {
        return mMovieRepository.getMovies(query);
    }
}
