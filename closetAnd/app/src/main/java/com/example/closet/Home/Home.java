package com.example.closet.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.closet.Clothes.Clothes;
import com.example.closet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    View view;
    private Button BtnMove;
    private Activity activity;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        BtnMove = (Button) view.findViewById(R.id.BtnActivityOne);

        BtnMove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, Clothes.class);
                activity.startActivity(intent);
            }
        });
        return view;
    }

}
