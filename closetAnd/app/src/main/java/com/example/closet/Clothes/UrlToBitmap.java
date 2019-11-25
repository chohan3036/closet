package com.example.closet.Clothes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UrlToBitmap extends AsyncTask<Void, Void, ArrayList<Bitmap>> {
    ArrayList<URL> photoUrls;
    ArrayList<Bitmap> photoBitmap = new ArrayList<>();

    public UrlToBitmap(ArrayList<URL> photoUrls) {
        this.photoUrls = photoUrls;
    }

    @Override
    protected ArrayList<Bitmap> doInBackground(Void... voids) {

        for (int i = 0; i < photoUrls.size(); i++) {
            try {
                HttpURLConnection connection = (HttpURLConnection) photoUrls.get(i).openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                photoBitmap.add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return photoBitmap;
    }

    @Override
    protected void onPostExecute(ArrayList<Bitmap> o) {
        super.onPostExecute(o);
    }
}