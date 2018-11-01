package com.elves.permissionlib;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.elves.permissionlib.annotation.IPermission;
import com.elves.permissionlib.helper.PermissionHelper;
import com.elves.permissionlib.listener.PermissionCallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PermissionManager {

    /**
     * Checking the permission is granted or not;
     * @param activity  //current activity;
     * @param permissions //the request permissions;
     * @return  //All permissions are granted,return true,otherwise return false;
     */
    public static boolean hasPermission(Activity activity, String... permissions) {
        if(activity == null){
            throw new IllegalArgumentException("activity can't be null.");
        }

        //The build version lower than M,we needn't to request permission dynamic.
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        for (String permission : permissions){
            if(ContextCompat.checkSelfPermission(activity,permission)!=PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    /**
     * Requesting Permiisons
     * @param activity //current activity
     * @param requestCode // request code
     * @param permissions // The request permissions
     */
    public static void requestPermission(Activity activity, int requestCode, String... permissions) {
            if(hasPermission(activity,permissions)){
                notifyHasPerssions(activity,requestCode,permissions);
                return;
            }
            PermissionHelper helper = PermissionHelper.newInstance(activity);
            helper.requestPermission(requestCode,permissions);
    }

    private static void notifyHasPerssions(Activity activity, int requestCode, String[] permissions) {
        int[] grantedResult = new int[permissions.length];
        for (int i=0;i<permissions.length;i++){
            grantedResult[i] = PackageManager.PERMISSION_GRANTED;
        }
        onRequestPermissionsResult(requestCode,permissions,grantedResult,activity);
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantedResult, Activity activity) {
        ArrayList<String> granted = new ArrayList<String>();
        ArrayList<String> denied = new ArrayList<String>();
        for (int i = 0; i < permissions.length ; i++) {
            if(grantedResult[i] == PackageManager.PERMISSION_GRANTED){
                granted.add( permissions[i]);
            }else{
                denied.add( permissions[i]);
            }
        }
        if(!granted.isEmpty()){
            if(activity instanceof PermissionCallback){
                ((PermissionCallback) activity).onPermissionGranted(requestCode,granted);
            }
        }

        if(!denied.isEmpty()){
            if(activity instanceof PermissionCallback){
                ((PermissionCallback) activity).onPermissionDenied(requestCode,denied);
            }
        }

        //if all permission has been granted.call the IPermission methods.
        if(!granted.isEmpty()&& denied.isEmpty()){
            reflectAnnotationMethod(activity,requestCode);
        }
    }

    private static void reflectAnnotationMethod(Activity activity, int requestCode) {
            Class<? extends  Activity> clazz = activity.getClass();
            Method[] methods = clazz.getDeclaredMethods();
        for (Method method:methods) {
            boolean isIPermissionMethod  = method.isAnnotationPresent(IPermission.class);
            if(isIPermissionMethod){
                IPermission iPermission = method.getAnnotation(IPermission.class);
                if(iPermission.value() == requestCode){
                    try {
                        if(!method.isAccessible())method.setAccessible(true);
                        method.invoke(activity);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }

        }


    }

    public static boolean somePermissionPermanentlyDenied(Activity activity, List<String> deniedPerms) {
        return PermissionHelper.newInstance(activity).somePermissionPermanentlyDenied(deniedPerms);
    }
}
