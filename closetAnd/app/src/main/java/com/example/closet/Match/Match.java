package com.example.closet.Match;

import android.app.AlertDialog;
import android.content.Context;
import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.closet.Clothes.selected_items;
import com.example.closet.Networking;
import com.example.closet.R;

import android.widget.GridView;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import androidx.fragment.app.Fragment;

import static android.graphics.BitmapFactory.decodeByteArray;

/**
 * A simple {@link Fragment} subclass.
 */
public class Match extends Fragment implements View.OnClickListener {
    private PopupWindow mPopupWindow;
    View view;
    ImageButton btn_camera;
    Button save, pick, reset;
    private final int REQUEST_WIDTH = 512;
    private final int REQUEST_HEIGHT = 512;
    ArrayList<URL> selected_from_clothes = new ArrayList<>();
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    Intent intent, intent1;
    ImageView iv;

    GridView bb;
    ArrayList<URL> selected_from_clothes2 = new ArrayList<>();
    ArrayList<Integer> match_checked_items;

    public Match() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        //selected_from_clothes = (ArrayList<URL>) intent.getSerializableExtra("selected_items");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_match, container, false);

        setting();

        bb = (GridView) view.findViewById(R.id.bbbb);

        return view;
    }

    public void setGrid() {

        Match_Adapter adapter;
        Log.d("Log_dMAtchAdapter", String.valueOf(selected_from_clothes));
        Log.d("Log_dMAtchAdapter", String.valueOf(selected_from_clothes2));


        selected_from_clothes2 = selected_items.selected_from_clothes;

        if (selected_from_clothes2 == null) {
            Toast.makeText(getContext(), "선택된 옷이 없습니다", Toast.LENGTH_LONG).show();
        } else {
            adapter = new Match_Adapter(getActivity(), R.layout.match_griditem, selected_from_clothes2);
            bb.setAdapter(adapter);
        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
            setGrid();
    }

    private void setting() {
        btn_camera = (ImageButton) view.findViewById(R.id.match_camera);
        btn_camera.setOnClickListener(this);
        iv = (ImageView) view.findViewById(R.id.match_avatar);
        //pick = (Button) view.findViewById(R.id.pick);
        //pick.setOnClickListener(this);
        save = (Button) view.findViewById(R.id.save);
        save.setOnClickListener(this);
        reset = (Button) view.findViewById(R.id.reset);
        reset.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            //case R.id.pick:
            //    intent = new Intent(getContext(), Match_Grid.class);
            //    //intent.putExtra("selected_items", selected_from_clothes);
            //    startActivity(intent); // match_grid로 이동
            //    break;

            case R.id.save:
                final View popupView = getLayoutInflater().inflate(R.layout.match_pop_up, null);
                mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mPopupWindow.setFocusable(true);
                mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                Spinner spinner = (Spinner) popupView.findViewById(R.id.match_spinner);
                String[] Look_item = getResources().getStringArray(R.array.match_array);
                ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Look_item);
                dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter3);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // On selecting a spinner item
                        final String Look = parent.getItemAtPosition(position).toString();
                        if (!Look.equals("Look(Style)")) {
                            Toast.makeText(parent.getContext(), "Selected: " + Look, Toast.LENGTH_LONG).show();
                            Button ok = (Button) popupView.findViewById(R.id.match_save_Ok);
                            ok.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    //save networkigng 해야하는 곳(아바타랑 Look 같이 보내주기)
                                    //mPopupWindow.dismiss();
                                    //save networkigng 해야하는 곳(아바타랑 Look 같이 보내주기)
                                    try {
                                        URL url = new URL("http://52.78.194.160:3030/storeHistory");
                                        HashMap<String, String> arguments = new HashMap<>();
                                        arguments.put("uid", "3");
                                        arguments.put("outer_cid", "28");
                                        arguments.put("up_cid", "26");
                                        arguments.put("down_cid", "27");
                                        arguments.put("look_name", Look);
                                        //들어가는 값 다 처리해야 함.
                                        Networking networking = new Networking(url, arguments);
                                        networking.execute();
                                        JSONObject result = networking.get();
                                        Log.d("Log_dStoreHistory", String.valueOf(result));

                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });

                Button cancel = (Button) popupView.findViewById(R.id.match_save_Cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow.dismiss();
                    }
                });

                break;
            case R.id.reset:
                break;
            case R.id.match_camera:
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                break;
        }

    }

    // 압축률 문제 -) 사진 갤러리에 저장하거나 drawable에 저장 후 setimgaeview를 유지시켜야함
    // 사진 저장후 inSampleSize로 화질 조정해보기 -) 서버 연결(open pose, background 처리) -) 다시 안드로이드로
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {


                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);
                iv.setImageBitmap(bitmap);

            }
        }
    }
    // 이미지 Resize 함수
   /* private int setSimpleSize(BitmapFactory.Options options, int requestWidth, int requestHeight){
        // 이미지 사이즈를 체크할 원본 이미지 가로/세로 사이즈를 임시 변수에 대입.
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;

        // 원본 이미지 비율인 1로 초기화
        int size = 1;

        // 해상도가 깨지지 않을만한 요구되는 사이즈까지 2의 배수의 값으로 원본 이미지를 나눈다.
        while(requestWidth < originalWidth || requestHeight < originalHeight){
            originalWidth = originalWidth / 2;
            originalHeight = originalHeight / 2;

            size = size * 2;
        }
        return size;
    }*/
}

