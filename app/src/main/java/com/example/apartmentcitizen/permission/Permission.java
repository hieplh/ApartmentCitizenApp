package com.example.apartmentcitizen.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permission {

    public static int READ_EXTERNAL_STORAGE = 1000;
    public static int CAMERA = 1001;

    private Context context;
    private Activity activity;

    public Permission(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public boolean grantReadExternalStoratePermission(int requestCode) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
            return false;
        }
        return true;
    }

    public void grantCameraPermission(int requestCode) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, requestCode);
        }
    }
}
