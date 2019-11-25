package com.example.closet.Match;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.example.closet.R;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class Match_Grid extends AppCompatActivity {
    private PopupWindow mPopupWindow;
    View view;

    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  view = inflater.inflate(R.layout.fragment_match, container, false);
        //return view;
   // }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_my_pick);
        GridView gridview = (GridView) findViewById(R.id.gridview1);
        gridview.setAdapter(new Match_GridAdapter(this));


            //View popupView = getLayoutInflater().inflate(R.layout.match_my_pick, null);
           // mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
            //mPopupWindow.setFocusable(true); // 외부 영역 선택히 PopUp 종료
            //mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            gridview.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent,
                                        View v, int position, long id) {
                    // Send intent to SingleViewActivity
                    Intent i = new Intent(getApplicationContext(), Match_SingViewActivity.class);
                    // Pass image index
                    i.putExtra("id", position);
                    startActivity(i);
                }
            });
        }
    }

