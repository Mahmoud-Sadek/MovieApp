package com.example.mahmoud.movieapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    GridView gridView;
    GridviewAdapter gridviewAdapter;
    private ArrayList<String> movieArrayList = null;
    public static boolean land=false;
    DatabasAdapter DataBase ;
//    static MovieData movieData [];

    public MainActivityFragment() {
    }

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView) v.findViewById(R.id.grid_view);

        DataBase = new DatabasAdapter(getActivity());
        if(isNetworkAvailable()){
            Toast.makeText(getActivity(),DataBase.deleteData()+" deleted" , Toast.LENGTH_LONG).show();
            new MovieTask().execute("popular?");
        }
        else{
            Toast.makeText(getActivity(), "No Network Available", Toast.LENGTH_SHORT).show();
            movieArrayList =DataBase.getAllData();
                gridviewAdapter = new GridviewAdapter(getActivity(), movieArrayList );
                gridView.setAdapter(gridviewAdapter);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), movieArrayList.get(i), Toast.LENGTH_LONG).show();
                int poster_e = movieArrayList.get(i).indexOf("\"t\"");
                int title_s = movieArrayList.get(i).indexOf("\"t\"")+3;
                int title_e = movieArrayList.get(i).indexOf("\"d");
                int date_s = movieArrayList.get(i).indexOf("\"d\"") + 3;
                int date_e = movieArrayList.get(i).indexOf("\"v");
                int vote_s = movieArrayList.get(i).indexOf("\"v\"") + 3;
                int vote_e = movieArrayList.get(i).indexOf("\"o");
                int ov_s = movieArrayList.get(i).indexOf("\"o\"") + 3;
//                int ov_e = movieArrayList.get(i).indexOf("\"i");
//                int id_s = movieArrayList.get(i).indexOf("\"i\"") + 3;
                String baseUrl = "http://image.tmdb.org/t/p/w185";
                String poster_url = baseUrl+movieArrayList.get(i).substring(0, poster_e);
                String title = movieArrayList.get(i).substring(title_s, title_e);
                String date = movieArrayList.get(i).substring(date_s, date_e);
                String vote = movieArrayList.get(i).substring(vote_s, vote_e);
                String ov = movieArrayList.get(i).substring(ov_s);
//                String id =movieArrayList.get(i).substring(id_s);
//                DataBase = new DatabasAdapter(getActivity());
//                long insrt = DataBase.insertData(1, poster_url, title, date, vote, ov);
//                if (insrt !=-1){
//                    Toast.makeText(getActivity(), "Done Insert Data", Toast.LENGTH_SHORT).show();
//                }else Toast.makeText(getActivity(), "Error In Insert Data", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), DataBase.getAllData2(), Toast.LENGTH_LONG).show();

            if (land == true) {
                    MainActivity.land(poster_url, title, date, vote, ov);
                } else {
                Toast.makeText(getActivity(), movieArrayList.get(i), Toast.LENGTH_LONG).show();
                    Intent Deatail = new Intent(getActivity(), DetailActivity.class);
                    Deatail.putExtra("poster_url", poster_url);
                    Deatail.putExtra("title", title);
                    Deatail.putExtra("date", date);
                    Deatail.putExtra("vote", vote);
                    Deatail.putExtra("ov", ov);
                    startActivity(Deatail);
                }
            }
        });

        Button top = (Button) v.findViewById(R.id.top);
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "top rated ", Toast.LENGTH_LONG).show();
                MovieTask task = new MovieTask();
                task.execute("top_rated?");
            }
        });
        return v;
    }

    public void getData(String [] movie){
        if (movie != null) {
            movieArrayList = new ArrayList<>();
            for (int i =0; i< movie.length;i++) {
                movieArrayList.add(movie[i]);
                int poster_e = movieArrayList.get(i).indexOf("\"t\"");
                int title_s = movieArrayList.get(i).indexOf("\"t\"")+3;
                int title_e = movieArrayList.get(i).indexOf("\"d");
                int date_s = movieArrayList.get(i).indexOf("\"d\"") + 3;
                int date_e = movieArrayList.get(i).indexOf("\"v");
                int vote_s = movieArrayList.get(i).indexOf("\"v\"") + 3;
                int vote_e = movieArrayList.get(i).indexOf("\"o");
                int ov_s = movieArrayList.get(i).indexOf("\"o\"") + 3;
//                int ov_e = movieArrayList.get(i).indexOf("\"i");
//                int id_s = movieArrayList.get(i).indexOf("\"i\"") + 3;
                String baseUrl = "http://image.tmdb.org/t/p/w185";
                String poster_url = baseUrl+movieArrayList.get(i).substring(0, poster_e);
                String title = movieArrayList.get(i).substring(title_s, title_e);
                String date = movieArrayList.get(i).substring(date_s, date_e);
                String vote = movieArrayList.get(i).substring(vote_s, vote_e);
                String ov = movieArrayList.get(i).substring(ov_s);
//                String id =movieArrayList.get(i).substring(id_s);
                DataBase = new DatabasAdapter(getActivity());
//                Toast.makeText(getActivity(), poster_url +"\n"+ title+"\n"+ date+"\n"+ vote+"\n"+ ov, Toast.LENGTH_LONG).show();
                long insrt = DataBase.insertData(1, poster_url, title, date, vote, ov);
                if (insrt !=-1){
                    Toast.makeText(getActivity(), "Done Insert Data", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(getActivity(), "Error In Insert Data", Toast.LENGTH_SHORT).show();

            }
            gridviewAdapter = new GridviewAdapter(getActivity(), movieArrayList);
            gridView.setAdapter(gridviewAdapter);
            Log.d("Data Count", movie.length + "");

        } else
            Log.d("message", "No Data");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.top_rated) {
//            MainActivityFragment.FORECAST_BASE_URL="https://api.themoviedb.org/3/movie/top_rated?";
//            String ie="https://api.themoviedb.org/3/movie/top_rated?";
            Toast.makeText(getActivity(), "top rated ", Toast.LENGTH_LONG).show();
            MovieTask  task = new MovieTask();
            task.execute("top_rated?");
            Toast.makeText(getActivity(), "top rated ", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public class MovieTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {

            Log.d("message", "message");
//            if (strings.length == 0) {
//                return null;
//            }
//            Log.v("TESSSSSSSSSSSSSSSSSSSSt",params[0]);
            String FORECAST_BASE_URL =
                    "https://api.themoviedb.org/3/movie/" + params[0];
//            Toast.makeText(getActivity(), params[0], Toast.LENGTH_LONG).show();

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String JsonStr = null;


            try {
//                final String FORECAST_BASE_URL =
//                        "https://api.themoviedb.org/3/movie/popular?";

                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(APPID_PARAM, "9490ec35a6eea2efe32378982073f7a3")
                        .build();

                URL url = new URL(builtUri.toString());


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    Log.d("message", "InputStream");
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    Log.d("message", "buffer");

                    return null;
                }
                JsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("error", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("error", "Error closing stream", e);
                    }
                }
            }

            try {
                Log.d("message", "data");

                return getDataFromJson(JsonStr);
            } catch (JSONException e) {
                Log.e("error", e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            Log.d("message", "null");
            return null;
        }

        @Override
        protected void onPostExecute(String[] movie) {
            getData(movie);
        }

        private String[] getDataFromJson(String jsonStr) throws JSONException {
            final String M_LIST = "results";
            final String poster_path = "poster_path";
            final String release_date = "release_date";
            final String vote_average = "vote_average";
            final String overview = "overview";
            final String original_title = "original_title";
            final String id = "id";
            JSONObject movieJson = new JSONObject(jsonStr);
            JSONArray movieArray = movieJson.getJSONArray(M_LIST);

            String[] results = new String[movieArray.length()];

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.getJSONObject(i);
                String poster = movie.getString(poster_path);
                String release = movie.getString(release_date);
                String vote = movie.getString(vote_average);
                String overvie = movie.getString(overview);
                String title = movie.getString(original_title);
                int _id = movie.getInt(id);
                results[i] = poster + "\"t\"" + title + "\"d\"" + release + "\"v\"" + vote + "\"o\"" + overvie ;
            }
                return results;
        }

    }

}