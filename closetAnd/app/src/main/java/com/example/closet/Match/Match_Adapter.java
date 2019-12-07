package com.example.closet.Match;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentTransaction;

import com.example.closet.Clothes.UrlToBitmap;
import com.example.closet.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class Match_Adapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private LinearLayout sublayout = new LinearLayout(context);
    private int layout;
    private SparseBooleanArray mSelectedItemsIds;

    ImageView top, bottom;

    ArrayList<URL> selected_from_clothes;
    ArrayList<Bitmap> photoBitmap = new ArrayList<>();
    UrlToBitmap urlToBitmap;

    static public ArrayList<Integer> match_checked_items = new ArrayList<>(); //match에서 쓸 수 있게 static

    public Match_Adapter(Context context, int layout, ArrayList<URL> selected_from_clothes) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.layout = layout;
        this.selected_from_clothes = selected_from_clothes;
        mSelectedItemsIds = new SparseBooleanArray();

        urlToBitmap = new UrlToBitmap(selected_from_clothes);
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
    private void fileToBitmap(){
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        for(int i = 0 ; i<photoUrls.size(); i++) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoUrls.get(i).getPath(), bmOptions);
            System.out.println(photoUrls.get(i).getPath());
            System.out.println(photoUrls.get(i).getAbsolutePath());
            try {
                System.out.println(photoUrls.get(i).getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //bitmap= Bitmap.createScaledBitmap(bitmap,)
            Log.d("Log_dBITMAP",bitmap.toString());
            photoBitmap.add(bitmap);
        }
    }*/

    public int getCount() {
        return selected_from_clothes.size();
    }

    public Object getItem(int i) {
        return selected_from_clothes.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(final int i, View view, final ViewGroup viewGroup) {
        ViewHolder viewHolder;
        //checked_items = new ArrayList<>(); 어디선가 초기화해줘야하는데,,

        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.match_iv);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.chk_match_iv);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();
        viewHolder.imageView.setImageBitmap(photoBitmap.get(i));
        viewHolder.checkBox.setChecked(mSelectedItemsIds.get(i));

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(i, !mSelectedItemsIds.get(i));
                View sub = inflater.inflate(R.layout.fragment_match, sublayout);
                top = (ImageView) sub.findViewById(R.id.match_top);
                bottom = (ImageView) sub.findViewById(R.id.match_down);
                top.setImageBitmap(photoBitmap.get(i));
            }
        });

        if(viewHolder.checkBox.isChecked()){
            checkCheckBox(i, !mSelectedItemsIds.get(i));
        }

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

        //i는 0부터 체크박스 번호. 여기서는 position인가?
        //parent로 보내서 networking으로 받은거 정보 넘겨주면 될듯?

        if (value) {
            mSelectedItemsIds.put(position, true);
            match_checked_items.add((Integer)position);
        } else {
            mSelectedItemsIds.delete(position);
            match_checked_items.remove((Integer)position);
        }
        notifyDataSetChanged();
        //for(int i =0 ; i <checked_items.size() ; i++)
        //    Log.d("Log_dDD",i +"==="+(checked_items.get(i)));
    }

    /**
     * Return the selected Checkbox IDs
     **/
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}