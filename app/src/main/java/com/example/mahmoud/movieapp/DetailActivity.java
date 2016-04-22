package com.example.mahmoud.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    Intent back;
    Trailer t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        back = getIntent();
        ImageView poster = (ImageView) findViewById(R.id.movie_image);
        String poster_url = back.getStringExtra("poster_url");
        Toast.makeText(getBaseContext(), poster_url, Toast.LENGTH_LONG).show();
        Picasso.with(getApplicationContext()).load(poster_url).into(poster);
        TextView titel = ((TextView) findViewById(R.id.movie_name_text));
        titel.setText(back.getStringExtra("title"));
        TextView date = ((TextView) findViewById(R.id.movie_date_text));
        date.setText(back.getStringExtra("date"));
        TextView vote = ((TextView) findViewById(R.id.movie_averge_text));
        vote.setText(back.getStringExtra("vote"));
        TextView ov = ((TextView) findViewById(R.id.movie_overview_text));
        ov.setText(back.getStringExtra("ov"));
        Button trailer = (Button) findViewById(R.id.trailer);
        t = new Trailer(getBaseContext(),back.getStringExtra("id"));
        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(t.trailer)));
                Toast.makeText(DetailActivity.this, "Trailer is " + t.trailer, Toast.LENGTH_SHORT).show();
            }
        });

    }

}