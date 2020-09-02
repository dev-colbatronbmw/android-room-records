package com.colbyholmstead.dev.records;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
//import com.google.gson.Gson;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  public ListView listView;
  public List<String> allNames = new ArrayList<String>();
  static Record[] records;
  int recordId;
  Record record;
  Gson gson;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    listView = findViewById(R.id.listViewRecords);
    // prepare for request
    Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
    Network network = new BasicNetwork(new HurlStack());
    RequestQueue requestQueue = new RequestQueue(cache, network);
    requestQueue.start();
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
    gson = gsonBuilder.create();
    String url = "https://shrouded-meadow-28431.herokuapp.com/products";
    StringRequest request = new StringRequest(Request.Method.GET, url,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            // do the thing
            Log.d("INTERNET", "response " + response.toString());
            records = gson.fromJson(response, Record[].class);

            for (Record record : records) {
              allNames.add(record.getProdName());
              Log.d("INTERNET", record.getProdName() + " added to array");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.activity_listview, allNames);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("INTERNET", "selected " + position);
                // use position to find the id of the record in that location
                record = records[position];
                recordId = record.getProdId();
                Intent showIntent = new Intent(getApplicationContext(), ShowActivity.class);
                showIntent.putExtra("RecordIDNumber", recordId);
                startActivity(showIntent);

              }
            });

          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.d("INTERNET", "error " + error.toString());
          }
        }
    );
requestQueue.add(request);



  }


  public void newRecordOnClick(View V) throws ParseException {
    Intent newIntent = new Intent(this, AddActivity.class);
    startActivity(newIntent);


  }

  public static double roundDouble(double value, int places) {
    double scale = Math.pow(10, places);
    return Math.round(value * scale) / scale;
  }
  public void homeOnClick(View view){
    Intent mainIntent = new Intent(this, MainActivity.class);
    startActivity(mainIntent);
  }
}
