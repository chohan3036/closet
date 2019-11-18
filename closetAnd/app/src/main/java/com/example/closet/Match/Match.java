package com.example.closet.Match;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import com.example.closet.Clothes.Clothes;
import com.example.closet.R;
import com.example.closet.SignUp;


/**
 * A simple {@link Fragment} subclass.
 */
public class Match extends Fragment implements View.OnClickListener {

    View view;
    Button Save;


    public Match() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_match, container, false);

        Save = (Button)view.findViewById(R.id.Save);

        Save.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == Save){
            Intent intent = new Intent(getActivity(), Match_pop_up.class);
            startActivityForResult(intent,30);
        }
    }
}