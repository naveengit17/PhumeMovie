package com.example.phumemovie.ui.viewmodelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.phumemovie.repository.MovieRepository;
import com.example.phumemovie.ui.HomeActivityViewModel;

public class HomeActivityViewModelFactory implements ViewModelProvider.Factory {

    MovieRepository mMovieRepository;
    public HomeActivityViewModelFactory(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeActivityViewModel(mMovieRepository);
    }
}
