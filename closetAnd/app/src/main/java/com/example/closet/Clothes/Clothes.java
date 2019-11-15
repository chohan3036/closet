package com.example.closet.Clothes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import com.example.closet.R;

public class Clothes extends AppCompatActivity {

    View view;
    ImageView clothesIv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.clothes_grid);
        gridview.setAdapter(new Clothes_GridAdapter(this));

        Button btn1 = (Button) findViewById(R.id.add) ;
        Button btn2 = (Button) findViewById(R.id.mypick) ;

        btn1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });

        btn2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        clothesIv.setImageURI(data.getData());
    }

}

