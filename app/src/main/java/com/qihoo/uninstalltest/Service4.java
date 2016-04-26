package com.qihoo.uninstalltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

public class Service4 extends Service {

    public void onCreate() {
        Util.create(this, Const.D2);
        Util.create(this, Const.D1);
        Util.create(this, Const.E2);
        Util.create(this, Const.E1);
        Util.create(this, Const.F2);
        Util.create(this, Const.F1);
        Util.create(this, Const.J1);
        Util.create(this, Const.J2);


        final File indicatorDir = Util.getIndicatorDir(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.D2).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.D1).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.D4).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.D3).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.E2).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.E1).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.E4).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.E3).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.F2).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.F1).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.F4).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.F3).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.J1).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.J2).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.J3).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.J4).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
