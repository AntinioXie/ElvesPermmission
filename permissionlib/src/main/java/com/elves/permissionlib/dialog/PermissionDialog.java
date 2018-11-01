package com.elves.permissionlib.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

public class PermissionDialog implements Dialog.OnClickListener{
    private Activity activity;
    private String tittle;
    private String rationale;
    private int positiveButton;
    private int navigateButton;
    private Dialog.OnClickListener listener;
    private int requestCode = -1;
    public PermissionDialog(Activity activity, String tittle, String rationale, int positiveButton, int navigateButton, DialogInterface.OnClickListener listener, int requestCode) {
            this.activity = activity;
            this.tittle = tittle;
            this.rationale = rationale;
            this.positiveButton = positiveButton;
            this.navigateButton = navigateButton;
            this.listener = listener;
            this.requestCode = requestCode;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",activity.getPackageName(),null);
        intent.setData(uri);
        activity.startActivityForResult(intent,requestCode);
    }

    public void show(){
        if(listener!=null){
            showDialog();
        }else{
            throw new IllegalArgumentException("Dialog listener can't be null");
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(activity)
                .setTitle(tittle)
                .setMessage(rationale)
                .setCancelable(false)
                .setPositiveButton(positiveButton,this)
                .setNegativeButton(navigateButton,listener)
                .create().show();
    }

    public static class Builder{
        private Activity activity;
        private String tittle;
        private String rationale;
        private int positiveButton;
        private int navigateButton;
        private Dialog.OnClickListener listener;
        private int requestCode = -1;

        public Builder(Activity activity){
            this.activity = activity;
        }

        public Builder setTittle(String tittle) {
            this.tittle = tittle;
            return this;
        }

        public Builder setRationale(String rationale) {
            this.rationale = rationale;
            return this;
        }

        public Builder setPositiveButton(int positiveButton) {
            this.positiveButton = positiveButton;
            return this;
        }

        public Builder setNavigateButton(int navigateButton) {
            this.navigateButton = navigateButton;
            return this;
        }

        public Builder setListener(Dialog.OnClickListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public  PermissionDialog build(){
            return new PermissionDialog(activity,tittle,rationale,positiveButton,navigateButton,listener,requestCode);
        }
    }

}
