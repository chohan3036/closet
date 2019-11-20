package com.example.closet;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class storeClothingNetworking extends AsyncTask<Void,Void,Void> {

    URL url ;
    HttpURLConnection conn ;
    OutputStream outputStream = null;
    //uid,name(color),colorR,colorG,ColorB,category,description,photo

    public storeClothingNetworking() throws MalformedURLException {
        url = new URL("http://52.78.194.160:3000/closet/storeClothing");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String boundary = "-----";
            String LINE_FEED = "\r\n";
            PrintWriter writer ;
            JSONObject result = null;

            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-type","multipart/form-data;charset=UTF-8");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            outputStream = conn.getOutputStream();
            writer  = new PrintWriter(new OutputStreamWriter(outputStream,"UTF-8"),true);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
