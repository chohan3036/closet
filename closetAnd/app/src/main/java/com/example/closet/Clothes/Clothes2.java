package com.example.closet.Clothes;

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

import com.example.closet.Networking_Get;
import com.example.closet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Clothes2 extends AppCompatActivity {
    private Context context;
    private Clothes2_Adapter adapter;

    ArrayList<URL> photoUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);
        context = Clothes2.this;
        getClothings();
        loadGridView();
    }

    private void getClothings(){
        try {
            //URL url = new URL("http://52.78.194.160:3000/closet/show/personalCloset/1/null"); //uid 고치기
            URL url = new URL("http://52.78.194.160:3000/closet/show/personalCloset/1/null");
            Networking_Get networking = new Networking_Get(url);
            networking.execute();
            JSONObject result = networking.get();
            //Log.d("Log_d_Result.get_status", String.valueOf(result.get("status")));
            //Log.d("Log_d_Result.get_result", String.valueOf(result.get("result")));
            //[{"cid":19,"color_name":"red","color_r":255,"color_g":10,"color_b":30,"category":"skirt","description":"favorite","photo":"https:\/\/closetsook.s3.ap-northeast-2.amazonaws.com\/1574096231635.PNG"},{"cid":24,"co
            //W/System.err: org.json.JSONException: Not a primitive array: class org.json.JSONArray
            JSONArray clothingResults= (JSONArray) result.get("result");
            //Log.d("Log_d_jsonarray", String.valueOf(clothingResults));
            for(int i=0; i<clothingResults.length(); i++){
                JSONObject eachClothing = clothingResults.getJSONObject(i);
                String photoFile = eachClothing.getString("photo");
                //Log.d("Log_dPhotoFile",photoFile);
                photoUrls.add(new URL(photoFile));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
       } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void loadGridView() {
        GridView gridView = (GridView)findViewById(R.id.clothes_grid);
        adapter = new Clothes2_Adapter(context, R.layout.clothes_griditem,  photoUrls);
        gridView.setAdapter(adapter);
    }

    public class ImageArray{
        public ImageArray(int index, String msg){
            this.index = index;
            this.msg = msg;
        }
        int index;
        String msg;
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