package com.example.closet.History;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.closet.R;

class History_GridAdapter extends BaseAdapter {

    private Context context;
    public int[] imglist = {R.drawable.example_01, R.drawable.example_04, R.drawable.example_07};

    public History_GridAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return imglist.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(imglist[position]);
        return imageView;
    }
}