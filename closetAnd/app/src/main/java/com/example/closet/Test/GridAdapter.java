package com.example.closet.Test;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.closet.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
//데이터와 화면을 연결할 어댑터 클래스 생성

    Context context ;
    ArrayList<Bitmap> datas = new ArrayList<>();
    //boolean visible = false;
    ArrayList<Integer> dataInt = new ArrayList<>();
    //int[] drawing = new int[] {R.drawable.example_01, R.drawable.example_04, R.drawable.example_07};

    public GridAdapter(Context context){
        this.context = context;
        this.dataInt.add(R.drawable.example_01);
        this.dataInt.add(R.drawable.example_04);
        dataInt.add(R.drawable.example_07);
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = new GridItem(context);

        return view;
    }
}
