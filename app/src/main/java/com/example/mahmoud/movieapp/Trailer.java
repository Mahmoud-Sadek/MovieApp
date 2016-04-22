package com.example.mahmoud.movieapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mahmoud on 4/22/2016.
 */
public class Trailer {

    Context context;
    String trailer;
    public Trailer(Context context, String key) {
        this.context = context;
         MovieTask task = new MovieTask();
        task.execute(key);
    }

//    public String getTrailer(){
//        return
//    }

    public class MovieTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {

            Log.d("message", "message");
//            if (strings.length == 0) {
//                return null;
//            }
//            Log.v("TESSSSSSSSSSSSSSSSSSSSt",params[0]);
            String FORECAST_BASE_URL =
                    "http://api.themoviedb.org/3/movie/"+ params[0]+"/videos?";
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
            String id = movie[0];
            trailer = "https://www.youtube.com/watch?v="+id;
            Toast.makeText(context, "key= "+trailer, Toast.LENGTH_SHORT).show();
        }


        private String[] getDataFromJson(String jsonStr) throws JSONException {
            final String M_LIST = "results";
            final String key = "key";
            JSONObject movieJson = new JSONObject(jsonStr);
            JSONArray movieArray = movieJson.getJSONArray(M_LIST);

            String[] results = new String[movieArray.length()];

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.getJSONObject(i);
                String poster_key = movie.getString(key);
                results[i] = poster_key;
            }
            return results;
        }

    }
}