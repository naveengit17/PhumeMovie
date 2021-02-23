package com.example.phumemovie.model;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

@Entity(tableName = "movie_details")
public class MovieDetails {
    @PrimaryKey(autoGenerate = false)
    public int id;

    public String backdrop_path;
    public String imdb_id;
    public String original_language;
    public String original_title;
    public String overview;
    public double popularity;
    public String poster_path;
    public String release_date;
    public String title;
    public double vote_average;
    public int vote_count;

    public String name;

    public String status;
    public String first_air_date;

    public String getReleaseDataMsg() {
        return "Release Date: "+getReleaseDate();
    }

    public String getStatus() {
        return "Status: "+status;
    }

    public String getPopularity() {
        return "Popularity: "+popularity;
    }

    public String getVote() {
        return "Vote Count: "+vote_count;
    }

    public String getGetVoteAvg() {
        return "Vote Avg: "+vote_average;
    }


    public String getTitle() {
        return title != null ? title : name;
    }

    public String getReleaseDate() {
        return release_date != null ? release_date : first_air_date;
    }

    @BindingAdapter({"loadPoster"})
    public static void loadImage(ImageView posterView, String url) {
        String posterUrl = "https://image.tmdb.org/t/p/w500" + url;
        Glide.with(posterView.getContext())
                .load(posterUrl)
                .transform(new RoundedCorners(20))
                .into(posterView);
    }
}
