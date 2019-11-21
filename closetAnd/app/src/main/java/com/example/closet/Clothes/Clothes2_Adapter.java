package com.example.closet.Clothes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.closet.R;

import java.util.ArrayList;

class Clothes2_Adapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Integer> arrayList;
    private SparseBooleanArray mSelectedItemsIds;

    int[] imageIDs = null;

    public Clothes2_Adapter(Context context, int layout, ArrayList<Integer> arrayList) {

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.arrayList = arrayList;
        this.layout = layout;
        //this.imageIDs = imageIDs;
        mSelectedItemsIds = new SparseBooleanArray();
    }

    // 수정했음
    public int getImageCount() {
        return (null != imageIDs) ? imageIDs.length : 0;
    }

    public Object getImageItem(int position) {
        return (null != imageIDs) ? imageIDs[position] : 0;
    }

    public long getImageItemId(int position) {
        return position;
    }

    public int getCount() {
        return arrayList.size();
    }

    public Object getItem(int i) {
        return arrayList.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View gridView ;

        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) view.findViewById(R.id.clothes_iv);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.chk_clothes_iv);

            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

            viewHolder.imageView.setImageResource(arrayList.get(i)); // 보완 필요
            viewHolder.checkBox.setChecked(mSelectedItemsIds.get(i));

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkCheckBox(i, !mSelectedItemsIds.get(i));
                }
            });

        return view;
    }

    private class ViewHolder {
        private ImageView imageView;
        private CheckBox checkBox;
    }

    /**
     * Remove all checkbox Selection
     **/
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    /**
     * Check the Checkbox if not checked
     **/
    public void checkCheckBox(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, true);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    /**
     * Return the selected Checkbox IDs
     **/
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}