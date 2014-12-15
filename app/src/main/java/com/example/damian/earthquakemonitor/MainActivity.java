package com.example.damian.earthquakemonitor;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity{
    ListView listview;
    ListViewAdapter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    String newTitle = "";

    public static final String TAG_FEATURES = "features";
    public static final String TAG_PROPERTIES = "properties";
    public static final String TAG_MAGNITUDE = "mag";
    public static final String TAG_DATE = "updated";
    public static final String TAG_TIME = "time";
    public static final String TAG_METADATA= "metadata";
    public static final String TAG_SUMMARY_TITLE = "title";
    public static final String TAG_DETAIL_TITLE = "title";
    public static final String TAG_PLACE = "place";
    public static final String TAG_GEOMETRY = "geometry";
    public static final String TAG_COORDINATES = "coordinates";
    public static final String TAG_LATITUDE = "latitude";
    public static final String TAG_LONGITUDE = "longitude";
    public static final String TAG_DEPHT = "depth";
    public static final String HASH_MAP = "hashmap";

    private JSONParser parserObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DownloadJSON().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            arraylist = new ArrayList<>();
            String url = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";

            try {
                parserObj = new JSONParser(url);
                parserObj.getJSONfromURL();
                while(parserObj.parsingComplete);
                JSONObject root = new JSONObject(parserObj.getData());
                Log.i("json", parserObj.getData() );
                JSONArray json_result = root.getJSONArray(TAG_FEATURES);
                JSONObject metadata = root.getJSONObject(TAG_METADATA);
                newTitle = metadata.optString(TAG_SUMMARY_TITLE);
                for (int i = 0; i < json_result.length(); i++) {
                    HashMap<String, String> map = new HashMap();
                    JSONObject feature = json_result.getJSONObject(i);
                    JSONObject properties = feature.getJSONObject(TAG_PROPERTIES);
                    map.put(TAG_MAGNITUDE, properties.optString(TAG_MAGNITUDE));
                    map.put(TAG_DATE, properties.optString(TAG_DATE));
                    map.put(TAG_TIME, properties.optString(TAG_TIME));
                    map.put(TAG_DETAIL_TITLE, properties.optString(TAG_DETAIL_TITLE));
                    map.put(TAG_PLACE, properties.optString(TAG_PLACE));

                    JSONObject geometry = feature.getJSONObject(TAG_GEOMETRY);
                    JSONArray coordinates = geometry.getJSONArray(TAG_COORDINATES);
                    map.put(TAG_LONGITUDE, coordinates.getString(0));
                    map.put(TAG_LATITUDE, coordinates.getString(1));
                    map.put(TAG_DEPHT, coordinates.getString(2));


                    arraylist.add(map);
                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            getSupportActionBar().setTitle(newTitle);
            listview = (ListView) findViewById(R.id.list_view);
            adapter = new ListViewAdapter(MainActivity.this, arraylist);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra(HASH_MAP, arraylist.get(position));
                    startActivity(intent);
                }
            });
        }

    }
}
