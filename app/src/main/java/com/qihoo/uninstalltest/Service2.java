package com.qihoo.uninstalltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

public class Service2 extends Service {
    public Service2() {
    }

    public void onCreate() {
        Util.create(this,Const.A2);
        Util.create(this,Const.A1);
        Util.create(this,Const.B1);
        Util.create(this,Const.B2);
        Util.create(this,Const.E1);
        Util.create(this,Const.E2);
        Util.create(this,Const.H1);
        Util.create(this,Const.H2);

        final File indicatorDir = Util.getIndicatorDir(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.A2).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.A1).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.A4).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.A3).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.B1).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.B2).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.B3).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.B4).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.E1).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.E2).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.E3).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.E4).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.H1).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.H2).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.H3).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.H4).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
