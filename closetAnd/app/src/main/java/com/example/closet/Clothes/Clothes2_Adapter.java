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
    private ArrayList<ClothesItem> arrayList;
    private SparseBooleanArray mSelectedItemsIds;

    int[] imageIDs = null;

    public Clothes2_Adapter(Context context, ArrayList<ClothesItem> arrayList, int[] imageIDs) {
        this.context = context;
        this.arrayList = arrayList;
        this.imageIDs = imageIDs;
        mSelectedItemsIds = new SparseBooleanArray();
    }

    GridView imagegrid = (GridView) findViewById(R.id.clothes_grid);
    imageAdapter = new ImageAdapter();
        imagegrid.setAdapter(imageAdapter);
        imagecursor.close();

    final Button selectBtn = (Button) findViewById(R.id.add);
        selectBtn.setOnClickListener(new

    OnClickListener() {

        public void onClick (View v){
            // TODO Auto-generated method stub
            final int len = thumbnailsselection.length;
            int cnt = 0;
            String selectImages = "";
            for (int i = 0; i < len; i++) {
                if (thumbnailsselection[i]) {
                    cnt++;
                    selectImages = selectImages + arrPath[i] + "|";
                }
            }
            if (cnt == 0) {
                Toast.makeText(getApplicationContext(),
                        "Please select at least one image",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "You've selected Total " + cnt + " image(s).",
                        Toast.LENGTH_LONG).show();
                Log.d("SelectedImages", selectImages);
            }
        }
    });


    // *** 수정했음 ***
    public int getArrayCount() {
        return arrayList.size();
    }

    public Object getArrayItem(int i) {
        return arrayList.get(i);
    }

    public long getArrayItemId(int i) {
        return i;
    }

    public View getArrayView(final int i, View view, ViewGroup viewGroup, int position) {
        ViewHolder viewHolder;
        ImageView imageView = null;

        if (view == null) {
            viewHolder = new ViewHolder();

            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), imageIDs[position]);
            bmp = Bitmap.createScaledBitmap(bmp, 320, 240, false);
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setImageBitmap(bmp);

            viewHolder.imageView = (ImageView) view.findViewById(R.id.clothes_iv);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.chk_clothes_iv);

            view.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) view.getTag();

        viewHolder.checkbox.setId(position);
        viewHolder.imageview.setId(position);
        viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                CheckBox cb = (CheckBox) v;
                int id = cb.getId();
                if (thumbnailsselection[id]){
                    cb.setChecked(false);
                    thumbnailsselection[id] = false;
                } else {
                    cb.setChecked(true);
                    thumbnailsselection[id] = true;
                }
            }
        });
        holder.imageview.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                int id = v.getId();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + arrPath[id]), "image/*");
                startActivity(intent);
            }
        });
        holder.imageview.setImageBitmap(thumbnails[position]);
        holder.checkbox.setChecked(thumbnailsselection[position]);
        holder.id = position;
        return convertView;
    }

    class ViewHolder {
        ImageView imageview;
        CheckBox checkbox;
        int id;
    }
}