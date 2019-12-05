package com.example.closet.History;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.closet.Clothes.UrlToBitmap;
import com.example.closet.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class History_GridAdapter extends BaseAdapter {
    private PopupWindow mPopupWindow;
    Context context;
    private LayoutInflater inflater;

    ArrayList<URL> photoUrls;
    ArrayList<Bitmap> photoBitmap = new ArrayList<>();
    UrlToBitmap urlToBitmap;
    ArrayList<String> arrayTextList;

    public History_GridAdapter(Context context, ArrayList<URL> photoUrls, ArrayList<String> arrayTextList) {

        this.context = context;
        inflater = LayoutInflater.from(context);

        this.photoUrls = photoUrls;
        this.arrayTextList = arrayTextList;

        urlToBitmap = new UrlToBitmap(photoUrls);
        urlToBitmap.execute();
        try {
            photoBitmap = urlToBitmap.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return photoUrls.size();
    }

    public Object getItem(int i) {
        return photoUrls.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();

            view = inflater.inflate(R.layout.history_griditem, viewGroup, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.history_iv);
            viewHolder.textView = (TextView) view.findViewById(R.id.history_tv);

            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        viewHolder.imageView.setImageBitmap(photoBitmap.get(i));
        viewHolder.textView.setText(arrayTextList.get(i));

        viewHolder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final View popupView = LayoutInflater.from(context).inflate(R.layout.history_pop_up, null);

                mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mPopupWindow.setFocusable(true);
                mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                ImageView single_image = (ImageView) popupView.findViewById(R.id.single_iv);
                Button single_ok = (Button) popupView.findViewById(R.id.single_ok);
                single_image.setImageBitmap(photoBitmap.get(i));

                single_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow.dismiss();
                    }
                });
            }
        });

        return view;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView textView;
    }
}
