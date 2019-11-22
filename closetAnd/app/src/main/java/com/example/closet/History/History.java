package com.example.closet.History;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.closet.R;

import java.net.URL;
import java.util.ArrayList;

public class History extends Fragment {

    View view;
    private Context context;
    private ArrayList<String> arrayTextList;
    ArrayList<URL> photoUrls = new ArrayList<>();
    private History_GridAdapter adapter;

    public History() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadGridView(view);
    }

    private void loadGridView(View view) {
        GridView gridView = (GridView) view.findViewById(R.id.history_grid);
        arrayTextList = new ArrayList<>();
        for (int i = 1; i <= photoUrls.size()+1; i++)
            arrayTextList.add("History Items " + i);
        History_GridAdapter imageGridAdapter = new History_GridAdapter(context, arrayTextList, photoUrls);
        gridView.setAdapter(imageGridAdapter);
    }

}
