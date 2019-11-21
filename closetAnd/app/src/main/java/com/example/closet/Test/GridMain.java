package com.example.closet.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.closet.R;

public class GridMain extends AppCompatActivity {
    GridView gridView ;
    GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_main);

        gridView = (GridView)findViewById(R.id.gridTest);
        //int[] drawing = new int[] {R.drawable.example_01, R.drawable.example_04, R.drawable.example_07};
        adapter = new GridAdapter(this);
        gridView.setAdapter(adapter);
    }
}
