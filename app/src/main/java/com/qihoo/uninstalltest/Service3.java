package com.qihoo.uninstalltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

public class Service3 extends Service {

    public void onCreate() {
        Util.create(this, Const.B2);
        Util.create(this, Const.B1);
        Util.create(this, Const.C2);
        Util.create(this, Const.C1);
        Util.create(this, Const.F1);
        Util.create(this, Const.F2);
        Util.create(this, Const.I1);
        Util.create(this, Const.I2);

        final File indicatorDir = Util.getIndicatorDir(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.B2).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.B1).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.B4).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.B3).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.C2).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.C1).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.C4).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.C3).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.F1).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.F2).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.F3).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.F4).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.I1).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.I2).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.I3).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.I4).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
