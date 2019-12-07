package com.example.closet.Match;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.closet.Clothes.AddClothes;
import com.example.closet.storeClothingNetworking;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Date;

import java.util.concurrent.ExecutionException;

import com.example.closet.Clothes.selected_items;
import com.example.closet.Networking;
import com.example.closet.R;

import android.widget.GridView;

import java.util.HashMap;
import java.util.regex.Matcher;

import static android.app.Activity.RESULT_OK;
import static android.graphics.BitmapFactory.decodeByteArray;
import static android.widget.Toast.LENGTH_LONG;
import static com.example.closet.Clothes.Clothes.getDataColumn;
import static com.example.closet.Clothes.Clothes.isDownloadsDocument;
import static com.example.closet.Clothes.Clothes.isExternalStorageDocument;
import static com.example.closet.Clothes.Clothes.isMediaDocument;

/**
 * A simple {@link Fragment} subclass.
 */
/* 아바타 결과
* [{'avatar_id': 13, 'uid': 7, 'Head': '(220, 31)', 'Neck': '(236, 118)', 'RShoulder': '(165, 174)                       ', 'RElbow': '(157, 261)', 'RWrist': '(141, 356)', 'LShoulder': '(299, 158)', 'LElbow': '(331, 2                       69)', 'LWrist': '(338, 348)', 'RHip': '(212, 602)', 'RKnee': 'None', 'RAnkle': '(228, 404)', 'LH                       ip': '(197, 594)', 'LKnee': '(331, 380)', 'LAnkle': '(236, 396)', 'Chest': '(236, 237)', 'Backgr                       ound': None, 'photo': 'https://closetsook.s3.ap-northeast-2.amazonaws.com/User_Avatar_20191206-0                       95657.png'}]
*
*
* */
public class Match extends Fragment implements View.OnClickListener {
    private PopupWindow mPopupWindow;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private String pictureFilePath;
    private String deviceIdentifier;
    View view;
    ImageButton btn_camera;
    Button save, pick, reset;
    ArrayList<URL> selected_from_clothes = new ArrayList<>();
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    Intent intent, intent1;
    ImageView iv;
    GridView gridView;
    ArrayList<URL> selected_from_clothes2 = new ArrayList<>();
    ArrayList<Integer> match_checked_items;
    Context context;
    JSONObject avatarInfo;//pose networking결과
    ProgressBar progressBar;

    String avatarPhotoPath;
    boolean avatarSelected = false;

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

        gridView = (GridView) view.findViewById(R.id.match_gridView);

        return view;
    }

    public void setGrid() {

        Match_Adapter adapter;
        selected_from_clothes2 = selected_items.selected_from_clothes;
        if (selected_from_clothes2 == null) {
            Toast.makeText(context, "선택된 옷이 없습니다", Toast.LENGTH_LONG).show();
            //getContext못가져오면 이것도 못가져올것같기도?
        } else {
            adapter = new Match_Adapter(getActivity(), R.layout.match_griditem, selected_from_clothes2);
            gridView.setAdapter(adapter);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            setGrid();
    }

    private void setting() {
        context = getContext();
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
                            Toast.makeText(parent.getContext(), "Selected: " + Look, LENGTH_LONG).show();
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
                getPhotoFromAlbum();
                //dispatchTakePictureIntent();
                //getFragmentManager();

                break;
        }
    }

    private void getPhotoFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 3030);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                ///storage/emulated/0/Android/data/com.example.closet/files/Pictures/JPEG_20191206_141016_1292747622526178270.jpg
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.closet.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }



    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        avatarPhotoPath = image.getAbsolutePath();
        //currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    String currentPhotoPath;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3030) {
            Uri uri = data.getData();
            String filePath = getPath(getActivity(), uri);
            Log.d("Log_dFilePath ",filePath.toString());

            try {
                NetworkingAvatar networking = new NetworkingAvatar(filePath,getActivity());
                networking.execute();
                avatarInfo = networking.get();
                //여기서  null 나면 박수빈한테 알려조

                if (new File(filePath).exists()) {
                    iv.setImageURI(Uri.fromFile(new File(filePath)));
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //String selectedImagesPaths = getRealPathFromURI(getContext(), uri);

                //currentPhotoPath = image.getAbsolutePath();
                //File imgFile = new File(currentPhotoPath);

               // networkingPhoto(filePath);

            }
            // Save a file: path for use with ACTION_VIEW intents

            //Log.d("Real file path is", selectedImagesPaths);
            //imagesSelected = true;

        }
        /*
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA && resultCode == RESULT_OK) {

            avatarSelected = true;
            //File imgFile = new File(avatarPhotoPath);
            //if (imgFile.exists()) {
            //    iv.setImageURI(Uri.fromFile(imgFile));
            //}
        }
        AddClothes sendImage = new AddClothes(avatarSelected, avatarPhotoPath);
        sendImage.connectServer();

            File imgFile = new File(currentPhotoPath);
            if (imgFile.exists()) {
                iv.setImageURI(Uri.fromFile(imgFile));
            }
        }*/

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



