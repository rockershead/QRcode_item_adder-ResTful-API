package com.smove.qr_item_adder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;





import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

     ZXingScannerView scannerview,scannerview2;
     Button scanbutton;JSONObject obj;
    String obj_string;
    Button button;int quantity_count=0;

    private static String url_json = "http://35.237.163.36:60001/product";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanbutton = (Button) findViewById(R.id.button);








    }

    public void onClick(View v) {
        scannerview = new ZXingScannerView(this);
        setContentView(scannerview);
        scannerview.setResultHandler(this);
        scannerview.startCamera();


    }

    @Override
    public void handleResult(Result result) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //try {

    //        JSONObject obj = new JSONObject(result.getText());

         //   Log.d("My App", obj.toString());

      //  } catch (Throwable t) {

      //  }

        obj_string=result.getText().toString();
        if(checkNetworkConnection())
            new HTTPAsyncTask().execute("http://35.237.163.36:60001/product");
        else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();

        scannerview.resumeCameraPreview(this);


    }


    public boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isConnected = false;
        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
            // show "Connected" & type of network "WIFI or MOBILE"
            //tvIsConnected.setText("Connected "+networkInfo.getTypeName());
            // change background color to red
            //tvIsConnected.setBackgroundColor(0xFF7CCC26);


        } else {
            // show "Not Connected"
            //tvIsConnected.setText("Not Connected");
            // change background color to green
            //tvIsConnected.setBackgroundColor(0xFFFF0000);
        }

        return isConnected;
    }

    private String httpPost(String myUrl) throws IOException, JSONException {
        String result = "";

        URL url = new URL(myUrl);

        // 1. create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");



        // 2. add JSON content to POST request body
        setPostRequestContent(conn, obj_string);

        // 3. make POST request to the given URL
        conn.connect();

        // 4. return response message
        return conn.getResponseMessage()+"";

    }

    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return httpPost(urls[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

        }
    }

    private void setPostRequestContent(HttpURLConnection conn, String obj_string) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(obj_string);
        //Log.i(MainActivity.class.toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }








}
