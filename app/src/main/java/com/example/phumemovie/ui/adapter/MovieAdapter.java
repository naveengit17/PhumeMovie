package com.example.phumemovie.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phumemovie.R;
import com.example.phumemovie.databinding.MovieItemLayoutBinding;
import com.example.phumemovie.model.Movie;
import com.example.phumemovie.ui.MovieClickCallback;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    List<Movie> movieList = new ArrayList<>();

    MovieClickCallback mMovieClickCallback;

    public MovieAdapter(MovieClickCallback movieClickCallback) {
        mMovieClickCallback = movieClickCallback;
    }

    public void updateList(List<Movie> list) {
        movieList.clear();
        movieList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        MovieItemLayoutBinding movieItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_item_layout, parent, false);
        return new MovieHolder(movieItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieHolder holder, final int position) {
        holder.mMovieItemLayoutBinding.setMovie(movieList.get(position));
        holder.mMovieItemLayoutBinding.movieLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMovieClickCallback.onClick(movieList.get(position).id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    public class MovieHolder extends RecyclerView.ViewHolder{

        public MovieItemLayoutBinding mMovieItemLayoutBinding;

        public MovieHolder(MovieItemLayoutBinding movieItemLayoutBinding) {
            super(movieItemLayoutBinding.getRoot());

            mMovieItemLayoutBinding = movieItemLayoutBinding;
        }
    }
}
