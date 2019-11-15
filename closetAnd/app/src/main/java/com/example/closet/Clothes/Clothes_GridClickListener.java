package com.example.closet.Clothes;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.example.closet.MainActivity;

public class Clothes_GridClickListener {

    Context context;
    int imageID;

    public Clothes_GridClickListener(Context context, int imageID) {
        this.context = context;
        this.imageID = imageID;
    }
    public void onClick(View v) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("image ID", imageID);
        context.startActivity(intent);
    }

}
