package com.qihoo.uninstalltest;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.lang.reflect.Field;


public class MyApplication extends Application {

    public static Context context;
    public static IBinder mRemote;
    public static Parcel mData;


    protected void attachBaseContext(Context base) {
        context = base;
        initAmsBinder();
        initActivityParcel();
    }

    public static void send() {
        try {
            if (mRemote == null || mData == null) {
                Log.e("MainActivity", "REMOTE IS NULL or PARCEL IS NULL !!!");
                return;
            }
            mRemote.transact(3, mData, null, 0);
        } catch (RemoteException e) {
            Log.e("MainActivity", "", e);
        }
    }

    private void initAmsBinder() {
        Class<?> activityManagerNative;
        try {
            activityManagerNative = Class.forName("android.app.ActivityManagerNative");
            Object amn = activityManagerNative.getMethod("getDefault").invoke(activityManagerNative);
            Field mRemoteField = amn.getClass().getDeclaredField("mRemote");
            mRemoteField.setAccessible(true);
            mRemote = (IBinder) mRemoteField.get(amn);
        } catch (Exception e) {
            Log.e("MainActivity", "", e);
        }
    }

    private void initActivityParcel() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
        mData = Parcel.obtain();
        mData.writeInterfaceToken("android.app.IActivityManager");
        mData.writeStrongBinder(null);
        mData.writeString(null);
        intent.writeToParcel(mData, 0);
        mData.writeString(null);
        mData.writeStrongBinder(null);
        mData.writeString(null);
        mData.writeInt(0);
        mData.writeInt(0);
        mData.writeInt(0);
        mData.writeInt(0);
    }
}
