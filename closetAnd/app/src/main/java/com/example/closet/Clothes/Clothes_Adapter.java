package com.example.closet.Clothes;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.closet.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class Clothes_Adapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private SparseBooleanArray mSelectedItemsIds;

    ArrayList<URL> photoUrls;
    ArrayList<Bitmap> photoBitmap = new ArrayList<>();
    UrlToBitmap urlToBitmap;


    public Clothes_Adapter(Context context, int layout, ArrayList<URL> photoUrls) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.layout = layout;
        this.photoUrls = photoUrls;
        //Log.d("Log_dasagagadg", String.valueOf(this.photoUrls.get(0)));
        mSelectedItemsIds = new SparseBooleanArray();

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
        View gridView;

        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.clothes_iv);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.chk_clothes_iv);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();
        viewHolder.imageView.setImageBitmap(photoBitmap.get(i));
        viewHolder.checkBox.setChecked(mSelectedItemsIds.get(i));

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(i, !mSelectedItemsIds.get(i));
            }
        });

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
        if (value)
            mSelectedItemsIds.put(position, true);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    /**
     * Return the selected Checkbox IDs
     **/
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}