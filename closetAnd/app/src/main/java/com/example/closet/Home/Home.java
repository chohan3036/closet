package com.example.closet.Home;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.closet.Clothes.Clothes3;
import com.example.closet.R;
import com.example.closet.SignUp;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements View.OnClickListener {

    View view;
    Button singUp;
    ImageButton BtnMove;


    public Home() {
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
        view = inflater.inflate(R.layout.fragment_home, container, false);

        singUp = (Button)view.findViewById(R.id.signUp);
        BtnMove = (ImageButton) view.findViewById(R.id.BtnActivityOne);

        singUp.setOnClickListener(this);
        BtnMove.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == singUp){
            Intent intent = new Intent(getActivity(), SignUp.class);
            startActivityForResult(intent,30);
        }
        else if(view == BtnMove){
            Intent intent = new Intent(getActivity(), Clothes3.class);
            startActivityForResult(intent,30);
        }
    }
}