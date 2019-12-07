package com.example.closet.Match;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

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

public class NetworkingAvatar extends AsyncTask<Void, Void, JSONObject> {
    Activity activity;
    String selectedImagesPaths;
    JSONObject avaInfo;

    NetworkingAvatar(String selectedImagesPaths, Activity activity){
        this.selectedImagesPaths = selectedImagesPaths;
        this.activity = activity;
    }
    public void connectServer() {

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
            return;
        }

        String postUrl = "http://" + ipv4Address + ":" + portNumber + "/saveAvatar";

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            Bitmap responseImage = BitmapFactory.decodeFile(selectedImagesPaths, options);
            responseImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        }catch(Exception e){
            System.out.println("Please Make Sure the Selected File is an Image.");
            return;
        }
        byte[] byteArray = stream.toByteArray();

        multipartBodyBuilder.addFormDataPart("photo", "avatar.jpg",
                RequestBody.create(MediaType.parse("image/*jpg"), byteArray));
        multipartBodyBuilder.addFormDataPart("uid", "3"); //uid바꾸기

        RequestBody postBody = multipartBodyBuilder.build();

        postRequest(postUrl, postBody);
    }

    void postRequest(String postUrl, RequestBody postBody) {
        //OkHttpClient client = new OkHttpClient();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(120,TimeUnit.SECONDS)
                .readTimeout(130,TimeUnit.SECONDS)
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
                String result=response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String jsonResult = (String) jsonObject.get("result");
                    JSONArray jsonArray = new JSONArray(jsonResult);

                    avaInfo = (JSONObject) jsonArray.get(0);
                    String head= (String) avaInfo.get("Head");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @Override
    protected JSONObject doInBackground(Void... voids) {
        connectServer();
        /* 결과값jsonPasring해서 줘야할듯,,
         * [{'avatar_id': 18, 'uid': 3, 'Head': '(220, 31)', 'Neck': '(236, 118)', 'RShoulder': '(165, 166)', 'RElbow': '(149, 261)', 'RWrist': '(141, 356)', 'LShoulder': '(299, 158)', 'LElbow': '(331, 269)', 'LWrist': '(338, 348)', 'RHip': '(212, 602)', 'RKnee': 'None', 'RAnkle': '(228, 404)', 'LHip': '(197, 602)', 'LKnee': '(331, 372)', 'LAnkle': '(236, 396)', 'Chest': '(236, 229)', 'Background': None, 'photo': 'https://closetsook.s3.ap-northeast-2.amazonaws.com/User_Avatar_20191206-195749.png'}]

         * */

        return avaInfo;
    }

    @Override
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);
    }
}
