package com.example.closet.Recommend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.closet.R;

public class recommend_SingViewActivity extends Activity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.recommend_griditem);

            // Get intent data
            Intent i = getIntent();

            // Selected image id
            int position = i.getExtras().getInt("id");
            recommend_GridAdapter imageAdapter = new recommend_GridAdapter(this);

            ImageView imageView = (ImageView) findViewById(R.id.SingleView);
            imageView.setImageResource(imageAdapter.mThumbIds[position]);
        }
    }