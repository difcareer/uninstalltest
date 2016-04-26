package com.qihoo.uninstalltest;


import android.util.Log;

public class NativeHelper {

    static {
        System.loadLibrary("eternity");
    }

    public static void onUninstalled() {
        MyApplication.send();
        Log.e("MainActivity","after send");
    }


    public static native void invoke(String s1, String s2, String s3, String s4);


}
