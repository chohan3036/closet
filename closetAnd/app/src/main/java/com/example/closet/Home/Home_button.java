package com.example.closet.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.closet.Clothes.Clothes;
import com.example.closet.History.History_GridItem;
import com.example.closet.Match.Match_Mypick;
import com.example.closet.R;

public class Home_button extends AppCompatActivity {

    private Button BtnMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        BtnMove = findViewById(R.id.BtnActivityOne);

        BtnMove.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                moveToClothes();
            }
        });
    }
    private void moveToClothes(){
        Intent intent   = new Intent(com.example.closet.Home.Home_button.this, Clothes.class);
        startActivity(intent);
    }
}