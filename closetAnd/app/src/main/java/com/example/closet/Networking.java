package com.example.closet;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Networking extends AsyncTask<Void,Void,JSONObject> {
    URL url = null;
    HttpURLConnection urlConnection = null;
    HashMap<String,String> arguments  =null;
    OutputStream outputStream = null;
    String response = null;

    public Networking(URL url, HashMap arguments){

            this.url = url;
            this.arguments = arguments;
            //argus 를 string말고 hashMap으로 받아야 할 것 같음..
    }
    @Override
    protected JSONObject doInBackground(Void... voids) {
        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setConnectTimeout(20 * 1000);//20초
            urlConnection.setReadTimeout(20 * 1000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            outputStream = urlConnection.getOutputStream();
            String params ="";
            Set set = arguments.entrySet();
            Iterator iterator = set.iterator();

            while(iterator.hasNext()){
                Map.Entry entry = (Map.Entry)iterator.next();
               // Log.d("Log_dEntry", String.valueOf(entry)); //key=value
                String key = (String)entry.getKey();
                String value = (String)entry.getValue();
                params += key+"="+value;
                if(iterator.hasNext())
                    params+="&";
               // Log.d("Log_dParams",params);

            }
            //params = "id=" + arguments[0] + "&pwd=" + arguments[1] + "&nickname=" + arguments[2] + "&age=" + arguments[3] + "&sex=" + arguments[4];
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(params);
            outputStreamWriter.flush();
            urlConnection.connect();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = readStream(in);
            JSONObject responseJson = new JSONObject(response);

            return responseJson;

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject responseCode ) {
        super.onPostExecute(responseCode);
    }



    private void checkResponse(){ //이거는 각 fragment에서 해야할듯..
        try {
            //Log.d("Log_dResponseCode", String.valueOf(urlConnection.getResponseCode()));
            if (urlConnection.getResponseCode() == 201) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                response = readStream(in);
                JSONObject responseJson = new JSONObject(response);
                String uid = responseJson.getString("ID");
                String nick = responseJson.getString("Nickname");
                String Uid = responseJson.getString("Uid");
                //Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                //Caused by: java.lang.RuntimeException: Can't toast on a thread that has not called Looper.prepare()
                //intent로 home으로 보내거나,,
            } else if (urlConnection.getResponseCode() == 303) {
                //Toast.makeText(getApplicationContext(),"이미 존재하는 ID입니다",Toast.LENGTH_LONG);
                //아이디 확인 버튼 만들기
                //이미 아이디 있음
            } else {
                //404나 그 외 예외처리하기.
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String readStream(InputStream in) {
        String data = "";
        Scanner s = new Scanner(in);
        while (s.hasNext())
            data += s.nextLine() + "\n";
        s.close();
        try {
            JSONObject response = new JSONObject(data);
            //Log.d("Log_dINput",response.getClass().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    /*
    *   HashMap<String,String> param = new HashMap<>();
        param.put("id","safas");
        param.put("pwd","sda");
        Log.d("Log_d_EntrySet", String.valueOf(param.entrySet())); [id=safas, pwd=sda]
        Log.d("Log_d_EntrySetKey", String.valueOf(param.keySet())); [id, pwd]
        Log.d("Log_d_ket",param.get("id"));
        * */
}
