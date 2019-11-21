package com.example.closet.History;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.closet.R;

public class History extends Fragment {

    View view;


    public int[] imageIDs = new int[]{R.drawable.example_01, R.drawable.example_04, R.drawable.example_07};

    public History() {
        // Required empty public constructor
    }
}
/*
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_history, container, false);

        return view;
    }

}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        GridView gridViewImages = (GridView) findViewById(R.id.clothes_grid);
        GridAdapter imageGridAdapter = new GridAdapter(this, imageIDs);
        gridViewImages.setAdapter(imageGridAdapter);
    }
 */