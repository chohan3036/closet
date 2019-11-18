package com.example.closet.Match;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;
import com.example.closet.R;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import com.example.closet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Match extends Fragment implements View.OnClickListener {

    private PopupWindow mPopupWindow;
    View view;
    Button Save,My_pick;
    public int[] imageIDs = new int[] {R.drawable.example_01, R.drawable.example_04, R.drawable.example_07};

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
            View popupView = getLayoutInflater().inflate(R.layout.match_griditem, null);
            mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
            mPopupWindow.setFocusable(true); // 외부 영역 선택히 PopUp 종료
            mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            //Intent intent = new Intent(getActivity(), Match_GridItem.class);
            //startActivityForResult(intent,30);
        }
    }
}