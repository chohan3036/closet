package com.example.closet.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.closet.MainActivity;
import com.example.closet.Networking;
import com.example.closet.R;
import com.example.closet.SaveSharedPreference;

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
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    EditText id, pwd;
    Button ok;
    CheckBox automatic;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        id = (EditText) findViewById(R.id.login_id);
        pwd = (EditText) findViewById(R.id.login_pwd);
        ok = (Button) findViewById(R.id.login_ok);
        ok.setOnClickListener(this);
        context = this;
        automatic = (CheckBox)findViewById(R.id.automatic_login_check);
    }

    @Override
    public void onClick(View view) {
        if (view == ok) {
            if((id.getText().toString()).isEmpty() || (pwd.getText().toString()).isEmpty()) {
                Log.d("Log_d","로그인 정보를 입력해주세요");
            }else{
                HashMap<String,String> params = new HashMap<>();
                params.put("id",id.getText().toString());
                params.put("pwd",pwd.getText().toString());
                Networking networking = null;

                try {
                    networking = new Networking(new URL("http://52.78.194.160:3000/user/signin"),params);
                    networking.execute();
                    JSONObject result = networking.get();
                    if(result.get("status").equals(201)){
                        //uid는 class java.lang.Integer 로 오네
                        Log.d("Log_dLogIn", String.valueOf(result));

                        if(automatic.isChecked()){
                            //shared에 저장.
                            SaveSharedPreference.setString(context,"uid", String.valueOf(result.get("Uid"))); //그냥 int로 받아야하나?

                        }

                        Intent intent  = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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