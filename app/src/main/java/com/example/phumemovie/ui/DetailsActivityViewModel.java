package com.example.phumemovie.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.phumemovie.model.MovieDetails;
import com.example.phumemovie.repository.MovieRepository;

public class DetailsActivityViewModel extends ViewModel {
    MovieRepository mMovieRepository;

    public DetailsActivityViewModel(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    LiveData<MovieDetails> getMovieWithId(int id) {
        return mMovieRepository.getMovieWithId(id);
    }

    void addBookMark(MovieDetails movieDetails) {
        mMovieRepository.addMookMark(movieDetails);
    }
}
