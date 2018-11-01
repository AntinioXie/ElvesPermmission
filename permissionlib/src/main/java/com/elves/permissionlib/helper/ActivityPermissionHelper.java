package com.elves.permissionlib.helper;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class ActivityPermissionHelper extends PermissionHelper {
    private static boolean needShowRationale = false;
    public ActivityPermissionHelper(Activity activity) {
        super(activity);
    }

    @Override
    public void requestPermission(int requestCode, String[] permissions) {
        if(shouldShowRationale(permissions)&&needShowRationale){
            Log.i("test","shouldShowRationale");
        }else{
            ActivityCompat.requestPermissions(getHost(),permissions,requestCode);
        }
    }

    private boolean shouldShowRationale(String[] permissions) {
        for(String permission:permissions){
            if(shouldShowRequestPermissionRationale(permission)){
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean shouldShowRequestPermissionRationale(String deniedPermission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(getHost(),deniedPermission);
    }

}
