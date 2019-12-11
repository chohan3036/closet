package com.example.closet.Recommend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.closet.GetUID;
import com.example.closet.Recommend.Recommend_GridAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Recommend extends Fragment implements AdapterView.OnItemSelectedListener {

    String uid;
    View view;
    int[] recommend_spinner = new int[]{R.drawable.recommend_beige, R.drawable.thumb_on, R.drawable.thumb_off};
    int spinner_id = 0;
    URL url = null;
    Spinner recom_spinner;

    ArrayList<URL> photoUrls = new ArrayList<>();
    private Context context;
    private Recommend_GridAdapter adapter;
    ArrayList<String> hidList =new ArrayList<>();

    public Recommend() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid = UID;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_recommend, container, false);
        setting();
        loadGridView();
        return  view;
    }
/*
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setting();
        loadGridView(view);
    }
*/
    private void setting(){
        recom_spinner = (Spinner) view.findViewById(R.id.recommend_spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.recommend));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recom_spinner.setAdapter(dataAdapter);
        recom_spinner.setOnItemSelectedListener(this);
    }

    public void showRecommendList(JSONArray jsonArray) {
    //그리드뷰 업데이트 될 때마다 photoUrl clear
        photoUrls.clear();
        hidList.clear();
        for(int i=0 ; i<jsonArray.length() ; i++){
            try {
                JSONObject eachRecommendedClothing = jsonArray.getJSONObject(i);
                String photoFIle = eachRecommendedClothing.getString("photo_look");
                String hid = eachRecommendedClothing.getString("hid");
                //Log.d("Log_dPhotoFile",photoFIle);
                if("null".equals(photoFIle)){
                    //Log.d("Log_dNULLSTRINGhhhhhhhh",photoFIle);
                }
                else {
                    photoUrls.add(new URL(photoFIle));
                    hidList.add(hid);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        loadGridView();
    }
    private void loadGridView() {

        GridView gridView = (GridView) view.findViewById(R.id.recommend_grid);
        adapter = new Recommend_GridAdapter(getContext(), photoUrls,hidList);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //gridView.invalidateViews();//?
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String string = adapterView.getItemAtPosition(i).toString();
        //처음에 들어갔을 때 어떻게 보여주지?
        try {

            if (string.equals("나이/성별")) {
                url = new URL("http://52.78.194.160:3030/recommendByUserInfo?uid=" + uid);
            } else if (string.equals("코디 기반")) {
                url = new URL("http://52.78.194.160:3030/recommendByLookTable?uid=" + uid);
            } else if(string.equals("좋아요 순")){
                url = new URL("http://52.78.194.160:3000/closet/like/recommendByLike");
            }
            Networking_Get networking = new Networking_Get(url);
            networking.execute();
            JSONObject result = networking.get();

            if (result!=null) {
                Log.d("Log_dRECOMMEND", result.toString());
                //: {"message":"successfully selected","result":[{"hid":37,"uid":3,"like":11,"look_name":"daily,campus","pho
                JSONArray jsonArray = result.getJSONArray("result");
                Log.d("Log_dResultArray", jsonArray.toString());
                showRecommendList(jsonArray);
                //[{"down_cid":22,"hid":18,"like":4,"look_name":"romantic,office,daily","outer_cid":0,"photo_look":null,"uid":2,"up_cid":20},{"down_cid":21,"hid":22,"like":3,"look_name":"office,daily
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

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}