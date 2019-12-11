package com.example.closet.Clothes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UrlToBitmap2 extends AsyncTask<Void, Void, Bitmap> {
    URL url;
    Bitmap bitmap;

    public UrlToBitmap2(URL url) {
        this.url = url;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
                e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap o) {
        super.onPostExecute(o);
    }
}
