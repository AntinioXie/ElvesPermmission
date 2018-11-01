package com.elves.sample;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.elves.permissionlib.PermissionManager;
import com.elves.permissionlib.dialog.PermissionDialog;
import com.elves.permissionlib.listener.PermissionCallback;

import java.util.List;

public class PermissionActivity extends BaseActivity implements PermissionCallback {

    public static final String TAG = "PermissionActivity";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionGranted(int requestCode, List<String> granted) {
        Log.d(TAG,granted.toString());
    }

    @Override
    public void onPermissionDenied(int requestCode, List<String> denied) {
        if(PermissionManager.somePermissionPermanentlyDenied(this,denied)){
            //show our self dialog here
            new PermissionDialog.Builder(this)
                    .setTittle(getApplication().getResources().getString(R.string.permission_dialog_tittle))
                    .setRationale(getApplication().getResources().getString(R.string.permission_contents))
                    .setNavigateButton(android.R.string.cancel)
                    .setPositiveButton(android.R.string.ok)
                    .setRequestCode(333)
                    .setListener(new Dialog.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           Log.i("TAG","onClick");
                        }
                    })
                    .build()
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
