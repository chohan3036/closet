package com.example.closet.Clothes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.Spinner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.closet.Match.Match;
import com.example.closet.Match.Match_Grid;
import com.example.closet.Networking_Get;
import com.example.closet.R;
import com.example.closet.SaveSharedPreference;
import com.example.closet.storeClothingNetworking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Clothes extends AppCompatActivity {
    String[] REQUESTED_PEERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};

    private Clothes_Adapter adapter;
    ArrayList<URL> photoUrls = new ArrayList<>();
    GridView gridView;
    int spinner_id = 0;
    private Context context;

    //****image to server
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;

    String selectedImagesPaths;
    boolean imagesSelected = false;
    private Uri uri;
    Bitmap bitmap;
    //****image to server

    String uid = "3"; // 들어오는  유저 index저장 하기.
    private String net_url = "http://52.78.194.160:3000/closet/show/personalCloset?uid=" + uid;

    ArrayList<Integer> checked_items;
    JSONArray clothingResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);
        context = this; //context오는지 확인해야 할 듯
        getClothings(net_url);
        getUid();
        loadGridView();
        setSpinner();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(Clothes.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), "Access to Storage Permission Granted. Thanks.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Access to Storage read Permission Denied.", Toast.LENGTH_SHORT).show();
                }

            }
            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), "Access to Camera Permission Granted. Thanks.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Access to Camera Permission Denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 3: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), "Access to Camera Permission Granted. Thanks.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Access to Storage write Permission Denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void getUid() {
        uid = SaveSharedPreference.getString(this, "uid"); //이걸 메인에서 받아서 intent로 넘겨줘야하나?
        Log.d("Log_dGetUid",uid);
    }

    private void setSpinner() {
        final String[] spinnerNames = new String[]{"Color", "All", "Red", "Orange", "Yellow", "Yellow Green", "Green", "Sky Blue",
                "Blue", "Bluish Violet", "Khaki", "Brown", "Beige", "White", "Black", "Gray"};
        int[] spinnerImages = new int[]{R.drawable.none, R.drawable.all, R.drawable.red, R.drawable.orange
                , R.drawable.yellow
                , R.drawable.yellow_green
                , R.drawable.green
                , R.drawable.sky_blue
                , R.drawable.blue
                , R.drawable.bluish_violet
                , R.drawable.khaki
                , R.drawable.brown
                , R.drawable.beige
                , R.drawable.white
                , R.drawable.black
                , R.drawable.gray};

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner4);
        String[] items = getResources().getStringArray(R.array.clothes_array);
        //String[] items1 = getResources().getStringArray(R.array.clothes_color_array);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        //ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //dataAdapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                System.out.println(item);

                if (!item.equals("Category")) {
                    // Showing selected spinner item
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                    photoUrls.clear();
                    String categoryUrl;

                    if (item.equals("All"))
                        categoryUrl = net_url;
                    else
                        categoryUrl = net_url.concat("&category=" + item);
                    getClothings(categoryUrl);
                    loadGridView();
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        CustomSpinner_Adapter customSpinnerAdapter = new CustomSpinner_Adapter(Clothes.this, spinnerNames, spinnerImages);
        spinner1.setAdapter(customSpinnerAdapter);
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_id = spinner1.getSelectedItemPosition();
                //spinnerNames[spinner_id] = parent.getItemAtPosition(position).toString();
                System.out.println(spinnerNames[spinner_id]);
                if (!spinnerNames[spinner_id].equals("Color")) {
                    // Showing selected spinner item
                    Toast.makeText(parent.getContext(), "Selected: " + spinnerNames[spinner_id], Toast.LENGTH_LONG).show();
                    photoUrls.clear();
                    String colorUrl;
                    if (spinnerNames[spinner_id].equals("All"))
                        colorUrl = net_url;
                    else
                        colorUrl = net_url.concat("&color=" + spinnerNames[spinner_id]);
                    getClothings(colorUrl);
                    loadGridView();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void getClothings(String net_url) {
        try {
            URL url = new URL(net_url);
            Networking_Get networking = new Networking_Get(url);
            networking.execute();
            JSONObject result = networking.get();
            clothingResults = (JSONArray) result.get("result");
            //Log.d("Log_d_jsonarrayResult", String.valueOf(clothingResults));
            //checked 된 거랑 맞춰서 intent로 보내는 방법으로  해보기
            //[{"cid":19,"color_name":"red","color_r":255,"color_g":10,"color_b":30,"category":"skirt","description":"favorite","photo":"https:\/\/closetsook.s3.ap-northeast-2.amazonaws.com\/1574096231635.PNG"},{"cid":24,"color_name":"white","color_r":11,"color_g":45,"color_b":133,"category":"skirt"

            for (int i = 0; i < clothingResults.length(); i++) {
                JSONObject eachClothing = clothingResults.getJSONObject(i);
                String photoFile = eachClothing.getString("photo");
                //Log.d("Log_dPhotoFile",photoFile);
                photoUrls.add(new URL(photoFile));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadGridView() {
        gridView = (GridView) findViewById(R.id.clothes_grid);
        adapter = new Clothes_Adapter(this, R.layout.clothes_griditem, photoUrls);
        gridView.setAdapter(adapter);
    }

    @SuppressLint("ResourceType")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                addPopup();
                break;
            case R.id.info:
                infoPopup();
                break;
            case R.id.mypick:
                ArrayList<URL> selected_to_match = new ArrayList<>();
                checked_items = Clothes_Adapter.checked_items;
                try {
                    for (int i = 0; i < checked_items.size(); i++) {
                        //Log.d("Log_dDDaaaaa", i + ":" + (checked_items.get(i)));
                        JSONObject eachClothing = null;
                        //int check_index = checked_items.get(i);
                        eachClothing = clothingResults.getJSONObject(checked_items.get(i));
                        String photoFile = eachClothing.getString("photo");
                        selected_to_match.add(new URL(photoFile));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                selected_items.selected_from_clothes = selected_to_match;
                Toast.makeText(this, "선택하신 옷이 전송되었습니다", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(view.getContext(), Match.class);
                //intent.putExtra("selected_items", selected_to_match);
                //view.getContext().startActivity(intent);
                break;
        }
    }

    private PopupWindow addPopupWindow;
    private PopupWindow infoPopupWindow;

    protected void addPopup() {
        View addPopupView = getLayoutInflater().inflate(R.layout.activity_clothes3, null);
        addPopupWindow = new PopupWindow(addPopupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
        addPopupWindow.setFocusable(true);
        // 외부 영역 선택시 PopUp 종료
        addPopupWindow.showAtLocation(addPopupView, Gravity.CENTER, 0, 0);

        Button close = (Button) addPopupView.findViewById(R.id.closeAdd);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPopupWindow.dismiss();
            }
        });

        Button camera = (Button) addPopupView.findViewById(R.id.callCamera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Toast.makeText(getApplicationContext(), "No picture", Toast.LENGTH_SHORT).show();
                    }
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                "com.example.closet.fileprovider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
                    }
                }
            }
        });

        Button album = (Button) addPopupView.findViewById(R.id.callAlbum);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Album", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_FROM_ALBUM);
                //infoPopup();
            }
        });
    }

    protected void infoPopup() {
        View popupView = getLayoutInflater().inflate(R.layout.activity_clothes2, null);
        infoPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
        infoPopupWindow.setFocusable(true);
        // 외부 영역 선택시 PopUp 종료
        infoPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        Button cancel = (Button) popupView.findViewById(R.id.Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPopupWindow.dismiss();
            }
        });

        Button ok = (Button) popupView.findViewById(R.id.Ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //String currentPhotoPath;

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
        selectedImagesPaths = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_CAMERA ) {
            //File imgFile = new File(selectedImagesPaths);
            imagesSelected = true;
        }
        else if (requestCode == PICK_FROM_ALBUM) {
            if (data == null) {
                Log.d("Log_d data", "data is null");
            } else {
                uri = data.getData();
                Log.d("UIR is ", uri.toString());
                selectedImagesPaths = getRealPathFromURI(this, uri);
                Log.d("Real file path is", selectedImagesPaths);
                imagesSelected = true;
            }
        }

        AddClothes sendImage = new AddClothes(imagesSelected, selectedImagesPaths);
        sendImage.connectServer();
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getRealPathFromURI(final Context context, final Uri uri) {

        if (DocumentsContract.isDocumentUri(context, uri)) {

            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                } else {
                    String SDcardpath = getRemovableSDCardPath(context).split("/Android")[0];
                    return SDcardpath +"/"+ split[1];
                }
            }
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
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
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getRemovableSDCardPath(Context context) {
        File[] storages = ContextCompat.getExternalFilesDirs(context, null);
        if (storages.length > 1 && storages[0] != null && storages[1] != null)
            return storages[1].toString();
        else
            return "";
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }
}