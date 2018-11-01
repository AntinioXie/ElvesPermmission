package com.elves.permissionlib.helper;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import java.util.List;

public abstract class PermissionHelper {
    private Activity activity;

    public PermissionHelper(Activity activity) {
        this.activity = activity;
    }

    public Activity getHost(){
        return activity;
    }
    public static PermissionHelper newInstance(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return new LowApiPermissionHelper(activity);
        } else {
            return new ActivityPermissionHelper(activity);
        }
    }

    public abstract void requestPermission(int requestCode,String[] permissions);

    /**
     * 第一次打開app時,返回false;
     * 上次彈出權限拒絕,沒有勾選"不再詢問",返回true;
     * 上次彈出權限拒絕,並且勾選"不再詢問",返回false;
     * @param deniedPermission //被拒絕的權限
     * @return
     */
    protected abstract boolean shouldShowRequestPermissionRationale(String deniedPermission);

    public boolean somePermissionPermanentlyDenied( List<String> deniedPermissions){
        for (String denied: deniedPermissions) {
            if(!shouldShowRequestPermissionRationale(denied)){
                return true;
            }
        }
        Log.i("test","somePermissionPermanentlyDenied return false");
        return false;

    }


}
