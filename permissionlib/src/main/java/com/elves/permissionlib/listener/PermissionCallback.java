package com.elves.permissionlib.listener;

import java.util.List;

public interface PermissionCallback {

    void onPermissionGranted(int requestCode,List<String> perms);

    void onPermissionDenied(int requestCode,List<String> perms);
}
