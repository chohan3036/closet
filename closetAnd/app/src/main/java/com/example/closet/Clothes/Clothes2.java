/* package com.example.closet.Clothes;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.closet.R;

import java.util.ArrayList;

public class Clothes2 extends AppCompatActivity {
    private Context context;
    private Clothes2_Adapter adapter;
    private ArrayList<ClothesItem> arrayList;

    public int[] imageIDs = new int[] {R.drawable.example_01, R.drawable.example_04, R.drawable.example_07};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        loadGridView();
    }

    private void loadGridView() {
        GridView gridView = (GridView)findViewById(R.id.clothes_grid);
        arrayList = new ArrayList<>();
        adapter = new Clothes2_Adapter(context, arrayList, imageIDs);
        gridView.setAdapter(adapter);
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.add:
            case R.id.info:
                Popup();
            case R.id.mypick:
        }
    }

    private PopupWindow mPopupWindow;

    protected void Popup() {
        View popupView = getLayoutInflater().inflate(R.layout.activity_clothes2, null);
        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
        mPopupWindow.setFocusable(true);
        // 외부 영역 선택시 PopUp 종료
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
                Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

 */