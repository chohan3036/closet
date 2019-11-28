package com.example.closet.History;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.closet.R;

public class History_SingleViewActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_griditem);

        // 확대되는 이미지를 보여주기 위해 ImageView 뷰를 설정합니다.
        ImageView imageView = (ImageView)findViewById(R.id.history_iv);
        setImage(imageView);
    }

    private void setImage (ImageView imageView){

        // 초기 액티비티의 GridView 뷰의 이미지 항목을 클릭할 때 생성된 인텐트는
        // 이 액티비티는 getIntent 메소드를 호출하여 접근할 수 있습니다.
        Intent receivedIntent = getIntent();

        // 확대되는 이미지의 리소스 ID를 인텐트로부터 읽어들이고,
        // 그것을 ImageView 뷰의 이미지 리소스로 설정합니다.
        Uri imageURL = receivedIntent.getParcelableExtra("image URL");
        imageView.setImageURI(imageURL);
    }

}