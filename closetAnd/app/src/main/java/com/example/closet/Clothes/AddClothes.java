package com.example.closet.Clothes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.closet.LoadingActivity;
import com.example.closet.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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

public class AddClothes extends AppCompatActivity {

    private static final Pattern IP_ADDRESS
            = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))");

    String selectedImagesPaths; // Paths of the image(s) selected by the user.
    boolean imagesSelected; // Whether the user selected at least an image or not.
    static String[] responses;

    public AddClothes (boolean imagesSelected, String selectedImagesPaths)
    {
        this.imagesSelected = imagesSelected;
        this.selectedImagesPaths = selectedImagesPaths;
    }

    public void connectServer() throws ExecutionException, InterruptedException {

        if (imagesSelected == false) {
            System.out.println("No Image Selected to Upload. Select Image(s) and Try Again.");
        }
        System.out.println("Sending the Files. Please Wait ...");

        String ipv4Address = "52.78.194.160";
        //String ipv4Address = "192.168.0.3";
        String portNumber = "3030";

        Matcher matcher = IP_ADDRESS.matcher(ipv4Address);
        if (!matcher.matches()) {
            System.out.println("Invalid IPv4 Address. Please Check Your Inputs.");
        }
        String postUrl = "http://" + ipv4Address + ":" + portNumber;

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            Bitmap responseImage = BitmapFactory.decodeFile(selectedImagesPaths, options);
            // 사이즈가 클 때 줄이는 코드
            int height = responseImage.getHeight();
            int width = responseImage.getWidth();
            Bitmap resized = null;
            while(height > 800 || width > 800){
                resized = Bitmap.createScaledBitmap(responseImage, width / 2 , height / 2, true);
                height = resized.getHeight();
                width = resized.getWidth();
            }
            responseImage = resized;
            responseImage.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        }catch(Exception e){
            System.out.println("Please Make Sure the Selected File is an Image.");
        }
        byte[] byteArray = stream.toByteArray();

        multipartBodyBuilder.addFormDataPart("photo", "Clothes.jpg",
                RequestBody.create(MediaType.parse("image/*jpg"), byteArray));

        RequestBody postBody = multipartBodyBuilder.build();
        postRequest(postUrl, postBody);
    }

    void postRequest(String postUrl, RequestBody postBody) throws ExecutionException, InterruptedException {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(postUrl + "/saveClothes")
                .post(postBody)
                .build();

        CallbackFuture future = new CallbackFuture();
        client.newCall(request).enqueue(future);

        Response response = future.get();
        String resultStr = null;
        try {
            resultStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(resultStr);
        responses = resultStr.split(",");
    }

    class CallbackFuture extends CompletableFuture<Response> implements Callback{

        public void onFailure(Call call, IOException e){
            super.completeExceptionally(e);
            call.cancel();
            Log.d("FAIL", e.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Failed to Connect to Server. Please Try Again.");
                }
            });
        }

        public void onResponse(Call call, final Response response){
            super.complete(response);
        }
    }


    /* 비동기로 구현한 코드! 혹시 몰라서 안 지우는 것이니 볼 필요 없음
    client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            // Cancel the post on failure.
            call.cancel();

            // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("Fail","Failed to Connect to Server");
                }
            });
        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {
            // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String resultStr = response.body().string();
                        responses = resultStr.split(",");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    });*/
}
