package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetail extends AppCompatActivity {

    final OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        movieId = getIntent().getIntExtra("movie_id", 0);

        ImageView postView = findViewById(R.id.profile_Pic);
        ImageView covorView = findViewById(R.id.cover);
        TextView titleView = findViewById(R.id.title);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView overview = findViewById(R.id.overview);


        String response = null;
        try {
            response = run("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=3fa9058382669f72dcb18fb405b7a831");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Test  " + movieId);

        MovieDetailsResponse mr = new Gson().fromJson(response, MovieDetailsResponse.class);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + mr.getPosterPath())
                .into(postView);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + mr.getBackdropPath())
                .into(covorView);

        // titleView.setText(mr.getTitle());
        ratingBar.setRating((float) mr.getVoteAverage() / 2);
        overview.setText(mr.getOverview());


        // Toast.makeText(this, "Movie id "+response, Toast.LENGTH_LONG).show();


//        String response1 = null;
//        try {
//            response1 = run("https://api.themoviedb.org/3/movie/"+movieId+"?api_key=3fa9058382669f72dcb18fb405b7a831");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        CastResponse mr1 = new Gson().fromJson(response, CastResponse.class);
//
//        RecyclerView cast = findViewById(R.id.recView2);
//
//        LinearLayoutManager manager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        //LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        cast.setAdapter(new MyAdapter2(mr1.getResults()));
//
//
//
//
//    }
//    class MyAdapter2 extends RecyclerView.Adapter<MainActivity.MyViewHolder>{
//        List<CastResponse> castResponseList;
//
//
//        @NonNull
//        @Override
//        public MainActivity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View cItemView = LayoutInflater.from(MovieDetail.this)
//                    .inflate(R.layout.cast_detail, parent, false);
//            return new MyViewHolder2(cItemView);
//
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull MovieDetail.MyViewHolder2 holder, int position) {
//
//
//        }
//
//        @Override
//        public int getItemCount(){return castResponseList.size();}
//    }
//    public class MyViewHolder2 extends RecyclerView.ViewHolder {
//
//        TextView name;
//        ImageView flag;
//
//
//        public MyViewHolder2(View itemView) {
//            super(itemView);
//            name = itemView.findViewById(R.id.profile_Pic);
//            flag = itemView.findViewById(R.id.cname);
//
//        }
//    }


    }
}