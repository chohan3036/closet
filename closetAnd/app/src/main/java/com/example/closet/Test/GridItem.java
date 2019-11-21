package com.example.closet.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.closet.R;

public class GridItem extends LinearLayout {
    View view;
    public GridItem(Context context) {
        super(context);

        view = LayoutInflater.from(context).inflate(R.layout.activity_grid_item,this);



    }

}
