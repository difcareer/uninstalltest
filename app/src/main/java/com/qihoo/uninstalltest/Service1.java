package com.qihoo.uninstalltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

public class Service1 extends Service {
    public Service1() {
    }

    public void onCreate() {
        Util.create(this, Const.A1);
        Util.create(this, Const.A2);
        Util.create(this, Const.C1);
        Util.create(this, Const.C2);
        Util.create(this, Const.D1);
        Util.create(this, Const.D2);
        Util.create(this, Const.G1);
        Util.create(this, Const.G2);

        final File indicatorDir = Util.getIndicatorDir(this);


        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.A1).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.A2).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.A3).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.A4).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.C1).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.C2).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.C3).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.C4).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.D1).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.D2).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.D3).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.D4).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.G1).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.G2).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.G3).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.G4).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
