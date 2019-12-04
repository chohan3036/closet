package com.example.closet.Test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.closet.R;

import java.util.ArrayList;

public class Horizon_grid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizon_grid);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.testRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Drawable> ddss = new ArrayList<>();
        ddss.add(getResources().getDrawable(R.drawable.camera));
        ddss.add(getResources().getDrawable(R.drawable.camera));
        ddss.add(getResources().getDrawable(R.drawable.camera));
        ddss.add(getResources().getDrawable(R.drawable.camera));

        String[] dataSet = new String[]{"asf","sdadas","safasag"};
        MyAdapter adapter = new MyAdapter(dataSet);
        recyclerView.setAdapter(adapter);
    }
}
class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        //TextView v = (TextView) LayoutInflater.from(parent.getContext())
         //       .inflate(R.layout.horizon_gridview, parent, false);

        //View
        //MyViewHolder vh = new MyViewHolder(v);
        //return vh;
        return null;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        //return mDataset.length;
        return mDataset.length;
    }
}
