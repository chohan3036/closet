package com.example.closet.History;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.closet.MainActivity;

public class History_GridClickListener implements  OnClickListener {

    Context context;
    int imageID;

    public History_GridClickListener(Context context, int imageID) {
        this.context = context;
        this.imageID = imageID;
    }
    public void onClick(View v) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("image ID", imageID);
        context.startActivity(intent);
    }
}