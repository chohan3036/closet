package com.example.closet.History;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.example.closet.R;

public class History extends Fragment {

    View view;
    public int[] imageIDs = new int[]{R.drawable.example_01, R.drawable.example_04, R.drawable.example_07};

    public History() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match, container, false);
        GridView gridview = (GridView) view.findViewById(R.id.clothes_grid);
        gridview.setAdapter(new History_GridAdapter(this, imageIDs));

        return view;
    }
}
