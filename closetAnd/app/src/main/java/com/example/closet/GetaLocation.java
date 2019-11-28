package com.example.closet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class GetaLocation extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    FusedLocationProviderClient mFusedLocationProviderClient;
    Boolean mLocationPermissionGranted = false;
    String lat, lng;
    String weatherResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geta_location);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocationPermission();
        getDeviceLocation();
    }

    @Override
    public void onClick(View view) {

    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                //locationResult.addOnSuccessListener(){}
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            //mLastKnownLocation = task.getResult();
                            //Log.d("Log_D_TASK.RETRESULT", task.getResult().toString());
                            String result = task.getResult().toString();
                            String[] LatLng = result.split(" ")[1].split(",");
                            lat = LatLng[0];
                            lng = LatLng[1];
                            Log.d("Log_dLocation", lat + "\n" + lng);
                            //Location[fused 37.544685,126.965041 hAcc=15 et=+11d4h25m39s888ms alt=80.5 vAcc=2 sAcc=??? bAcc=??? {Bundle[mParcelledData.dataSize=52]}]
                            getWeather();
                        } else {
                            Log.d("Task Is not Successful", "Current location is null. Using defaults.");
                            Log.e("Task Is not Successful", "Exception: %s", task.getException());
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void getWeather() {
        try {
            URL url = new URL("http://52.78.194.160:3030/weather?lat=" + lat + "&lng=" + lng);
            Networking_Get networking = new Networking_Get(url);
            networking.execute();
            weatherResult = String.valueOf(networking.get());
            //Log.d("Log_d_From_weatherAPI", weatherResult);
            // {"grid":{"latitude":"37.53376","longitude":"126.98864","city":"서울","county":"용산구","village":"용산동4가"},"wind":{"wdir":"252.00","wspd":"1.60"},"precipitation":{"sinceOntime":"0.00","type":"0"},"sky":{"code":"SKY_O01","name":"맑음"},"temperature":{"tc":"11.20","tmax":"13.00","tmin":"4.00"},"humidity":"61.00","lightning":"0","timeRelease":"2019-11-26 16:00:00"}
            returnToHome();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void returnToHome() {
        Intent intent = new Intent();
        intent.putExtra("result", weatherResult);
        setResult(3000,intent);
        finish();
    }
}
