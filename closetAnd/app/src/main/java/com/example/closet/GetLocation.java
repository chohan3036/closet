package com.example.closet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.FusedLocationProviderClient;

public class GetLocation extends AsyncTask<Void, Void, Void> {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    FusedLocationProviderClient mFusedLocationProviderClient;
    Boolean mLocationPermissionGranted = false;
    Activity activity;

    public GetLocation(Activity activity) {

        this.activity = activity;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        getLocationPermission();
        getDeviceLocation();
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    /*@Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the` result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }*/

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                final Task locationResult = mFusedLocationProviderClient.getLastLocation();

                locationResult.addOnCompleteListener(activity, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d("Log_D_TASK.RETRESULT", task.getResult().toString());
                            String result = task.getResult().toString();
                            String[] rr = result.split(" ");
                            System.out.println(rr[0]);
                            System.out.println(rr[1]);
                            Log.d("Log_D_TASK.RETRESULT111", rr[0]);
                            Log.d("Log_D_TASK.RETRESULT222", rr[1]);
                            //Location[fused 37.544685,126.965041 hAcc=15 et=+11d4h25m39s888ms alt=80.5 vAcc=2 sAcc=??? bAcc=??? {Bundle[mParcelledData.dataSize=52]}]
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

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}
