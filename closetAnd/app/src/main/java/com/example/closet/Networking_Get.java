package com.example.closet;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Networking_Get extends AsyncTask<Void,Void,JSONObject> {

    URL url = null;
    HttpURLConnection urlConnection = null;
    String response = null;

    public Networking_Get(URL url){
            this.url = url;
    }
    @Override
    protected JSONObject doInBackground(Void... voids) {
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setConnectTimeout(40 * 1000);//40초
            urlConnection.setReadTimeout(40 * 1000);
            urlConnection.setRequestMethod("GET");
            //urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            urlConnection.setDoInput(true);
            //urlConnection.setDoOutput(true);
            urlConnection.connect();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = readStream(in);
            JSONObject responseJson = new JSONObject(response);
            Log.d("Log_dGETResponse",response);
            //JSON return 해주기.
            return responseJson;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject resultJson) {
        super.onPostExecute(resultJson);
    }

    public String readStream(InputStream in) {
        String data = "";
        Scanner s = new Scanner(in);
        while (s.hasNext())
            data += s.nextLine() + "\n";
        s.close();
        try {
            JSONObject response = new JSONObject(data);
            //Log.d("Log_dINput",response.getClass().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
