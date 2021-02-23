package com.example.phumemovie.model;

import android.widget.ImageView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movie")
public class Movie {

    @PrimaryKey(autoGenerate = false)
    public int id;

    public String title;
    public String name;

    public String poster_path;

    public String release_date;
    public String first_air_date;

    public int movieType;

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
