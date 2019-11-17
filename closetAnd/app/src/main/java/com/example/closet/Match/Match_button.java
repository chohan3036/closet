package com.example.closet.Match;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.closet.History.History_GridItem;
import com.example.closet.R;

public class Match_button extends AppCompatActivity {

    private Button BtnMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_match);

        BtnMove = findViewById(R.id.BtnActivityOne);
        BtnMove = findViewById(R.id.BtnActivityThree);

        BtnMove.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                moveToMatch_Mypick();
            }
        });

        BtnMove.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                moveTohistroty_GridItem();
            }
        });
    }
    private void moveToMatch_Mypick(){
        Intent intent   = new Intent(Match_button.this, Match_Mypick.class);
            startActivity(intent);
        }

    private  void moveTohistroty_GridItem(){
        Intent intent = new Intent(Match_button.this, History_GridItem.class);
        startActivity(intent);
    }
    }

