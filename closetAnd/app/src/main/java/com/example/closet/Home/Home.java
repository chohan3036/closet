package com.example.closet.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.closet.GetLocation;
import com.example.closet.GetUID;
import com.example.closet.GetaLocation;
import com.example.closet.Clothes.Clothes;
import com.example.closet.User.LogIn;
import com.example.closet.R;
import com.example.closet.User.SignUp;
import com.example.closet.openCV_test;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements View.OnClickListener {
    private static final int GET_WEATHER_IS_OK = 3000;

    View view;
    ImageButton BtnMove;
    ImageView map;
    TextView weather_info_textView;

    String weather_info;

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
        setting();
        return view;
    }

    private void setting(){
        map = (ImageView) view.findViewById(R.id.mapButton);
        map.setOnClickListener(this);
        BtnMove = (ImageButton) view.findViewById(R.id.BtnActivityOne);
        BtnMove.setOnClickListener(this);
        weather_info_textView = (TextView)view.findViewById(R.id.weather_text);

    }
    @Override
    public void onStart() {
        super.onStart();
        //getWeatherOnBackground();
    }


    private void getWeatherOnBackground() {
        weather_info = null;
        GetLocation getLocation = new GetLocation(getActivity());
        getLocation.execute(); //여기안에서 networking을 또 해서 asyncTask가 또있음 null
        try {

            weather_info = getLocation.get();
            if(weather_info!= null)
                setWeather_info(weather_info);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View view) {
        //로그인 , 회원가입하고 startForResult로 uid받기 !!
       if (view == map) {
           //필요 없음
            Intent intent = new Intent(getActivity(), GetaLocation.class);
            //startActivity(intent);
            startActivityForResult(intent,GET_WEATHER_IS_OK);
            //startActivitiyForReuslt =Activity로 호출받게 됨.
            //Fragment를 불러들인 mainActivity가 있다면 거기서 받을 수가 있겠죠.
            //메인에서 받은 값을 Fragment에서 재 캐치하는 방법으로 했었던게 기억납니다.
        } if (view == BtnMove) {
            Intent intent = new Intent(getActivity(), Clothes.class);
            startActivityForResult(intent, 30);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

       if (requestCode == GET_WEATHER_IS_OK) {
            String weather_info = data.getStringExtra("result");
            setWeather_info(weather_info);

        }
    }

    private  void setWeather_info(String weatherInfo){
        try {
            //JSONArray jsonArray = new JSONArray(weatherInfo); {}
            JSONObject jsonObject = new JSONObject(weatherInfo);
            //Log.d("JSONAOBJECT",jsonObject.toString());
            JSONObject grid = (JSONObject) jsonObject.get("grid");
            JSONObject wind = (JSONObject) jsonObject.get("wind");
            JSONObject temp = (JSONObject) jsonObject.get("temperature");
            String humidity = (String) jsonObject.get("humidity");
            String timeRelease = (String) jsonObject.get("timeRelease");

            /*Log.d("JSONGRID",grid.toString());
            Log.d("JSONWIND",wind.toString());
            Log.d("JSONTEMP",temp.toString());
            Log.d("JSONHUMI",humidity);
            Log.d("JSONTIME",timeRelease);
            */
            String [] cold = new String[] {"밖이 추워요! 외투를 잊지 마세요~", "밖이 추워요! 코트를 잊지 마세요.", "한파 주위! 롱패딩은 필수에요~"};
            String [] warm = new String[] {"날이 따스하네요. 소풍 가기 좋은 날이에요!", "비교적 따뜻한 날씨입니다.\n 가벼운 옷차림을 추천해요!"};
            String [] hot = new String [] {"너무 더운 날이에요. 물을 자주 드세요!", "폭염 주의! 모자와 선글라스를 챙기시는 게 어떠세요?"};
            String advice = null;
            String result = null;

            //Log.d("Random", String.valueOf(Math.random()));//0~1사이 소수
            //int rv = (int)(Math.random()*3);
            int rv = 1;
            String stmax = temp.get("tmax").toString();
            double tmax = Double.parseDouble(stmax);
            if(tmax < 0)
                advice = cold[rv];
            else if(tmax < 20)
                advice = warm[rv];

            String wea = (String) grid.get("city") + " " + grid.get("county") + "의 현재 날씨입니다.\n최고기온: "+temp.get("tmax")+", 최저기온 : "+temp.get("tmin")+"\n기준시간 : "+timeRelease + "\n";
            result = wea + advice;
            weather_info_textView.setText(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}