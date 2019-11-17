package com.example.closet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    Button cancel, ok;
    EditText nickName, id, pwd, height, weight, age;
    CheckBox female, male;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }

    public void setting() {
        cancel = (Button) findViewById(R.id.signup_cancel);
        ok = (Button) findViewById(R.id.signup_ok);
        nickName = (EditText) findViewById(R.id.et_nickname);
        id = (EditText) findViewById(R.id.et_id);
        pwd = (EditText) findViewById(R.id.et_password);
        height = (EditText) findViewById(R.id.et_height);
        weight = (EditText) findViewById(R.id.et_weight);
        age = (EditText) findViewById(R.id.et_age);
        female = (CheckBox) findViewById(R.id.chk_female);
        male = (CheckBox) findViewById(R.id.chk_male);

    }

    @Override
    public void onClick(View view) {

        if (view == cancel) {
            //intent로 돌려보내기
        }
        if (view == ok) {
            //네트워킹
        }
    }

    class signUpNetworking extends AsyncTask<Void,Void,Object>{
        URL url = null;
        HttpURLConnection urlConnection = null;
        String[] arguments;

        public signUpNetworking(String[] arguments){
            this.arguments = arguments;
        }
        @Override
        protected Object doInBackground(Void... voids) {
            try {
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("POST");
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}

