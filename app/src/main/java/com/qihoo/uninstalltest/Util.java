package com.qihoo.uninstalltest;


import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class Util {

    public static String getDstPath(Context context) {
        File dst = new File(context.getFilesDir(), "eternity");
        return dst.getAbsolutePath();
    }

    public static File getIndicatorDir(Context context) {
        return context.getDir(Const.INDICATOR_DIR, Context.MODE_PRIVATE);
    }

    public static void create(Context context, String name) {
        File dirFile = context.getDir(Const.INDICATOR_DIR, Context.MODE_PRIVATE);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        try {
            createNewFile(dirFile, name);
        } catch (IOException e) {
            Log.e("MainActivity", "", e);
        }
    }

    private static void createNewFile(File dirFile, String fileName) throws IOException {
        File file = new File(dirFile, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

}
