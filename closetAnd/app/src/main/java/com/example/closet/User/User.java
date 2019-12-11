package com.example.closet.User;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.closet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class User extends Fragment implements AdapterView.OnItemClickListener {
View view;
ListView listView;
ArrayAdapter<String> adapter;
Intent intent;

    public User() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_user, container, false);
        listView = (ListView)view.findViewById(R.id.userListView);
        String[] datas = {"로그인","회원가입"};
        listView.setOnItemClickListener(this);
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,datas);
        listView.setAdapter(adapter);
        return view;

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == 0){
            //로그인 ..
            intent = new Intent(getContext(),LogIn.class);
            //startActivityForResult(intent,30);//상수로 바꾸기
            startActivity(intent);
        }
        else if(i ==1){
            //회원가입
            intent = new Intent(getContext(),SignUp.class);
            //startActivityForResult(intent,40);
            startActivity(intent);
        }
    }
}
