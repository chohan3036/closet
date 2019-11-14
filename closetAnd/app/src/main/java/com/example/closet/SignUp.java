package com.example.closet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText nameTxt = (EditText) findViewById(R.id.et_nickname);
        EditText idTxt = (EditText) findViewById(R.id.et_id);
        EditText passwordTxt = (EditText) findViewById(R.id.et_password);
        EditText heightTxt = (EditText) findViewById(R.id.et_height);
        EditText weightTxt = (EditText) findViewById(R.id.et_weight);
        EditText AgeTxt = (EditText) findViewById(R.id.et_age);
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.chk_female);
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.chk_male);
        checkBox1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                }
            }
        });
        checkBox2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                }
            }
        });
    }

}
