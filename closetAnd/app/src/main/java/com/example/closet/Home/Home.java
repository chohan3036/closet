package com.example.closet.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


import com.example.closet.GetaLocation;
import com.example.closet.Clothes.Clothes;
import com.example.closet.LogIn;
import com.example.closet.R;
import com.example.closet.SignUp;
import com.example.closet.storeClothingNetworking;

import java.net.MalformedURLException;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements View.OnClickListener {

    View view;
    Button singUp,logIn;
    ImageButton BtnMove;
    Button map;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        map = (Button) view.findViewById(R.id.mapButton);
        map.setOnClickListener(this);
        singUp = (Button)view.findViewById(R.id.signUp);
        BtnMove = (ImageButton) view.findViewById(R.id.BtnActivityOne);

        singUp.setOnClickListener(this);
        BtnMove.setOnClickListener(this);

        logIn = (Button)view.findViewById(R.id.login_button);
        logIn.setOnClickListener(this);

        try {
            storeClothingNetworking  networking = new storeClothingNetworking();
            //networking.execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == singUp) {
            Intent intent = new Intent(getActivity(), SignUp.class);
            startActivityForResult(intent, 30); //requestCode상수로 만들기
        } else if (view == map) {
            Intent intent = new Intent(getActivity(), GetaLocation.class);
            getActivity().startActivityForResult(intent, 50);
            //startActivitiyForReuslt =Activity로 호출받게 됨.
            //Fragment를 불러들인 mainActivity가 있다면 거기서 받을 수가 있겠죠.
            //메인에서 받은 값을 Fragment에서 재 캐치하는 방법으로 했었던게 기억납니다.
        }else if(view == logIn){
            Intent intent = new Intent(getActivity(), LogIn.class);
            startActivity(intent);
        }  else if(view == BtnMove){
            Intent intent = new Intent(getActivity(), Clothes.class);
            startActivityForResult(intent,30);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 30) {
            Log.d("Log.dSD", "회원가입 완료");
        } else if (requestCode == 50) {
            Log.d("Log_DDSF", "위치 얻기 완료");
        }
    }
}