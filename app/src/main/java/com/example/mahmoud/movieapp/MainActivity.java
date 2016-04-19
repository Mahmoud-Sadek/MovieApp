package com.example.mahmoud.movieapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {


    static TextView date_text;
    static TextView ov_text;
    static TextView vote_text;
    static TextView titel;
    static ImageView poster;
    static Context context;
    static View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        WindowManager wm = getWindowManager();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);


        context = getBaseContext();
        view =findViewById(R.id.fragment2);
        poster = (ImageView) findViewById(R.id.movie_image);
        titel = ((TextView) findViewById(R.id.movie_name_text));
        date_text = (TextView) findViewById(R.id.movie_date_text);
        vote_text = ((TextView) findViewById(R.id.movie_averge_text));
        ov_text = ((TextView) findViewById(R.id.movie_overview_text));
        if(size.x>size.y){
            View viewGroup = (View) findViewById(R.id.fragment2);
            viewGroup.setVisibility(View.INVISIBLE);
            MainActivityFragment.land=true;
        }
        fragmentTransaction.commit();
    }

    public static void land(String poster_url, String title, String date, String vote, String ov){

        view.setVisibility(View.VISIBLE);
        Picasso.with(context).load(poster_url).into(poster);
        titel.setText(title);
        date_text.setText(date);
        vote_text.setText(vote);
        ov_text.setText(ov);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
