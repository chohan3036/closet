package com.example.closet.History;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.example.closet.Networking_Get;
import com.example.closet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.closet.MainActivity.UID;

public class History extends Fragment {

    String uid;
    private Context context;
    ArrayList<URL> photoUrls = new ArrayList<>();
    private ArrayList<String> arrayTextList;
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
        getClothings(null);
        loadGridView(view);

    }
    private void getClothings(String category) {
        try {
            uid = UID; // 고치기
            Log.d("uid",uid);
            //결과없으면 match에서 코디를 만들고 저장하라고 알려주기
            URL url = new URL("http://52.78.194.160:3000/closet/show/personalHistory/"+uid);
            Networking_Get networking = new Networking_Get(url);
            networking.execute();
            JSONObject result = networking.get();
            JSONArray clothingResults = (JSONArray) result.get("result");
            //Log.d("Log_d_jsonarray", String.valueOf(clothingResults));
            for (int i = 0; i < clothingResults.length(); i++) {
                JSONObject eachClothing = clothingResults.getJSONObject(i);
                String photoFile = eachClothing.getString("photo_look");
                //Log.d("Log_dPhotoFile",photoFile);
                if(!"null".equals(photoFile))
                    photoUrls.add(new URL(photoFile));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadGridView(View view) {
        GridView gridView = (GridView) view.findViewById(R.id.history_grid);
        arrayTextList = new ArrayList<>();
        for (int i = 1; i <= photoUrls.size()+1; i++)
            arrayTextList.add("History Items " + i);
        adapter = new History_GridAdapter(context, photoUrls, arrayTextList);
        gridView.setAdapter(adapter);
        photoUrls.clear();//여기?
    }
}

