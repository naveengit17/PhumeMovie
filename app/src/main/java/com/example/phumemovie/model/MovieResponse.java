package com.example.phumemovie.model;

import androidx.lifecycle.MutableLiveData;
import com.example.phumemovie.data.network.RetrofitServer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class MovieResponse {
    public int page;
    public List<Movie> results;
}
