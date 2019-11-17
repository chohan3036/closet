package com.example.closet.Clothes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageButton;
import com.example.closet.R;

class Clothes_GridAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    Context context;
    private boolean[] thumbnailsselection;
    private String[] arrPath;
    private Bitmap[] thumbnails;

    public int[] imglist = {R.drawable.example_01, R.drawable.example_04, R.drawable.example_07};

    public Clothes_GridAdapter(Context c) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imglist.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.clothes_griditem, null);
            holder.imageview = (ImageView) convertView.findViewById(R.id.clothes_iv);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.chk_clothes_iv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkbox.setId(position);
        holder.imageview.setId(position);
        holder.checkbox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                CheckBox cb = (CheckBox) v;
                int id = cb.getId();
                if (thumbnailsselection[id]) {
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
                context.startActivity(intent);
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
/* if (convertView == null) {
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
*/
