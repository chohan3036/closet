package com.example.closet.Clothes;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.closet.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
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

    private static final Pattern IP_ADDRESS
            = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))");

    String selectedImagesPaths; // Paths of the image(s) selected by the user.
    boolean imagesSelected; // Whether the user selected at least an image or not.
    static String[] responses;

    EditText color;

    public AddClothes (boolean imagesSelected, String selectedImagesPaths)
    {
        this.imagesSelected = imagesSelected;
        this.selectedImagesPaths = selectedImagesPaths;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "이 앱을 실행하려면 인터넷 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(AddClothes.this, new String[]{Manifest.permission.INTERNET}, 1);
        }
        setContentView(R.layout.activity_clothes2);
        color = findViewById(R.id.cloth_color);
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
        }
    }

    public void connectServer() {

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
        /*multipartBodyBuilder.addFormDataPart("uid", "3");
        multipartBodyBuilder.addFormDataPart("name", "red");
        multipartBodyBuilder.addFormDataPart("colorR", "211");
        multipartBodyBuilder.addFormDataPart("colorG", "11");
        multipartBodyBuilder.addFormDataPart("colorB", "10");
        multipartBodyBuilder.addFormDataPart("category", "jeans");
        multipartBodyBuilder.addFormDataPart("description","descriptionTest");*/

        RequestBody postBody = multipartBodyBuilder.build();
        postRequest(postUrl, postBody);
    }

    void postRequest(String postUrl, RequestBody postBody) {

                // final EditText color = (EditText)findViewById(R.id.cloth_color);
                // final EditText category = (EditText)findViewById(R.id.cloth_category);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(postUrl + "/saveClothes")
                .post(postBody)
                .build();

        final CountDownLatch latch = new CountDownLatch(1);

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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //EditText color = findViewById(R.id.cloth_category);
                        try {
                            //response 파싱해서 Edittext에 띄우게 해야 됨

                            //String resultStr = response.body().string();
                            //String[] results = resultStr.split(",");
                            //color.setText(results[2].split(":")[1]);
                            //category.setText(results[3].split(":")[1]);
                            String resultStr = response.body().string();
                            System.out.println(resultStr);
                            responses = resultStr.split(",");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }


        });
    }
}
