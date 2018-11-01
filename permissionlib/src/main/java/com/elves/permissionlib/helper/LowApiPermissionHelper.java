package com.elves.permissionlib.helper;

import android.app.Activity;

public class LowApiPermissionHelper extends PermissionHelper {
    public LowApiPermissionHelper(Activity activity) {
        super(activity);

    }

    @Override
    public void requestPermission( int requestCode,String[] permissions) {

    }

    @Override
    protected boolean shouldShowRequestPermissionRationale(String denied) {
        return false;
    }

}
