package com.example.mahmoud.movieapp;

import android.content.Context;
import android.graphics.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud on 3/25/2016.
 */

public class GridviewAdapter extends BaseAdapter {
    private Context context;
    //    int arr[] = {R.drawable.logo22,R.drawable.add_person,R.drawable.log_out,R.drawable.ic_launcher,R.drawable.image,R.drawable.image1,R.drawable.image2};
    ArrayList<String> data;
    GridviewAdapter(Context context, ArrayList<String> data){
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

//        ImageView imageView = new ImageView(context);
//        Picasso.with(context).load(data.get(i)).into(imageView);
        if (view == null) {
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_movie,viewGroup, false);
        }
        ImageView imgView = (ImageView) view.findViewById(R.id.list_item_movie_imageView);
        int poster_e = data.get(i).indexOf("\"t\"");
        String baseUrl = "http://image.tmdb.org/t/p/w185";
        String poster_url = baseUrl+data.get(i).substring(0, poster_e);
//        Toast.makeText(context, poster_url, Toast.LENGTH_LONG).show();
//        String poster_url = data[i].getPoster_url();
        Picasso.with(context).load(poster_url).into(imgView);
        return view;
    }
}
