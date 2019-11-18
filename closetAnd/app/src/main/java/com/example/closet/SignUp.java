package com.example.closet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    Button cancel, ok;
    EditText nickName, id, pwd ,age;
    RadioGroup radioGroup ;
    RadioButton female, male;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setting();
    }

    public void setting() {
        cancel = (Button) findViewById(R.id.cancel_signUp);
        ok = (Button) findViewById(R.id.ok_singUp);
        radioGroup = (RadioGroup)findViewById(R.id.sexGroup);
        female = (RadioButton)findViewById(R.id.female);
        male= (RadioButton)findViewById(R.id.male);
        female.setOnClickListener(this);
        male.setOnClickListener(this);
        nickName = (EditText) findViewById(R.id.nickname_signUp);
        id = (EditText) findViewById(R.id.id_signUp);
        pwd = (EditText) findViewById(R.id.pwd_signUp);
        age = (EditText) findViewById(R.id.age_signUp);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == cancel) {
            //intent로 돌려보내기
        }
        if (view == ok) {
            String nickName = this.nickName.getText().toString();
            String id = this.id.getText().toString();
            String pwd = this.pwd.getText().toString();
            String age = this.age.getText().toString();
            String sex = "0";
            if(male.isChecked()) {
                sex = "1";
                female.setChecked(false);
            }else if(female.isChecked()){
                sex= "0";
                male.setChecked(false);
            }

            //네트워킹
            String[] arguments = {id, pwd,nickName,age, sex};
            signUpNetworking signUp = new signUpNetworking(arguments);
            signUp.execute();
        }
    }

    class signUpNetworking extends AsyncTask<Void, Void, Object> {
        URL url = null;
        HttpURLConnection urlConnection = null;
        String[] arguments;
        OutputStream outputStream = null;
        String response = null;

        public signUpNetworking(String[] arguments) {
            this.arguments = arguments;
        }

        @Override
        protected Object doInBackground(Void... voids) {
            try {
                Log.d("Log_dDoIn",arguments.toString());
                url = new URL("http://52.78.194.160:3000/user/signup");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Connection", "Keep-Alive");
                urlConnection.setConnectTimeout(20*1000);//20초
                urlConnection.setReadTimeout(20*1000);

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                /*
                HashMap<String, String> params = new HashMap<>();
                params.put("id", "sd");
                params.put("pwd", "aa");
                params.put("nickname", "asd");
                params.put("sex", "1");
                params.put("age", "25");*/

                outputStream = urlConnection.getOutputStream();
                String params = null;
                //hash map으로 해서 ,,
                for(int i =0 ; i<arguments.length; i++){
                    params = "id="+arguments[0]+"&pwd="+arguments[1]+"&nickname="+arguments[2]+"&age="+arguments[3]+"&sex="+arguments[4];
                }

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(params);
                outputStreamWriter.flush();
                urlConnection.connect();

                if(urlConnection.getResponseCode() == 201){
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    response = readStream(in);
                    JSONObject responseJson = new JSONObject(response);
                    String uid = responseJson.getString("ID");
                    String nick = responseJson.getString("Nickname");
                    String Uid = responseJson.getString("Uid");
                    //Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                    //Caused by: java.lang.RuntimeException: Can't toast on a thread that has not called Looper.prepare()

                    //intent로 home으로 보내거나,,

                }else if(urlConnection.getResponseCode() == 303){
                    //Toast.makeText(getApplicationContext(),"이미 존재하는 ID입니다",Toast.LENGTH_LONG);
                    //아이디 확인 버튼 만들기
                    //이미 아이디 있음
                }else {
                    //404나 그 외 예외처리하기.
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
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


