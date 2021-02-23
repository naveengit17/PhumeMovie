package com.example.phumemovie.ui;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import com.example.phumemovie.R;
import com.example.phumemovie.data.db.MovieDataBase;
import com.example.phumemovie.data.network.NetworkInterceptor;
import com.example.phumemovie.data.network.RetrofitClient;
import com.example.phumemovie.data.network.RetrofitServer;
import com.example.phumemovie.databinding.ActivityDetailsBinding;
import com.example.phumemovie.model.MovieDetails;
import com.example.phumemovie.repository.MovieRepository;
import com.example.phumemovie.ui.viewmodelfactory.DetailsViewModelFactory;
import com.example.phumemovie.util.MovieUtil;

public class DetailsActivity extends AppCompatActivity {


    RetrofitServer mRetrofitServer;
    DetailsActivityViewModel mDetailsActivityViewModel;
    ActivityDetailsBinding mActivityDetailsBinding;

    MovieDetails currentMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        mRetrofitServer = RetrofitClient.getService(new NetworkInterceptor(this.getApplicationContext()));
        MovieDataBase movieDataBase = Room.databaseBuilder(this.getApplicationContext(), MovieDataBase.class, "tdmdatabase").build();
        mDetailsActivityViewModel = new ViewModelProvider(this,
                new DetailsViewModelFactory(
                        new MovieRepository(mRetrofitServer, movieDataBase))).get(DetailsActivityViewModel.class);

        int id = getIntent().getIntExtra("movie_id", 343611);
        fetchMovie(id);
    }

    private void fetchMovie(int id) {
        mDetailsActivityViewModel.getMovieWithId(id).observe(this, new Observer<MovieDetails>() {
            @Override
            public void onChanged(MovieDetails movieDetails) {
                mActivityDetailsBinding.setMovie(movieDetails);
                currentMovie = movieDetails;
            }
        });
    }

    public void clickHandler(View view) {
        if(currentMovie == null) {
            MovieUtil.showToast(this, "Something went wrong");
            return;
        }
        mDetailsActivityViewModel.addBookMark(currentMovie);
    }
}
