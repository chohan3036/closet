package com.example.closet.History;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.closet.R;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.appcompat.app.AppCompatActivity;

import com.example.closet.Networking_Get;
import com.example.closet.R;

import java.net.MalformedURLException;
import java.net.URL;


public class History extends Fragment {

    View view;


    public int[] imageIDs = new int[]{R.drawable.example_01, R.drawable.example_04, R.drawable.example_07};

    public History() {
        // Required empty public constructor
    }


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
        //setContentView(R.layout.activity_clothes);
        //Intent it;
        //String text = it.getStringEXTR("text");
        //TextView mTextview = (TextView) view.findViewById(R.id.history_tv);
        //mTextview.setText(text);

        //GridView gridViewImages = (GridView) findViewById(R.id.clothes_grid);
        //GridAdapter imageGridAdapter = new GridAdapter(this, imageIDs);
        //gridViewImages.setAdapter(imageGridAdapter);
        return view;
    }

        }