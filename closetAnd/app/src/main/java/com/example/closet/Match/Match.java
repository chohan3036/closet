package com.example.closet.Match;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.Image;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.closet.Clothes.UrlToBitmap;
import com.example.closet.Clothes.UrlToBitmap2;
import com.example.closet.DataTransferInterface;
import com.example.closet.Networking_Get;
import com.example.closet.storeClothingNetworking;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import java.io.File;
import java.io.FileOutputStream;
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

import org.json.JSONArray;
import org.json.JSONException;
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
import static com.example.closet.MainActivity.UID;

public class Match extends Fragment implements View.OnClickListener, DataTransferInterface {
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
    ImageView iv, top, bottom;
    TextView lshoulder, rshoulder, lwrist, rwrist, lknee, rknee;
    GridView gridView;
    ArrayList<URL> selected_from_clothes2 = new ArrayList<>();
    ArrayList<String> selected_from_clothes_category = new ArrayList<>();
    ArrayList<Integer> match_checked_items = Match_Adapter.match_checked_items;
    ;
    Context context;
    JSONObject avatarInfo;//pose networking결과
    ProgressBar progressBar;

    String avatarPhotoPath;
    boolean avatarSelected = false;
    TextView test;

    String uid = null;
    String mPath;

    String lShoulder, forLshoulder, rShoulder, forRshoulder;
    String[] forLshoulderInt, forRshoulderInt;
    int lShoulderX, lShoulderY, rShoulderX, rShoulderY, lShoulderLength, rShoulderLength, forTopWidth;

    String lWrist, rWrist, lKnee, forLwrist, forRwrist, forLknee;
    String[] forLwristInt, forRwristInt, forLkneeInt;
    int lWristLength, lWristX, lWristY, forTopHeight, rWristLength, rWristX, rWristY, forBottomWidth, lKneeLength, lKneeX, lKneeY, forBottomHeight;


    public Match() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //selected_from_clothes = (ArrayList<URL>) intent.getSerializableExtra("selected_items");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_match, container, false);
        gridView = (GridView) view.findViewById(R.id.match_gridView);
        uid = UID;
        setting();
        return view;
    }

    public void setGrid() {

        Match_Adapter adapter;
        selected_from_clothes2 = selected_items.selected_from_clothes;
        selected_from_clothes_category = selected_items.selected_from_clothes_category;
        if (selected_from_clothes2 == null) {
            Toast.makeText(context, "선택된 옷이 없습니다", Toast.LENGTH_LONG).show();
            //getContext못가져오면 이것도 못가져올것같기도?
        } else {
            adapter = new Match_Adapter(getActivity(), R.layout.match_griditem,
                    selected_from_clothes2, selected_from_clothes_category, this);
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

        try {
            Networking_Get networking = new Networking_Get(new URL("http://52.78.194.160:3000/closet/show/getAvatar/" + uid));
            networking.execute();
            JSONObject result = networking.get();

            if (result != null) {
                String avatarResult = (String) result.get("photo");
                //JSONArray avatarResults = (JSONArray) result.get("photo");
                Log.d("Log_d_avatar", avatarResult);
                UrlToBitmap2 bitmap2 = new UrlToBitmap2(new URL(avatarResult));
                bitmap2.execute();
                Bitmap avatarPhoto = bitmap2.get();
                iv.setImageBitmap(avatarPhoto);

                lShoulder = (String) result.get("LShoulder");

                rShoulder = (String) result.get("Rshoulder");
                lWrist = (String) result.get("LWrist");
                rWrist = (String) result.get("RWrist");
                lKnee = (String) result.get("LKnee");
                //Log.d("LKnee", lKnee);

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

    public void parsing() {

        //test = (TextView) view.findViewById(R.id.match_tv);
        //test.setText(avatarInfo.toString());

        // 상의 위치 정보 parsing
        if (!lShoulder.equals("None")) {
            lShoulderLength = lShoulder.length();
            forLshoulder = lShoulder.substring(1, lShoulderLength - 1);
            forLshoulderInt = forLshoulder.split(", ");
            lShoulderX = Integer.parseInt(forLshoulderInt[0]);
            lShoulderY = Integer.parseInt(forLshoulderInt[1]);
        } else {
            lShoulderX = 200;
            lShoulderY = 250;
        }

        if (!rShoulder.equals("None")) {
            rShoulderLength = rShoulder.length();
            forRshoulder = rShoulder.substring(1, rShoulderLength - 1);
            forRshoulderInt = forRshoulder.split(", ");
            rShoulderX = Integer.parseInt(forRshoulderInt[0]);
            rShoulderY = Integer.parseInt(forRshoulderInt[1]);
        } else {
            rShoulderX = 250;
            rShoulderY = 250;
        }

        forTopWidth = rShoulderX - lShoulderX; // 상의 width

        if (!lWrist.equals("None")) {
            // 하의 위치 정보 parsing
            lWristLength = lWrist.length();
            forLwrist = lWrist.substring(1, lWristLength - 1);
            forLwristInt = forLwrist.split(", ");
            lWristX = Integer.parseInt(forLwristInt[0]);
            lWristY = Integer.parseInt(forLwristInt[1]);
        } else {
            lWristX = 250;
            lWristY = 1000;
        }

        if (!rWrist.equals("None")) {
            rWristLength = rWrist.length();
            forRwrist = rWrist.substring(1, rWristLength - 1);
            forRwristInt = forRwrist.split(", ");
            rWristX = Integer.parseInt(forRwristInt[0]);
            rWristY = Integer.parseInt(forRwristInt[1]);
        } else {
            rWristX = 750;
            rWristY = 1000;
        }

        forBottomWidth = rWristX - lWristX; // 하의 width
        forTopHeight = lShoulderY - lWristY; // 상의 height

        if (!lKnee.equals("None")) {
            // 상의 및 하의 height 정의
            lKneeLength = lKnee.length();
            forLknee = lKnee.substring(1, lKneeLength - 1);
            forLkneeInt = lKnee.split(", ");
            lKneeX = Integer.parseInt(forLwristInt[0]);
            lKneeY = Integer.parseInt(forLwristInt[1]);
        } else {
            lKneeX = 300;
            lKneeY = 1500;
        }

        forBottomHeight = lWristY - lKneeY; // 하의 height
    }

    @Override
    public void setValues(String category, Bitmap photo) {
        // TODO Auto-generated method stub
        parsing();
        top = view.findViewById(R.id.match_top);
        bottom = view.findViewById(R.id.match_bottom);
        /*
        lshoulder = view.findViewById(R.id.lshoulder);
        rshoulder = view.findViewById(R.id.rshoulder);
        lwrist = view.findViewById(R.id.lwrist);
        rwrist = view.findViewById(R.id.rwrist);
        lknee = view.findViewById(R.id.lknee);
        lshoulder.setX(lShoulderX);
        lshoulder.setY(lShoulderY);
        lshoulder.setText(lShoulder);
        rshoulder.setX(rShoulderX);
        rshoulder.setY(rShoulderY);
        rshoulder.setText(rShoulder);
        lwrist.setX(lWristX);
        lwrist.setY(lWristY);
        lwrist.setText(lWrist);
        rwrist.setX(rWristX);
        rwrist.setY(rWristY);
        rwrist.setText(rWrist);
        lknee.setX(lKneeX);
        lknee.setY(lKneeY);
        lknee.setText(lKnee);
        */
        System.out.println(category);
        if (category.equals("top") || category.equals("shirt") ||
                category.equals("Coat") || category.equals("Dress")) {
            // 첫번째로 선택한 옷은 상의
            top.setX(lShoulderX);
            top.setY(lShoulderY);
            top.getLayoutParams().width = forTopWidth;
            top.getLayoutParams().height = forTopHeight;
            top.setImageBitmap(photo);
            top.setImageAlpha(255);
            top.requestLayout();
        } else {
            // 그 다음은 하의
            bottom.setX(lWristX);
            bottom.setY(lWristY);
            bottom.getLayoutParams().width = forBottomWidth;
            bottom.getLayoutParams().height = forBottomHeight;
            bottom.setImageBitmap(photo);
            bottom.setImageAlpha(255);
            bottom.requestLayout();
        }
        //top.setLayoutParams(new FrameLayout.LayoutParams());
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
                                    mPath = takeScreenshot();
                                    //try {
                                    NetworkingHistory networkingHistory = new NetworkingHistory(mPath, Look, getActivity());
                                    networkingHistory.connectServer();
                                        /*
                                        URL url = new URL("http://52.78.194.160:3030/storeHistory");
                                        HashMap<String, String> arguments = new HashMap<>();
                                        arguments.put("uid", uid);
                                        //arguments.put("outer_cid", "28");
                                        //arguments.put("up_cid", "26");
                                        //arguments.put("down_cid", "27");
                                        arguments.put("look_name", Look);
                                        //들어가는 값 다 처리해야 함.
                                        Networking networking = new Networking(url, arguments);
                                        networking.execute();
                                        JSONObject result = networking.get();
                                        Log.d("Log_dStoreHistory", String.valueOf(result));
                                        */
                                    //} catch (MalformedURLException e) {
                                    //    e.printStackTrace();
                                    //} catch (InterruptedException e) {
                                    //    e.printStackTrace();
                                    //} catch (ExecutionException e) {
                                    //    e.printStackTrace();
                                    //}
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
                top.setImageBitmap(null);
                bottom.setImageBitmap(null);
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
        avatarPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3030) {
            Uri uri = data.getData();
            String filePath = getPath(getActivity(), uri);
            Log.d("Log_dFilePath ", filePath.toString());

            NetworkingAvatar networking = new NetworkingAvatar(filePath, getActivity());
            networking.connectServer();
            //여기서 null나면 박수빈한테 알려조
            if (new File(filePath).exists()) {
                iv.setImageURI(Uri.fromFile(new File(filePath)));
            }

            while (!networking.responsed) ; //response받을 때 까지 기다림 아예 view동작을멈춤
            avatarInfo = networking.getAvaInfo();
            Log.d("Log_dAvatar", avatarInfo.toString());
            try {
                lShoulder = (String) avatarInfo.get("LShoulder");
                //Log.d("lShoulder",lShoulder);
                rShoulder = (String) avatarInfo.get("RShoulder");
                //Log.d("rShoulder",rShoulder);
                lWrist = (String) avatarInfo.get("LWrist");
                rWrist = (String) avatarInfo.get("RWrist");
                lKnee = (String) avatarInfo.get("LKnee");
            } catch (JSONException e) {
                e.printStackTrace();
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

    private String takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            //View v1 = getActivity().getWindow().getDecorView().getRootView();
            //ImageView v1 = getActivity().getWindow().getDecorView().getRootView();

            //View v1 = (ImageView) view.findViewById(R.id.newimage);
            View v1 = (FrameLayout) view.findViewById(R.id.frame);
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
        return mPath;
    }

    private void openScreenshot(File imageFile) {
        //Intent intent = new Intent();
        //intent.setAction(Intent.ACTION_VIEW);
        //Uri uri = Uri.fromFile(imageFile);
        //ImageView new1 = (ImageView) view.findViewById(R.id.newimage);
        //new1.setImageURI(Uri.fromFile(imageFile));
        //intent.setDataAndType(uri, "image/*");
        //startActivity(intent);
    }

}