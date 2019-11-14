package com.example.closet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.closet.Clothes.Clothes;
import com.example.closet.Recommend.recommend_GridAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button;
    ImageButton imageButton;
    private ViewPager viewPager;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new recommend_GridAdapter(this));

        imageButton =(ImageButton)findViewById(R.id.imageButton);
        button = (Button)findViewById(R.id.testbutton);
        button.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        // Example of a call to a native method
        //TextView tv = findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }

    public void onClick(View view) {
        if(view == button){
            Intent intent = new Intent(this,GetaLocation.class );
            Intent intent1 = new Intent(this, Clothes.class);
            //startActivityForResult(intent,30);
            startActivity(intent);
        }

    }
}