package com.example.closet.Recommend;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.closet.Clothes.UrlToBitmap;
import com.example.closet.Networking;
import com.example.closet.Networking_Get;
import com.example.closet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

class Recommend_GridAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater inflater;
    ArrayList<URL> photoUrls;
    private ArrayList<Bitmap> photoBitmap = new ArrayList<>();
    private UrlToBitmap urlToBitmap;
    private ViewHolder viewHolder = new ViewHolder();
    boolean showing = false;
    ArrayList<String> hidList = new ArrayList<>();
    String uid = "1"; // 받아오기


    private ArrayList<Integer> likedHid = new ArrayList<>();//  그 사용자가 좋아요  한 list

    public Recommend_GridAdapter(Context context, ArrayList<URL> photoUrls, ArrayList<String> hidList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.photoUrls = photoUrls;
        this.hidList = hidList;
        likedHid = likeList(); //여기있으면,,

        urlToBitmap = new UrlToBitmap(photoUrls);
        urlToBitmap.execute();
        try {
            photoBitmap = urlToBitmap.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public int getCount() {
        return photoUrls.size();
    }

    public Object getItem(int i) {
        return photoUrls.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(final int i, View view, final ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.recommend_griditem, viewGroup, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.recommend_iv);
            viewHolder.imageButton = (ImageButton) view.findViewById(R.id.likeit);
            viewHolder.textView = (TextView) view.findViewById(R.id.rec_item_text);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        viewHolder.imageView.setImageBitmap(photoBitmap.get(i));
        viewHolder.textView.setText("코디 정보? ");
        for (int j = 0; j < likedHid.size(); j++) {
            if (Integer.parseInt(hidList.get(i)) == likedHid.get(j)) {
                //Log.d("Log_dLikeHids", String.valueOf(likedHid.get(j)) + "\n" + j);
                //Log.d("Log_dHidList", hidList.get(i) + "\n" + i);
                viewHolder.imageButton.setImageResource(R.drawable.full_like);
            }
        }
        viewHolder.imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageButton selected;
                try {
                    //37,21,17,20,22,19,18,50
                    HashMap<String, String> arugments = new HashMap<String, String>();
                    arugments.put("uid", uid);
                    arugments.put("hid", hidList.get(i));
                    Networking networking = new Networking(new URL("http://52.78.194.160:3000/closet/like/makeLike"), arugments);
                    networking.execute();
                    JSONObject jsonObject = networking.get();
                    if (String.valueOf(jsonObject.get("like_status")).equals("1")) {
                        //viewHolder.imageButton.setImageResource(R.drawable.full_like);
                        selected = (ImageButton)view.findViewById(view.getId());
                        selected.setImageResource(R.drawable.full_like);

                    } else {
                        selected = (ImageButton)view.findViewById(view.getId());
                        selected.setImageResource(R.drawable.emptylike);
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
        });

        return view;
    }

    public void ChangeLikeStatus() {

    }

    public ArrayList<Integer> likeList() {

        //네트워킹해서 해당 uid가 좋아요 한 목록 뽑고 ,, 비교해서 하트 이미지 설정
        try {
            likedHid.clear();
            URL url = new URL("http://52.78.194.160:3000/closet/like/myLikeList/" + uid);
            Networking_Get networking_get = new Networking_Get(url);
            networking_get.execute();

            JSONObject jsonObject = networking_get.get();
            JSONArray result = null;
            if (jsonObject != null)
                result = (JSONArray) jsonObject.get("result");
            //Log.d("Log_dWWWWWW",result.toString());

            for (int i = 0; i < result.length(); i++) {
                Log.d("Log_dLikeListItem", String.valueOf(result.get(i)));
                likedHid.add((Integer) result.get(i));
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
        return likedHid;

    }

    /*
    @Override
    public void onClick(View view) {
        Log.d("Log_dCLIKCEKKDCC", String.valueOf(view.getId()));
        try {
            //37,17,21,19,20
            HashMap<String, String> arugments = new HashMap<String, String>();
            arugments.put("uid", uid);
            arugments.put("hid", hidList.get(i));
            Log.d("Log_dSeleceted", hidList.get(i));
            Networking networking = new Networking(new URL("http://52.78.194.160:3000/closet/like/makeLike"), arugments);
            networking.execute();
            JSONObject jsonObject = networking.get();
            Log.d("Log_dOOOOo", String.valueOf(jsonObject.get("like_status")));

            //if(jsonObject.get("like_status") == (Integer)1){
            if(String.valueOf(jsonObject.get("like_status")).equals("1")){ //return view 전이라서 바로 view update가 안됨..
                Log.d("Log_dMakeLike","good");
                viewHolder.imageButton.setImageResource(R.drawable.full_like);
            }
            else
                viewHolder.imageButton.setImageResource(R.drawable.emptylike);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }*/

    private class ViewHolder {
        private ImageView imageView;
        private ImageButton imageButton;
        private TextView textView;
    }

}