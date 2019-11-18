package com.example.closet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    class LogInNetworking extends AsyncTask<Void,Void,Void>{
        URL url = null;
        HttpURLConnection urlConnection = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                url = new URL("http://52.78.194.160:3000/user/signup");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Connection", "Keep-Alive");
                urlConnection.setConnectTimeout(20*1000);//20초
                urlConnection.setReadTimeout(20*1000);

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}