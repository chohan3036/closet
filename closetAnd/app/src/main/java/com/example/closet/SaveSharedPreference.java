package com.example.closet;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String NAME = "name";

    static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        //return PreferenceManager.getDefaultSharedPreferences(context);
    }

    //String 값 저장
    public static  void setString(Context context,String key,String value){
        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
    //Boolean값 저장
    public static void  setBoolean(Context context, String key, boolean value){
        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    //같은 방식으로 저장하고자 하는 데이터형을 바꿔가면서 함수를 만들어 주면 됨

    //값 로드
    public  static String getString(Context context,String key){
        SharedPreferences preferences = getSharedPreferences(context);
        String value = preferences.getString(key,"");
        return value;
    }

    //키 값 삭제
    public static void removeKey(Context context, String key){
        SharedPreferences preferences =getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }
    //모든 저장 데이터 삭제
    public  static void clear(Context context){
        SharedPreferences preferences =getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
