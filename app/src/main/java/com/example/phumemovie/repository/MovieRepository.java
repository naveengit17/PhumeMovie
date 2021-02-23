package com.example.phumemovie.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.phumemovie.data.db.MovieDataBase;
import com.example.phumemovie.data.network.RetrofitServer;
import com.example.phumemovie.model.Movie;
import com.example.phumemovie.model.MovieDetails;
import com.example.phumemovie.model.MovieResponse;
import com.example.phumemovie.util.MovieUtil;
import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    RetrofitServer mRetrofitServer;
    MovieDataBase mMovieDataBase;

    public MovieRepository(RetrofitServer retrofitServer, MovieDataBase movieDataBase) {
        mRetrofitServer = retrofitServer;
        mMovieDataBase = movieDataBase;
    }

    public MutableLiveData<MovieDetails> getMovieWithId(int id) {
        final MutableLiveData<MovieDetails> movieDetails = new MutableLiveData<>();
        mCompositeDisposable.add((mRetrofitServer.getMovieWithId(id,"ecfadb1cd6df1cf7a7ec8aa64b6d1312"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieDetails>() {
                               @Override
                               public void accept(MovieDetails movie) throws Exception {
                                   movieDetails.setValue(movie);
                               }
                           }
                ));

        return movieDetails;
    }

    public MutableLiveData<List<Movie>> getMovies(final int type) {
        Log.d("naveen", "getMovies");

        mMovieDataBase.getDao().getMovies(type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Movie>>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                                   Log.d("naveen", "Data from onSubscribe");
                               }

                               @Override
                               public void onSuccess(List<Movie> movies) {
                                   mutableLiveData.setValue(movies);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.d("naveen", "Data from onError");
                               }
                           }
                );
        return mutableLiveData;
    }

    public MutableLiveData<List<Movie>> getMovies(final String query) {
        Log.d("naveen", "get Search Movies");
        mCompositeDisposable.add(mRetrofitServer.getSearchMovies("ecfadb1cd6df1cf7a7ec8aa64b6d1312", query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) {
                        mutableLiveData.setValue(movieResponse.results);
                    }
                }));

        return mutableLiveData;
    }

    public void fetchData(final int type) {
        mCompositeDisposable.add((type == 0 ? mRetrofitServer.getTrendingMovies("ecfadb1cd6df1cf7a7ec8aa64b6d1312") : mRetrofitServer.getPlayingNowMovies("ecfadb1cd6df1cf7a7ec8aa64b6d1312"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        mutableLiveData.setValue(movieResponse.results);
                        updateCache(movieResponse.results, type);
                    }
                }));
    }

    private void updateCache(List<Movie> list, int type) {
        prepareData(list, type);
        mMovieDataBase.getDao().insertMovies(list).subscribeOn(Schedulers.io()).subscribe(new Action() {
            @Override
            public void run() throws Exception {
                Log.d("Movie", "Inserted");
            }
        });
    }

    private void prepareData(List<Movie> list, int type) {
        for(int i=0;i<list.size();i++) list.get(i).movieType = type;
    }

    public void addMookMark(MovieDetails movieDetails) {
        mMovieDataBase.getDetaisDao().insertMovies(movieDetails).subscribeOn(Schedulers.io()).subscribe(new Action() {
            @Override
            public void run() throws Exception {
                Log.d("Movie", "Inserted");
            }
        });
    }

    public MutableLiveData<List<Movie>> getBoolMarkMovies() {
        Log.d("naveen", "getMovies");

        mMovieDataBase.getDetaisDao().getMovies().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MovieDetails>>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onSuccess(List<MovieDetails> movieDetails) {
                                   mutableLiveData.setValue(MovieUtil.getMovieFromDetails(movieDetails));
                               }

                               @Override
                               public void onError(Throwable e) {

                               }
                           }
                );
        return mutableLiveData;
    }
}
