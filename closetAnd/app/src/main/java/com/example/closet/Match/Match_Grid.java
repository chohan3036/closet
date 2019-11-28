package com.example.closet.Match;

import android.os.Bundle;
import android.content.Intent;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.closet.R;

import java.net.URL;
import java.util.ArrayList;

public class Match_Grid extends AppCompatActivity {

    private Match_Adapter adapter;
    GridView gridView;

    ArrayList<URL> selected_from_clothes = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_my_pick);
        Intent intent = getIntent();
        selected_from_clothes = (ArrayList<URL>) intent.getSerializableExtra("selected_items");
        loadGridView();
    }

    private void loadGridView () {
        gridView = (GridView) findViewById(R.id.match_grid);
        adapter = new Match_Adapter(this, R.layout.match_griditem, selected_from_clothes);
        gridView.setAdapter(adapter);
    }

}

