package com.example.phumemovie.ui.viewmodelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.phumemovie.repository.MovieRepository;
import com.example.phumemovie.ui.DetailsActivityViewModel;

public class DetailsViewModelFactory implements ViewModelProvider.Factory {

    MovieRepository mMovieRepository;

    public DetailsViewModelFactory(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailsActivityViewModel(mMovieRepository);
    }
}
