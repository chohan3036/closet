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

import com.example.closet.Clothes.UrlToBitmap;
import com.example.closet.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class Recommend_GridAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater inflater;
    ArrayList<URL> photoUrls;
    private ArrayList<Bitmap> photoBitmap = new ArrayList<>();
    private UrlToBitmap urlToBitmap;
    private ViewHolder viewHolder = new ViewHolder();
    boolean showing = false;

    public Recommend_GridAdapter(Context context, ArrayList<URL> photoUrls) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.photoUrls = photoUrls;
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

            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();


        viewHolder.imageView.setImageBitmap(photoBitmap.get(i));
        viewHolder.imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.imageButton.setSelected(!viewHolder.imageButton.isSelected());
            }
        });

        return view;
    }

    /*        for (int j = 0 ; j<photoUrls.size();j++){
               Log.d("Log_dPhotoUrls",i+"\n"+photoUrls);
           }

           for (int j = 0 ; j<photoBitmap.size();j++){
               Log.d("Log_dPhotoBitmap",i+"\n"+photoBitmap);         */
    private class ViewHolder {
        private ImageView imageView;
        private ImageButton imageButton;
    }

}