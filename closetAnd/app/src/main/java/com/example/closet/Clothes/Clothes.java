package com.example.closet.Clothes;

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

public class Clothes extends AppCompatActivity {

    public int[] imageIDs = new int[] {R.drawable.example_01, R.drawable.example_04, R.drawable.example_07};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        GridView gridViewImages = (GridView) findViewById(R.id.clothes_grid);
        GridAdapter imageGridAdapter = new GridAdapter(this, imageIDs);
        gridViewImages.setAdapter(imageGridAdapter);
    }

    private PopupWindow addPopupWindow;
    private PopupWindow infoPopupWindow;

    public void ShowAddPopup(View v) {
        View addPopup = getLayoutInflater().inflate(R.layout.activity_clothes2, null);
        addPopupWindow = new PopupWindow(addPopup, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
        addPopupWindow.setFocusable(true);
        // 외부 영역 선택시 PopUp 종료
        addPopupWindow.showAtLocation(addPopup, Gravity.CENTER, 0, 0);

        Button close = (Button) addPopup.findViewById(R.id.closeAdd);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPopupWindow.dismiss();
            }
        });

        Button album = (Button) addPopup.findViewById(R.id.callAlbum);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Camera", Toast.LENGTH_SHORT).show();
            }
        });

        Button camera = (Button) addPopup.findViewById(R.id.callCamera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Camera", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void ShowInfoPopup() {
        View popupView = getLayoutInflater().inflate(R.layout.activity_clothes2, null);
        infoPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
        infoPopupWindow.setFocusable(true);
        // 외부 영역 선택시 PopUp 종료
        infoPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        Button cancel = (Button) popupView.findViewById(R.id.Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPopupWindow.dismiss();
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

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.add:
                ShowAddPopup(view);
                break;
            case R.id.info:
                ShowInfoPopup();
                break;
            case R.id.mypick:
                break;
        }
    }
}