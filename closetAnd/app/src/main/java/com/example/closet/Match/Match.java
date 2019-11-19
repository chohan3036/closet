package com.example.closet.Match;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.example.closet.R;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
/**
 * A simple {@link Fragment} subclass.
 */
public class Match extends Fragment implements View.OnClickListener {

    private PopupWindow mPopupWindow;
    View view;
    Button Save,My_pick;
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
        view = inflater.inflate(R.layout.fragment_match, container, false);
        Save = (Button) view.findViewById(R.id.Save);
        Save.setOnClickListener(this);
        My_pick = (Button) view.findViewById(R.id.My_pick);
        My_pick.setOnClickListener(this);
       // gridView =(GridView) view.findViewById(R.id.gridview1);
       // Match_GridAdapter ga = new Match_GridAdapter(view.getContext());
       // gridView.setAdapter(ga);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == Save) {
            View popupView = getLayoutInflater().inflate(R.layout.match_pop_up, null);
            mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
            mPopupWindow.setFocusable(true); // 외부 영역 선택히 PopUp 종료
            mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

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
        else if (view==My_pick){
            View popupView = getLayoutInflater().inflate(R.layout.match_my_pick, null);
            mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
            mPopupWindow.setFocusable(true); // 외부 영역 선택히 PopUp 종료
            mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            //gridView.setAdapter(new Match_GridAdapter(view.activity()));
            ImageButton cancel = (ImageButton) popupView.findViewById(R.id.close);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }


            //Intent intent = new Intent(getActivity(), Match_GridItem.class);
            //startActivityForResult(intent,30);
            });
    }
}
}