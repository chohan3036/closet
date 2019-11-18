package com.example.closet.Clothes;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class GridClickListener implements  OnClickListener {

    Context context;
    int imageID;

    public GridClickListener(Context context, int imageID) {
        this.context = context;
        this.imageID = imageID;
    }
    public void onClick(View v) {
        Intent intent = new Intent(context, Clothes.class);
        intent.putExtra("image ID", imageID);
        context.startActivity(intent);
    }
}
