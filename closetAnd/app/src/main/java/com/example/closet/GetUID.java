package com.example.closet;

import android.content.Context;
import android.util.Log;

public class GetUID {
    String uid;
    Context context;

    public GetUID(Context context) {
        uid = SaveSharedPreference.getString(context,"uid"); //이걸 메인에서 받아서 intent로 넘겨줘야하나?
        Log.d("GetUid : ", uid);
    }
}

