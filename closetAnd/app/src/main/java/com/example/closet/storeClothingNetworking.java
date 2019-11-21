package com.example.closet;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class storeClothingNetworking extends AsyncTask<Void,Void,Void> {

    URL url ;
    HttpURLConnection conn ;
    OutputStream outputStream = null;
    PrintWriter writer ;
    //uid,name(color),colorR,colorG,ColorB,category,description,photo

    public storeClothingNetworking() throws MalformedURLException {
        url = new URL("http://52.78.194.160:3000/closet/storeClothing");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String boundary = "-----";
            String LINE_FEED = "\r\n";
            JSONObject result = null;

            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-type","multipart/form-data;charset=UTF-8");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            outputStream = conn.getOutputStream();
            writer  = new PrintWriter(new OutputStreamWriter(outputStream,"UTF-8"),true);

            HashMap<String,String> params = new HashMap<>(); //들어오는 값으로 수정하기
            params.put("uid","3");
            params.put("name","blue");
            params.put("color_R","201");
            params.put("color_G","201");
            params.put("color_B","201");
            params.put("category","jeans");
            params.put("description","descriptionTest");

            //body에 data
            writer.append("--"+boundary).append(LINE_FEED);
            writer.append("Content-Disposirion: form-data; name=\""+"uid"+"\"").append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" +"UTF-8").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.append("데이터값").append(LINE_FEED);
            writer.flush();
            //왜이렇게 해야하지..? 안해도될것같은데

            /** 파일 데이터를 넣는 부분**/

            File file = new File("/C:Users/beeny/Desktop/request.PNG"); //파일 못찾음 어떤식으로 보낼지,,

            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"photo\"; filename=\"" + "/C:/Users/beeny/Desktop/request.PNG" + "\"").append(LINE_FEED);
            //writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[(int)file.length()];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            writer.append(LINE_FEED);
            writer.flush();

            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            Log.d("Log_dSTORE", String.valueOf(conn.getResponseCode()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
