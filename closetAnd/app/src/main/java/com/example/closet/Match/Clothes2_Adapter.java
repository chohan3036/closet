package com.example.closet.Match;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.closet.R;

class Match_Adapter extends BaseAdapter {

    private Context mcontext;
    private LayoutInflater inflater;
    private int layout;
    private SparseBooleanArray mSelectedItemsIds;


    public Match_Adapter(Context context) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         mcontext = context;

    }
    public int getCount() {
       return mThumbIds.length;
   }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mcontext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
       // imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
    public Integer[] mThumbIds = {
            R.drawable.closet, R.drawable.closet

    };
}