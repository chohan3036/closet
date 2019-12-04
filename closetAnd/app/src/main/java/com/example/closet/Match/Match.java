package com.example.closet.Match;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import java.io.File;
import java.io.IOException;
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

import static android.app.Activity.RESULT_OK;
import static android.graphics.BitmapFactory.decodeByteArray;
import static android.widget.Toast.LENGTH_LONG;

/**
 * A simple {@link Fragment} subclass.
 */
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
        Log.d("Log_dMAtchAdapter", String.valueOf(selected_from_clothes));
        Log.d("Log_dMAtchAdapter", String.valueOf(selected_from_clothes2));


        selected_from_clothes2 = selected_items.selected_from_clothes;

        if (selected_from_clothes2 == null) {
            Toast.makeText(getContext(), "선택된 옷이 없습니다", Toast.LENGTH_LONG).show();
        } else {
            adapter = new Match_Adapter(getActivity(), R.layout.match_griditem, selected_from_clothes2);
            gridView.setAdapter(adapter);
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
               dispatchTakePictureIntent();
                break;
        }
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
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
    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA && resultCode == RESULT_OK) {
            File imgFile = new File(currentPhotoPath );
            if (imgFile.exists()) {
                iv.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }
}