package com.colbyholmstead.dev.records;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ShowActivity extends AppCompatActivity {
//  private AppDatabase recordDatabase;
  public int recordRating;
  public Record record;
  public Record[] records;
  public TextView txtName, txtDescription, txtPrice, txtRating, txtDateCreated, txtDateModified;
  public int recordId;
  public String recordName;
  Gson gson;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show);
    Intent intent = getIntent();
    recordId = intent.getIntExtra("RecordIDNumber", 1);
    txtName = findViewById(R.id.txtName);
    txtDescription = findViewById(R.id.txtDesc);
    txtPrice = findViewById(R.id.txtPrice);
    txtRating = findViewById(R.id.txtRating);
    txtDateCreated = findViewById(R.id.txtDateCreated);
    txtDateModified = findViewById(R.id.txtDateModified);

    Log.d("INTERNET", "product id  " + recordId);






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
            txtName.setText(record.getProdName());
            txtDescription.setText(record.getProdDesc());
            txtPrice.setText(Double.toString(record.getProdPrice()));
            txtRating.setText(Integer.toString(record.getProdRating()));
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            String strCreatedDate = dateFormat.format(record.getProdCreatedDate());
            txtDateCreated.setText(strCreatedDate);
            if (record.getProdModifiedDate() != null) {
              String strModifiedDate = dateFormat.format(record.getProdModifiedDate());
              txtDateModified.setText(strModifiedDate);
            } else {
              txtDateModified.setText(R.string.not_modified);
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


  public void editButtonClicked(View view) {
    Intent editIntent = new Intent(getApplicationContext(), EditActivity.class);
    editIntent.putExtra("editId", recordId);
//    Log.d("POSITION", "sending to edit record " + recordId);
    startActivity(editIntent);
  }

  public void deleteRecordButtonClicked(View view) {

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        switch (which){
          case DialogInterface.BUTTON_POSITIVE:

    // todo DELETE here


            Toast.makeText(ShowActivity.this, record.getProdName() + " Deleted" , Toast.LENGTH_SHORT).show();
            String url = String.format("https://shrouded-meadow-28431.herokuapp.com/product/delete/%s",recordId);
            StringRequest request = new StringRequest( Request.Method.DELETE, url, new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                Log.d("INTERNET", "success" + response );
              }
            },new Response.ErrorListener() {
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

            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainIntent);



            break;

          case DialogInterface.BUTTON_NEGATIVE:
            //No button clicked
            break;
        }
      }
    };

    AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);
    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
        .setNegativeButton("No", dialogClickListener).show();




  }

  public void homeOnClick(View view){
    Intent mainIntent = new Intent(this, MainActivity.class);
    startActivity(mainIntent);
  }


}
