package com.example.phumemovie.ui;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.phumemovie.R;
import com.example.phumemovie.data.db.MovieDataBase;
import com.example.phumemovie.data.network.NetworkInterceptor;
import com.example.phumemovie.data.network.RetrofitClient;
import com.example.phumemovie.data.network.RetrofitServer;
import com.example.phumemovie.databinding.ActivityMainBinding;
import com.example.phumemovie.model.Constant;
import com.example.phumemovie.model.Movie;
import com.example.phumemovie.repository.MovieRepository;
import com.example.phumemovie.ui.adapter.MovieAdapter;
import com.example.phumemovie.ui.viewmodelfactory.HomeActivityViewModelFactory;
import com.example.phumemovie.util.MovieUtil;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ActivityMainBinding mActivityMainBinding;
    RecyclerView mRecyclerView;
    RetrofitServer mRetrofitServer;
    MovieAdapter mMovieAdapter;
    HomeActivityViewModel mHomeActivityViewModel;
    Context mContext;

    int mType = Constant.TRENDING;

    MovieClickCallback mMovieClickCallback = new MovieClickCallback() {
        @Override
        public void onClick(int id) {
            if(MovieUtil.isNetworkAvailable(mContext)) {
                Intent i = new Intent(HomeActivity.this, DetailsActivity.class);
                i.putExtra("movie_id", id);
                startActivity(i);
            } else {
                MovieUtil.showToast(mContext, "Make sure you have active data connection");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = this;
        mRetrofitServer = RetrofitClient.getService(new NetworkInterceptor(this.getApplicationContext()));
        MovieDataBase movieDataBase = Room.databaseBuilder(this.getApplicationContext(), MovieDataBase.class, "tdmdatabase").build();
        mHomeActivityViewModel = new ViewModelProvider(this,
                new HomeActivityViewModelFactory(
                        new MovieRepository(mRetrofitServer, movieDataBase))).get(HomeActivityViewModel.class);
        mMovieAdapter = new MovieAdapter(mMovieClickCallback);
        mRecyclerView = mActivityMainBinding.list;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mMovieAdapter);
        fetchData(mType);
    }

    private void fetchData(final int type) {
        mHomeActivityViewModel.getMovies(type).observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                mMovieAdapter.updateList(movies);
                if(movies != null && movies.size() != 0) {
                    mActivityMainBinding.noMovies.setVisibility(View.GONE);
                } else {
                    mActivityMainBinding.noMovies.setVisibility(View.VISIBLE);
                    if(type == Constant.BOOKMARK) {
                        mActivityMainBinding.noMovies.setText("No BookMark Movies");
                    } else {
                        mActivityMainBinding.noMovies.setText("Movie Not Availbale in database. Refresh to get latest movies");
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void clickHandler(View view) {
        switch (view.getId()) {
            case R.id.trending:
                mType = Constant.TRENDING;
                fetchData(mType);
                break;

            case R.id.now_playing:
                mType = Constant.PLAYING_NOW;
                fetchData(mType);
                break;

            case R.id.bookmark:
                mType = Constant.BOOKMARK;
                fetchData(mType);
                break;

            default: break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_icon:
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            case R.id.refresh_icon:
                if(MovieUtil.isNetworkAvailable(this) && mType != Constant.BOOKMARK) {
                    mHomeActivityViewModel.fetchMovies(mType);
                } else if(mType != Constant.BOOKMARK) {
                    MovieUtil.showToast(this, "Make sure you have active data connection");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
