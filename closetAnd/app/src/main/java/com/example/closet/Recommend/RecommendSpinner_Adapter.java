package com.example.closet.Recommend;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.closet.R;

public class RecommendSpinner_Adapter extends ArrayAdapter<String>{


    String[] spinnerNames;
    int[] spinnerImages;
    Context mContext;

    public RecommendSpinner_Adapter(Context context, String[] names, int[] images) {
        super(context, R.layout.spinner_row2);

        this.spinnerNames = names;
        this.spinnerImages = images;
        this.mContext = context;
    }

    @Override
    public View getDropDownView(int position,  View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return spinnerNames.length;
    }


    @Override
    public View getView(int position,  View convertView,ViewGroup parent) {

        ViewHolder mViewHolder = new ViewHolder();

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_row, parent, false);

            mViewHolder.mImage = (ImageView) convertView.findViewById(R.id.imageview_spinner_image);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.textview_spinner_name);
            convertView.setTag(mViewHolder);

        } else {

            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.mImage.setImageResource(spinnerImages[position]);
        mViewHolder.mName.setText(spinnerNames[position]);

        return convertView;
    }

    private static class ViewHolder {

        ImageView mImage;
        TextView mName;
    }
}