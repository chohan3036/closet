package com.example.closet.Match;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.closet.Clothes.Clothes_Adapter;
import com.example.closet.Clothes.selected_items;
import com.example.closet.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Match_Grid extends AppCompatActivity {

    private Match_Adapter adapter;
    GridView gridView;

    ArrayList<URL> selected_from_clothes = new ArrayList<>();
    ArrayList<Integer> match_checked_items;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_my_pick);
        //Intent intent = getIntent();
        //selected_from_clothes = (ArrayList<URL>) intent.getSerializableExtra("selected_items");
        selected_from_clothes = selected_items.selected_from_clothes;
        loadGridView();
    }

    private void loadGridView() {
        gridView = (GridView) findViewById(R.id.match_grid);
        if (selected_from_clothes == null) {
            Toast.makeText(this, "선택된 옷이 없습니다", Toast.LENGTH_LONG).show();
        } else {
            adapter = new Match_Adapter(this, R.layout.match_griditem, selected_from_clothes);
            gridView.setAdapter(adapter);
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select:
                match_checked_items = Match_Adapter.match_checked_items;
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList("photo", match_checked_items);
                Match match = new Match();
                match.setArguments(bundle);
                Toast.makeText(this, "옷이 선택되었습니다", Toast.LENGTH_LONG).show();

                checked_items = Clothes_Adapter.checked_items;
                try {
                    for (int i = 0; i < match_checked_items.size(); i++) {
                        //Log.d("Log_dDDaaaaa", i + ":" + (checked_items.get(i)));
                        JSONObject eachClothing = null;
                        //int check_index = checked_items.get(i);
                        eachClothing = clothingResults.getJSONObject(checked_items.get(i));
                        String photoFile = eachClothing.getString("photo");
                        selected_to_match.add(new URL(photoFile));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}

