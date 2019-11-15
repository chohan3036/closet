package com.example.closet.History;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.closet.R;

public class History_SingleViewActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_griditem);

        // Get intent data
        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");
        History_GridAdapter gridAdapter = new History_GridAdapter(this);

        ImageView imageView = (ImageView) findViewById(R.id.history_iv);
        imageView.setImageResource(gridAdapter.imglist[position]);
    }
}