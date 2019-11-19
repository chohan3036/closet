/* package com.example.closet.Clothes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

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

    public int getCount() {
        return (null != imageIDs) ? imageIDs.length : 0;
    }

    public Object getItem(int position) {
        return (null != imageIDs) ? imageIDs[position] : 0;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;

        if (null != convertView)
            imageView = (ImageView) convertView;
        else {
            // GridView 뷰를 구성할 ImageView 뷰의 비트맵을 정의합니다.
            // 그리고 그것의 크기를 320*240으로 줄입니다.
            // 크기를 줄이는 이유는 메모리 부족 문제를 막을 수 있기 때문입니다.
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), imageIDs[position]);
            bmp = Bitmap.createScaledBitmap(bmp, 320, 240, false);

            // GridView 뷰를 구성할 ImageView 뷰들을 정의합니다.
            // 뷰에 지정할 이미지는 앞에서 정의한 비트맵 객체입니다.
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setImageBitmap(bmp);
        }

        // 사진 항목들의 클릭을 처리하는 ImageClickListener 객체를 정의합니다.
        // 그리고 그것을 ImageView의 클릭 리스너로 설정합니다.
        GridClickListener imageViewClickListener = new GridClickListener(context, imageIDs[position]);
        imageView.setOnClickListener(imageViewClickListener);

        return imageView;
    }

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

            viewHolder.imageView = imageView;
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.chk_clothes_iv);

            view.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) view.getTag();

        viewHolder.imageView.setImageBitmap(arrayList.getPictureResId(i));
        viewHolder.checkBox.setChecked(mSelectedItemsIds.get(i));

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(i, !mSelectedItemsIds.get(i));
            }
        });

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            GridClickListener imageViewClickListener = new GridClickListener(context, imageIDs[position]);
            imageView.setOnClickListener(imageViewClickListener);
        });

        return view;
    }

    //Remove all checkbox Selection
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    //Check the Checkbox if not checked
    public void checkCheckBox(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, true);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    //Return the selected Checkbox IDs
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    private class ViewHolder {
        private ImageView imageView;
        private CheckBox checkBox;
    }
}
*/