package com.qihoo.uninstalltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.File;

public class Service5 extends Service {
    public void onCreate() {
        Util.create(this, Const.D2);
        Util.create(this, Const.D1);
        Util.create(this, Const.E2);
        Util.create(this, Const.E1);
        Util.create(this, Const.F2);
        Util.create(this, Const.F1);
        Util.create(this, Const.F1);
        Util.create(this, Const.G2);
        Util.create(this, Const.G1);
        Util.create(this, Const.H2);
        Util.create(this, Const.H1);
        Util.create(this, Const.I2);
        Util.create(this, Const.I1);
        Util.create(this, Const.J2);
        Util.create(this, Const.J1);


        final File indicatorDir = Util.getIndicatorDir(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.G2).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.G1).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.G4).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.G3).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.H2).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.H1).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.H4).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.H3).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.I2).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.I1).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.I4).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.I3).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s1 = new File(indicatorDir, Const.J2).getAbsolutePath();
                String s2 = new File(indicatorDir, Const.J1).getAbsolutePath();
                String s3 = new File(indicatorDir, Const.J4).getAbsolutePath();
                String s4 = new File(indicatorDir, Const.J3).getAbsolutePath();

                NativeHelper.invoke(s1, s2, s3, s4);
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
