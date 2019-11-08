package com.example.closet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private Uri mImageCaptureUri;
    ImageView imageView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView = (ImageView)findViewById(R.id.imageTest);
        doTakeAlbumAction();

    }
    private void doTakeAlbumAction()

    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_ALBUM) {
            if (data == null) {
                Log.d("Log_d data", "data is null");
            }
            else {
                mImageCaptureUri = data.getData();
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),mImageCaptureUri);
                    Log.d("Log_d bitmap객체 : ",bitmap.toString());
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            /*Log.d("Log_dsssssss4",mImageCaptureUri.toString());
                Log.d("Log_dsssssss0",data.toString());
                Log.d("Log_dsssssss1",data.getExtras().toString());
                Bundle bundle = data.getExtras();
                Log.d("log_dssssssss2",bundle.toString());

                Bitmap bitmap = (Bitmap)bundle.get("data");
                //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Log.d("Log_d bitmap", bitmap.toString());
                */
            }

        }
    }
}
