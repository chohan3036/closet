package com.example.closet.Match;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.closet.Networking_Get;
import com.example.closet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import com.example.closet.Networking_Get;
import com.example.closet.R;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Match_Grid extends AppCompatActivity {
    private PopupWindow mPopupWindow;
    View view;
    private Match_Adapter adapter;
    ArrayList<URL> photoUrls = new ArrayList<>();
    GridView gridView;

    String selectedImagesPaths;
    boolean imagesSelected = false;
    private Uri uri;
    Bitmap bitmap;

    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    //  view = inflater.inflate(R.layout.fragment_match, container, false);
    //return view;
    // }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_my_pick);
        loadGridView();
    }

        private void loadGridView () {
            gridView = (GridView) findViewById(R.id.gridview1);
            adapter = new Match_Adapter(this);
            gridView.setAdapter(adapter);

        }
}

