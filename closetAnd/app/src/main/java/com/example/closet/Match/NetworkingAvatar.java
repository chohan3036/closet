package com.example.closet.Match;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.closet.MainActivity.UID;

public class NetworkingAvatar {
    Activity activity;
    String selectedImagesPaths;
    JSONObject avaInfo = new JSONObject();
    Boolean responsed = false;
    String uid = UID;

    NetworkingAvatar(String selectedImagesPaths, Activity activity) {
        this.selectedImagesPaths = selectedImagesPaths;
        this.activity = activity;
    }

    public JSONObject getAvaInfo(){
        return avaInfo;
    }

    public void connectServer() {

        //String ipv4Address = "192.168.0.3";
        String ipv4Address = "52.78.194.160";
        String portNumber = "3030";

        final Pattern IP_ADDRESS
                = Pattern.compile(
                "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                        + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                        + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                        + "|[1-9][0-9]|[0-9]))");

        Matcher matcher = IP_ADDRESS.matcher(ipv4Address);
        if (!matcher.matches()) {
            System.out.println("Invalid IPv4 Address. Please Check Your Inputs.");
        }

        String postUrl = "http://" + ipv4Address + ":" + portNumber + "/saveAvatar";

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            Bitmap responseImage = BitmapFactory.decodeFile(selectedImagesPaths, options);
            // 사이즈가 클 때 줄이는 코드
            int height = responseImage.getHeight();
            int width = responseImage.getWidth();
            if (height > 1000 || width > 1000) {
                Bitmap resized = null;
                while (height > 1000 || width > 1000) {
                    resized = Bitmap.createScaledBitmap(responseImage, width / 2, height / 2, true);
                    height = resized.getHeight();
                    width = resized.getWidth();
                }
                responseImage = resized;
            }
            responseImage.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        } catch(Exception e){
            System.out.println("Please Make Sure the Selected File is an Image.");
        }
        byte[] byteArray = stream.toByteArray();


        multipartBodyBuilder.addFormDataPart("photo", "avatar.jpg",
                RequestBody.create(MediaType.parse("image/*jpg"), byteArray));
        multipartBodyBuilder.addFormDataPart("uid", uid); //uid바꾸기

        RequestBody postBody = multipartBodyBuilder.build();

        postRequest(postUrl, postBody);
    }

    JSONObject postRequest(String postUrl, RequestBody postBody) {
        //OkHttpClient client = new OkHttpClient();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                Log.d("Log_dFAIL", e.getMessage());

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Log_dFailed to Connect to Server. Please Try Again.");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //System.out.println("Log_dServer's Response\n" + response.body().string());
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String jsonResult = (String) jsonObject.get("result");
                        JSONArray jsonArray = new JSONArray(jsonResult);

                        avaInfo = (JSONObject) jsonArray.get(0);
                        String head = (String) avaInfo.get("Head");
                        responsed = true;

                        //Log.d("Log_dAAAAAA", String.valueOf(jsonObject));
                        //Log.d("Log_dAAAAAA", String.valueOf(avaInfo));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return  avaInfo;
    }

    public void imageRequest(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                response.body().byteStream();
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("request failed: " + e.getMessage());
            }
        });
    }
}