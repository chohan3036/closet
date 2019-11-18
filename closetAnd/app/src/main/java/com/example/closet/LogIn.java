package com.example.closet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    EditText id, pwd;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        id = (EditText) findViewById(R.id.login_id);
        pwd = (EditText) findViewById(R.id.login_pwd);
        ok = (Button) findViewById(R.id.login_ok);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == ok) {
            if(id.getText().toString() != null && pwd.getText().toString()!=null){
                ArrayList<String> params = new ArrayList<>();
                params.add(id.getText().toString());
                params.add(pwd.getText().toString());
                LogInNetworking networking = new LogInNetworking(params);
                networking.execute();
            }
            else{
                //입력해주세요
            }
        }
    }

    class LogInNetworking extends AsyncTask<Void, Void, Void> {
        URL url = null;
        HttpURLConnection urlConnection = null;
        ArrayList<String> params ;
        public LogInNetworking(ArrayList<String> params) {
            this.params = params;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String param = null;
            OutputStream outputStream;
            String response;

            try {
                param = "id="+params.get(0)+"&pwd="+params.get(1);
                url = new URL("http://52.78.194.160:3000/user/signin");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Connection", "Keep-Alive");
                urlConnection.setConnectTimeout(20 * 1000);//20초
                urlConnection.setReadTimeout(20 * 1000);

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                outputStream = urlConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(param);
                outputStreamWriter.flush();
                urlConnection.connect();

                Log.d("Log_dSIGNIN", String.valueOf(urlConnection.getResponseCode()));

                if (urlConnection.getResponseCode() == 201) {//200번으로 줘야되는것같은데
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    response = readStream(in);
                    JSONObject responseJson = new JSONObject(response);
                    String uid = responseJson.getString("Uid");
                    Log.d("LOGUID",uid);
                }
                else if(urlConnection.getResponseCode() == 400){
                    //id or pwd틀렸다
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
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
}