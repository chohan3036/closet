package com.example.closet.Clothes;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.closet.Networking_Get;
import com.example.closet.R;

import java.net.MalformedURLException;
import java.net.URL;

public class Clothes extends AppCompatActivity implements OnItemSelectedListener{

    public int[] imageIDs = new int[] {R.drawable.example_01, R.drawable.example_04, R.drawable.example_07};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        GridView gridViewImages = (GridView) findViewById(R.id.clothes_grid);
        GridAdapter imageGridAdapter = new GridAdapter(this, imageIDs);
        gridViewImages.setAdapter(imageGridAdapter);

        try {
            URL url = new URL("http://52.78.194.160:3000/closet/show/personalCloset/1/null"); //uid 고치기
            Networking_Get networking = new Networking_Get(url);
            networking.execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        String[] items = getResources().getStringArray(R.array.clothes_array);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(dataAdapter);
    }

    public void onClick(View view) {
        /*switch(view.getId()){
            case R.id.add:
                System.out.println(view.getId());
                addPopup();
                break;
            case R.id.info:
                //infoPopup();
                break;
            case R.id.mypick:
                break;
        }*/
    }
/*
    private PopupWindow infoPopupWindow;
    private PopupWindow addPopupWindow;

    protected void addPopup() {
        View addPopupView = getLayoutInflater().inflate(R.layout.activity_clothes3, null);
        addPopupWindow = new PopupWindow(addPopupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
        addPopupWindow.setFocusable(true);
        // 외부 영역 선택시 PopUp 종료
        addPopupWindow.showAtLocation(addPopupView, Gravity.CENTER, 0, 0);

        Button close = (Button) addPopupView.findViewById(R.id.closeAdd);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPopupWindow.dismiss();
            }
        });

        Button album = (Button) addPopupView.findViewById(R.id.callAlbum);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Camera", Toast.LENGTH_SHORT).show();
            }
        });

        Button camera = (Button) addPopupView.findViewById(R.id.callCamera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Camera", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void infoPopup() {
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
*/
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}