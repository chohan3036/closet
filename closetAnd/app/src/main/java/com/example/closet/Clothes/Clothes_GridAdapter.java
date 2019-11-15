package com.example.closet.Clothes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import com.example.closet.R;

class Clothes_GridAdapter extends BaseAdapter {

    private Context context;
    public int[] imglist = {R.drawable.example_01, R.drawable.example_04, R.drawable.example_07};

    public Clothes_GridAdapter(Context c) {
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
        ImageButton imgBtn;

        if (convertView == null) {
            imgBtn = new ImageButton(context);
            imgBtn.setScaleType(ImageButton.ScaleType.CENTER_CROP);
            imgBtn.setPadding(8, 8, 8, 8);
        }
        else
        {
            imgBtn = (ImageButton) convertView;
        }
        imgBtn.setImageResource(imglist[position]);
        return imgBtn;
    }
}