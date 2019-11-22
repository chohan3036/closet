package com.example.closet.Match;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import com.example.closet.R;
import com.example.closet.SignUp;

import android.widget.GridView;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
/**
 * A simple {@link Fragment} subclass.
 */
public class Match extends  Fragment implements View.OnClickListener {
    private PopupWindow mPopupWindow;
    View view;
    Button Save, My_pick, Reset;
    GridView gridView;

    public Match() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_match, container, false);
        Save = (Button) view.findViewById(R.id.Save);
        Save.setOnClickListener(this);

        My_pick = (Button) view.findViewById(R.id.My_pick);
        My_pick.setOnClickListener(this);
        Reset = (Button) view.findViewById(R.id.Reset);
        Reset.setOnClickListener(this);
        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Save: {
                Popup();
            }
            case R.id.Reset: {
            }
            case R.id.My_pick: {
                    Move();
            }
        }
    }

    protected void Popup() {
        final View popupView = getLayoutInflater().inflate(R.layout.match_pop_up, null);
        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
        mPopupWindow.setFocusable(true);
        // 외부 영역 선택시 PopUp 종료
        mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        Spinner spinner = (Spinner) popupView.findViewById(R.id.spinner);
        String[] items = getResources().getStringArray(R.array.match_array);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("Look") == false) {
                    // Showing selected spinner item
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        Button cancel = (Button) popupView.findViewById(R.id.Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        Button ok = (Button) popupView.findViewById(R.id.Ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }

   public void Move() {
            Intent intent = new Intent(getActivity(), Match_Grid.class);
            startActivityForResult(intent, 30); //requestCode상수로 만들기
        }
    }