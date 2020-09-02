package com.colbyholmstead.dev.records;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static java.lang.Double.parseDouble;

public class AddActivity extends AppCompatActivity {
  public String recordName;
  public double recordPrice;
  public int recordRating;
  public String recordDescription;
  public LiveData<List<String>> items;
  public ArrayAdapter<String> adapter;
  public List<String> outStrings;
  public EditText edtRecordName;
  public EditText edtRecordDescription;
  public EditText edtRecordPrice;
  public Boolean matchName = false;

  public List<String> allNames = new ArrayList<String>();
  static Record[] records;
  int recordId;
  Record record;
  Gson gson;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add);
    edtRecordName = findViewById(R.id.edtName);
    edtRecordDescription = findViewById(R.id.edtDescription);
    edtRecordPrice = findViewById(R.id.edtPrice);
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

  public void onRadioButtonClicked(View view) {
    // Is the button now checked?
    boolean checked = ((RadioButton) view).isChecked();
    // Check which radio button was clicked
    switch (view.getId()) {
      case R.id.radio_one:
        if (checked)
          recordRating = 1;
        break;
      case R.id.radio_two:
        if (checked)
          recordRating = 2;
        break;
      case R.id.radio_three:
        if (checked)
          recordRating = 3;
        break;
      case R.id.radio_four:
        if (checked)
          recordRating = 4;
        break;
      case R.id.radio_five:
        if (checked)
          recordRating = 5;
        break;
    }
  }


  public void addRecordButtonClicked(View view) throws ParseException {
    recordName = edtRecordName.getText().toString();
    for (String string : allNames) {
      if (recordName.matches(string)) {
        Toast.makeText(AddActivity.this, "You did not enter a unique name", Toast.LENGTH_SHORT).show();
        matchName = true;
        return;
      } else {
        matchName = false;
      }
    }
    if (matchName) {
      Toast.makeText(this, "You did not enter a unique name", Toast.LENGTH_SHORT).show();
    } else if (recordName.matches("")) {
      Toast.makeText(this, "You did not enter a valid name", Toast.LENGTH_SHORT).show();
    } else {
      recordDescription = edtRecordDescription.getText().toString();
      String priceString = edtRecordPrice.getText().toString();
      try {
        recordPrice = Double.parseDouble(priceString);
      } catch (NumberFormatException e) {
        // p did not contain a valid double
      }
//      new Thread(new Runnable() {
//        @Override
//        public void run() {
//          // if the name is not null and is not in the database proceed
////          Record record = new Record();
////          record.setName(recordName);
////          record.setDescription(recordDescription);
////          record.setRating(recordRating);
////          record.setPrice(recordPrice);
////          record.setDateCreated(new Date());
////          Log.d("RECORD", "record " + recordName + " " + recordDescription + " " + recordPrice + " " + recordRating);
////          recordDatabase.recordDao().addRecord(record);
//
//
//        }
//      }
//      ).start();
      Intent mainIntent = new Intent(this, MainActivity.class);
      if (!matchName) {
        String url = "https://shrouded-meadow-28431.herokuapp.com/add_product";
        HashMap<String, String> params = new HashMap<>();
        params.put("prodName", recordName);
        params.put("prodDesc", recordDescription);
        params.put("prodPrice", priceString);
        params.put("prodRating", String.valueOf(recordRating));
        JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(params), new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            Log.d("INTERNET", "success" + response );
          }
        },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                Log.d("INTERNET", "JSON error " + error.toString());
              }
            }
        );
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
       RequestQueue Queue = new RequestQueue(cache, network);
        Queue.start();
        Queue.add(request);



        startActivity(mainIntent);
      }
    }
  }
  public void homeOnClick(View view){
    Intent mainIntent = new Intent(this, MainActivity.class);
    startActivity(mainIntent);
  }

}
