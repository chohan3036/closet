package com.example.closet.Clothes;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUESTED_PEERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final Pattern IP_ADDRESS
            = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))");

    String selectedImagesPaths; // Paths of the image(s) selected by the user.
    boolean imagesSelected; // Whether the user selected at least an image or not.
    Bitmap bitmap;

    public AddClothes (boolean imagesSelected, String selectedImagesPaths, Bitmap bitmap)
    {
        this.imagesSelected = imagesSelected;
        this.selectedImagesPaths = selectedImagesPaths;
        this.bitmap = bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "이 앱을 실행하려면 인터넷과 외부 저장소 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(AddClothes.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            ActivityCompat.requestPermissions(AddClothes.this, new String[]{Manifest.permission.INTERNET}, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(), "Access to Storage Permission Granted. Thanks.", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getApplicationContext(), "Access to Storage Permission Denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(), "Access to Internet Permission Granted. Thanks.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Access to Internet Permission Denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void connectServer() {
        if (imagesSelected == false) {
            System.out.println("No Image Selected to Upload. Select Image(s) and Try Again.");
            return;
        }
        System.out.println(selectedImagesPaths);
        System.out.println("Sending the Files. Please Wait ...");

        String ipv4Address = "52.78.194.160";
        String portNumber = "3030";
        String url = "http://52.78.194.160:3030/saveClothes";


        Matcher matcher = IP_ADDRESS.matcher(ipv4Address);
        if (!matcher.matches()) {
            System.out.println("Invalid IPv4 Address. Please Check Your Inputs.");
            return;
        }

        String postUrl = "http://" + ipv4Address + ":" + portNumber + "/saveClothes";

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

        multipartBodyBuilder.addFormDataPart("photo", "Android_Flask_.jpg",
                RequestBody.create(MediaType.parse("image/*jpg"), byteArray));
        multipartBodyBuilder.addFormDataPart("uid", "3");
        multipartBodyBuilder.addFormDataPart("name", "red");
        multipartBodyBuilder.addFormDataPart("colorR", "211");
        multipartBodyBuilder.addFormDataPart("colorG", "11");
        multipartBodyBuilder.addFormDataPart("colorB", "10");
        multipartBodyBuilder.addFormDataPart("category", "jeans");
        multipartBodyBuilder.addFormDataPart("description","descriptionTest");

        RequestBody postBody = multipartBodyBuilder.build();

        postRequest(postUrl, postBody);
    }

    void postRequest(String postUrl, RequestBody postBody) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                Log.d("FAIL", e.getMessage());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Failed to Connect to Server. Please Try Again.");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                /*
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("Server's Response\n" + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });*/
                System.out.println("Server's Response\n" + response.body().string());
            }
        });
    }
}
