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

import org.json.JSONObject;

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
    String uid = "1" ; // 받아오기


    public Recommend_GridAdapter(Context context, ArrayList<URL> photoUrls,ArrayList<String> hidList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.photoUrls = photoUrls;
        this.hidList = hidList;

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

    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.recommend_griditem, viewGroup, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.recommend_iv);
            viewHolder.imageButton = (ImageButton) view.findViewById(R.id.likeit);
            viewHolder.textView= (TextView)view.findViewById(R.id.rec_item_text);

            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();
        viewHolder.imageView.setImageBitmap(photoBitmap.get(i));
        viewHolder.textView.setText("코디 정보? ");
        viewHolder.imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) { //네트워킹
                try {
                    HashMap<String,String> arugments = new HashMap<String,String>();
                    arugments.put("uid",uid);
                    arugments.put("hid","11"); //아으아아아각ㄱ
                    Networking networking = new Networking(new URL("http://52.78.194.160:3000/closet/like/makeLike"),arugments);
                    networking.execute();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                //viewHolder.imageButton.setSelected(!viewHolder.imageButton.isSelected());
                viewHolder.imageButton.setImageResource(R.drawable.full_like);
            }
        });

        return view;
    }


    public void likeList(){
        //네트워킹해서 해당 uid가 좋아요 한 목록 뽑고 ,, 비교해서 하트 이미지 설정
        try {
            URL url = new URL ("http://52.78.194.160:3000like/myLikeList/"+uid);
            Networking_Get networking_get = new Networking_Get(url);
            networking_get.execute();
            JSONObject jsonObject = networking_get.get();
            Log.d("Log_dLikeLIst",jsonObject.toString());

            /*
            * {
    "message": "like list 반환 성공",
    "result": [
        17,
        18,
        19
    ]
}
            * */
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    /*        for (int j = 0 ; j<photoUrls.size();j++){
               Log.d("Log_dPhotoUrls",i+"\n"+photoUrls);
           }

           for (int j = 0 ; j<photoBitmap.size();j++){
               Log.d("Log_dPhotoBitmap",i+"\n"+photoBitmap);         */

    private class ViewHolder {
        private ImageView imageView;
        private ImageButton imageButton;
        private TextView textView;
    }

}