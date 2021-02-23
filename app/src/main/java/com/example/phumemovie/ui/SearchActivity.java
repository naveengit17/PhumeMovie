package com.example.phumemovie.ui;

import android.content.Context;
import android.content.Intent;
import android.widget.SearchView;
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
import com.example.phumemovie.databinding.ActivitySearchBinding;
import com.example.phumemovie.model.Movie;
import com.example.phumemovie.repository.MovieRepository;
import com.example.phumemovie.ui.adapter.MovieAdapter;
import com.example.phumemovie.ui.viewmodelfactory.HomeActivityViewModelFactory;
import com.example.phumemovie.util.MovieUtil;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ActivitySearchBinding mActivitySearchBinding;
    RecyclerView mRecyclerView;
    RetrofitServer mRetrofitServer;
    MovieAdapter mMovieAdapter;
    HomeActivityViewModel mHomeActivityViewModel;
    Context mContext;

    MovieClickCallback mMovieClickCallback = new MovieClickCallback() {
        @Override
        public void onClick(int id) {
            if(MovieUtil.isNetworkAvailable(mContext)) {
                Intent i = new Intent(mContext, DetailsActivity.class);
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
        mActivitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        mContext = this;
        mRetrofitServer = RetrofitClient.getService(new NetworkInterceptor(this.getApplicationContext()));
        MovieDataBase movieDataBase = Room.databaseBuilder(this.getApplicationContext(), MovieDataBase.class, "tdmdatabase").build();
        mHomeActivityViewModel = new ViewModelProvider(this,
                new HomeActivityViewModelFactory(
                        new MovieRepository(mRetrofitServer, movieDataBase))).get(HomeActivityViewModel.class);
        mMovieAdapter = new MovieAdapter(mMovieClickCallback);
        mRecyclerView = mActivitySearchBinding.list;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mMovieAdapter);

        mActivitySearchBinding.searchId.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s != null && !s.isEmpty()) {
                    if(MovieUtil.isNetworkAvailable(SearchActivity.this)) {
                        fetchData(s);
                    }else {
                        MovieUtil.showToast(SearchActivity.this, "Make sure you have active data connection");
                    }

                }
                return false;
            }
        });
    }

    private void fetchData(String query) {
        mHomeActivityViewModel.getMovies(query).observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                mMovieAdapter.updateList(movies);
            }
        });
    }
}
