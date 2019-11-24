package com.example.closet.Recommend;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.closet.Clothes.GridClickListener;
import com.example.closet.Clothes.UrlToBitmap;
import com.example.closet.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class Recommend_GridAdapter extends BaseAdapter {

    Context context = null;
    private LayoutInflater inflater;
    private int layout;

    ArrayList<String> arrayTextList = null;
    ArrayList<URL> photoUrls;
    ArrayList<Bitmap> photoBitmap = new ArrayList<>();
    UrlToBitmap urlToBitmap;

    public Recommend_GridAdapter(Context context,  ArrayList<String> arrayTextList, ArrayList<URL> photoUrls) {
        this.context = context;
        this.arrayTextList = arrayTextList;
        this.photoUrls = photoUrls;
        Log.d("Log_dasagagadg", String.valueOf(this.photoUrls.get(0)));

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

    /*
    public int getCount() { return (null != imageIDs) ? imageIDs.length : 0; }

    public Object getItem(int position) {
        return (null != imageIDs) ? imageIDs[position] : 0;
    }

    public long getItemId(int position) {
        return position;
    }
    */

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
        ViewHolder viewHolder;
        View gridView;

        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
            viewHolder = new Recommend_GridAdapter.ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.recommend_iv);
            viewHolder.textView = (TextView) view.findViewById(R.id.like);
            viewHolder.button = (LikeButton) view.findViewById(R.id.like_button);
            view.setTag(viewHolder);
        } else
            viewHolder = (Recommend_GridAdapter.ViewHolder) view.getTag();
        viewHolder.imageView.setImageBitmap(photoBitmap.get(i));

        LikeButton likeButton = (LikeButton) view.findViewById(R.id.like_button);
        likeButton.setOnLikeListener(new OnLikeListener(){
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });

        return view;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private LikeButton button;
    }

}