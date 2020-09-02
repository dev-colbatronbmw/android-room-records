package com.colbyholmstead.dev.records;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.Date;
import java.util.HashMap;

public class EditActivity extends AppCompatActivity {
  //  private AppDatabase recordDatabase;
  public EditText edtName, edtDescription, edtPrice;
  public int recordRating;
  public Double recordPrice;
  public RadioButton rBtn1;
  public RadioButton rBtn2;
  public RadioButton rBtn3;
  public RadioButton rBtn4;
  public RadioButton rBtn5;
  public int recordId;
  public Record record;
  public Record[] records;
  public Record updatedRecord;
  Gson gson;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit);
    Intent intent = getIntent();
    recordId = intent.getIntExtra("editId", 1);
//    Log.d("POSITION", "record to edit " + recordId);
    rBtn1 = findViewById(R.id.radio_one);
    rBtn2 = findViewById(R.id.radio_two);
    rBtn3 = findViewById(R.id.radio_three);
    rBtn4 = findViewById(R.id.radio_four);
    rBtn5 = findViewById(R.id.radio_five);
    edtName = findViewById(R.id.edtEditName);
    edtDescription = findViewById(R.id.edtEditDesc);
    edtPrice = findViewById(R.id.edtEditPrice);







    Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
    Network network = new BasicNetwork(new HurlStack());
    RequestQueue requestQueue = new RequestQueue(cache, network);
    requestQueue.start();
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
    gson = gsonBuilder.create();
    String url = String.format("https://shrouded-meadow-28431.herokuapp.com/product/%s",recordId);
    Log.d("INTERNET", "url  " + url);
    StringRequest request = new StringRequest(Request.Method.GET, url,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            // do the thing
            Log.d("INTERNET", "response " + response.toString());
            records = gson.fromJson(response, Record[].class);
            record = records[0];


            edtName.setText(record.getProdName());
            edtDescription.setText(record.getProdDesc().toString());
            edtPrice.setText(Double.toString(record.getProdPrice()));
            recordRating = record.getProdRating();





    switch (recordRating) {
      case 1:
        rBtn1.toggle();
        break;
      case 2:
        rBtn2.toggle();
        break;
      case 3:
        rBtn3.toggle();
        break;
      case 4:
        rBtn4.toggle();
        break;
      case 5:
        rBtn5.toggle();
        break;
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

  public void editRecordButtonClicked(View view) {

    // todo post edit here
    String url = "https://shrouded-meadow-28431.herokuapp.com/edit_product";
    HashMap<String, String> params = new HashMap<>();
    params.put("prodId", record.getProdId().toString());
    params.put("prodName", edtName.getText().toString());
    params.put("prodDesc", edtDescription.getText().toString());
    params.put("prodPrice", edtPrice.getText().toString());
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

//    record.observe(this, new Observer<Record>() {
//      @Override
//      public void onChanged(Record record) {
//
//        record.setDateModified(new Date());
//        record.setName(edtName.getText().toString());
//        record.setDescription(edtDescription.getText().toString());
//        record.setRating(recordRating);
//
//        String priceString = edtPrice.getText().toString();
//
//        try{
//          recordPrice = Double.parseDouble(priceString);
//          record.setPrice(recordPrice);
//        } catch (NumberFormatException e) {
//          // p did not contain a valid double
//        }
//
//        new Thread(new Runnable() {
//          @Override
//          public void run() {
//
//            recordDatabase.recordDao().updateRecord(record);
//
//            Log.d("POSITION", "record updated " + record.getRecordId());
//          }
//        }
//        ).start();
//      }
//    });
    Intent showIntent = new Intent(getApplicationContext(), ShowActivity.class);
    showIntent.putExtra("RecordIDNumber", recordId);
    startActivity(showIntent);


  }
  public void homeOnClick(View view){
    Intent mainIntent = new Intent(this, MainActivity.class);
    startActivity(mainIntent);
  }

}
