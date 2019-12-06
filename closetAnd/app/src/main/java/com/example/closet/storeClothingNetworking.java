package com.example.closet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.camera2.CameraCharacteristics;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class storeClothingNetworking extends AsyncTask<Void, Void, Void> {

    URL url;
    HttpURLConnection conn;
    OutputStream outputStream = null;
    PrintWriter writer;
    String filePath;
    File photo_file;
    //uid,name(color),colorR,colorG,ColorB,category,description,photo
    String response;

    public storeClothingNetworking(String path) throws MalformedURLException {
        this.url = new URL("http://52.78.194.160:3030/saveAvatar");
        this.photo_file = new File(path);

    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Log.d("Log_dPhotoFileInNet",photo_file.toString());
            String boundary = "-----";
            //String boundary = "*****";
            String LINE_FEED = "\r\n";
            JSONObject result = null;

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-type", "multipart/form-data;boundary="+boundary);
            //conn.setRequestProperty("Connection","Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(30*1000);// 30초
            conn.setReadTimeout(20*1000);//
            conn.setDoInput(true);
            conn.setDoOutput(true);

            outputStream = conn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

            /*HashMap<String, String> params = new HashMap<>(); //들어오는 값으로 수정하기
            params.put("uid", "3");
            params.put("name", "blue");
            params.put("colorR", "201");
            params.put("colorG", "201");
            params.put("colorB", "201");
            params.put("category", "jeans");
            params.put("description", "descriptionTest");

            Set set= params.keySet();
            Iterator iterator =set.iterator();

            while(iterator.hasNext()){
                //body에 data
                String key = (String)iterator.next();

                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposirion: form-data; name=\"" + key + "\"").append(LINE_FEED);
                writer.append("Content-Type: text/plain; charset=" + "UTF-8").append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.append(params.get(key)).append(LINE_FEED);
                writer.flush();
            }*/

            writer.append("--"+boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"photo\"; filename=\"" + photo_file.toString() + "\"").append(LINE_FEED);
            //writer.append("Content-Type:multipart/form-data;").append(LINE_FEED); //이거는..?흠
            //writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();


            writer.append("--"+boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"uid\"").append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + "UTF-8").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.append("4").append(LINE_FEED);//값 .uid 값 argu로 받아야하나?
            writer.flush();

            /** 파일 데이터를 넣는 부분**/

            long FileSize = photo_file.length();
            if(FileSize > 2000000 ){
                Log.d("Log_dFIleSize","FIle size is too big");
                photo_file = ResizeFile(photo_file,FileSize);
            }


            FileInputStream inputStream = new FileInputStream(photo_file);
            byte[] buffer = new byte[(int) photo_file.length()];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            writer.append(LINE_FEED);
            writer.flush();

            writer.append("--"+boundary + "--").append(LINE_FEED);
            writer.close();

            Log.d("Log_dSTORE", String.valueOf(conn.getResponseCode()));

            conn.connect();

            if (conn.getResponseCode() == 201) {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = readStream(in);
                JSONObject responseJson = new JSONObject(response);
                Log.d("Log_dResponse", String.valueOf(responseJson));
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private File ResizeFile(File uploadFile,long Size) throws Exception{
        int num=(int)(Size/1000000);
        Bitmap uploadBitmap = BitmapFactory.decodeFile(uploadFile.getAbsolutePath());
        //File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/UVchecker");
        //File newFile= File.createTempFile("UVchecker", ".jpg", dir);
        FileOutputStream out = new FileOutputStream(uploadFile);

        int height=uploadBitmap.getHeight();
        int width=uploadBitmap.getWidth();
        int newheight=height/num;
        int newwidth=width/num;
        float scaleWidth = ((float) newwidth) / width;
        float scaleHeight = ((float) newheight) / height;
        Matrix matrix= new Matrix();
        uploadBitmap=Bitmap.createScaledBitmap(uploadBitmap, newwidth, newheight, true);
        Bitmap resizeBitmap=Bitmap.createBitmap(uploadBitmap,0,0,newwidth,newheight,matrix,true);
        resizeBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        out.close();
        return uploadFile;
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
}