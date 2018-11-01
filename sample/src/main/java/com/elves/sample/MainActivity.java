package com.elves.sample;
import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.elves.permissionlib.PermissionManager;
import com.elves.permissionlib.annotation.IPermission;


public class MainActivity extends PermissionActivity {
    public static final int CAMERA_REQUEST_CODE = 111;
    public static final int MUTIPLE_REQUEST_CODE = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void mutipleRequest(View view) {
        doMutiplePermission();

    }


    public void onCameraRequest(View view) {
        doCameraPermission();
    }

    @IPermission(CAMERA_REQUEST_CODE)
    private void doMutiplePermission() {
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_CONTACTS};
        if(PermissionManager.hasPermission(this,permissions)){
            Toast.makeText(this,"XXXXX permission ok.",Toast.LENGTH_SHORT).show();
        }else{
            PermissionManager.requestPermission(this,MUTIPLE_REQUEST_CODE,permissions);
        }
    }

    @IPermission(MUTIPLE_REQUEST_CODE)
    private void doCameraPermission() {
        if(PermissionManager.hasPermission(this,Manifest.permission.CAMERA)){
            Toast.makeText(this,"camera permission ok.",Toast.LENGTH_SHORT).show();
        }else{
            PermissionManager.requestPermission(this,CAMERA_REQUEST_CODE,Manifest.permission.CAMERA);
        }
    }

}
