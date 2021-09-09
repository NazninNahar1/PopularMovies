package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    final OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }

    }
    public void nextPage(View v){

        Intent intent = new Intent(getApplicationContext(), MovieDetails.class);
        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String response = null;
        try {
            response = run("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&page=1&api_key=3fa9058382669f72dcb18fb405b7a831");
        } catch (IOException e) {
            e.printStackTrace();
        }

        MovieDetails mr = new Gson().fromJson(response, MovieDetails.class);

        RecyclerView countryRecyclerView = findViewById(R.id.my_list);
        countryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        countryRecyclerView.setAdapter(new MyAdapter(mr.getResults()));
    }
    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<Result> movieList;

        public MyAdapter(List<Result> movieList) {
            this.movieList = movieList;
        }

        @Override
        public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

            View mItemView = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.list_item_movie, parent, false);
            return new MyViewHolder(mItemView);
        }

        @Override
        public void onBindViewHolder( MainActivity.MyViewHolder holder, int position) {
            holder.name.setText(movieList.get(position).getTitle());

            Glide.with(MainActivity.this)
                    .load("https://image.tmdb.org/t/p/w500"+movieList.get(position).getPosterPath())
                    .into(holder.flag);
            holder.rating.setText(String.valueOf(movieList.get(position).getVoteAverage()));

        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, rating;
        ImageView flag;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);

            rating = itemView.findViewById(R.id.rating);
            flag = itemView.findViewById(R.id.poster);
        }

    }
}