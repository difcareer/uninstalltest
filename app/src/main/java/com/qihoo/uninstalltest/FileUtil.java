package com.qihoo.uninstalltest;

import android.content.Context;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {
    public static final int MAX_FILE_SIZE_TO_GET_MD5 = 10485760;
    private static final String TAG = "FileUtil";
    private static final String TIMESTAMP_EXT = ".timestamp";

    public FileUtil() {
    }

    public static byte[] readFileByte(File file) {
        FileInputStream fis = null;
        FileChannel fc = null;
        byte[] data = null;

        try {
            fis = new FileInputStream(file);
            fc = fis.getChannel();
            data = new byte[(int)fc.size()];
            fc.read(ByteBuffer.wrap(data));
        } catch (FileNotFoundException var20) {
            var20.printStackTrace();
        } catch (Exception var21) {
            var21.printStackTrace();
        } finally {
            if(fc != null) {
                try {
                    fc.close();
                } catch (IOException var19) {
                    ;
                }
            }

            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException var18) {
                    ;
                }
            }

        }

        return data;
    }

    public static boolean writeByteFile(byte[] bytes, File file) {
        if(bytes == null) {
            return false;
        } else {
            boolean flag = false;
            FileOutputStream fos = null;

            try {
                fos = new FileOutputStream(file);
                fos.write(bytes);
                flag = true;
            } catch (FileNotFoundException var20) {
                var20.printStackTrace();
            } catch (Exception var21) {
                var21.printStackTrace();
            } finally {
                if(fos != null) {
                    try {
                        fos.flush();
                    } catch (Exception var19) {
                        ;
                    }

                    try {
                        fos.close();
                    } catch (IOException var18) {
                        ;
                    }
                }

            }

            return flag;
        }
    }

    public static boolean copyFile(File srcFile, File destFile) {
        boolean result = false;
        if(srcFile != null && srcFile.exists()) {
            FileInputStream in = null;

            try {
                in = new FileInputStream(srcFile);
                result = copyToFile(in, destFile);
            } catch (Exception var13) {
                var13.printStackTrace();
            } finally {
                if(in != null) {
                    try {
                        in.close();
                    } catch (Exception var12) {
                        ;
                    }
                }

            }
        }

        return result;
    }

    public static boolean copyToFile(InputStream inputStream, File destFile) {
        boolean result = false;
        FileOutputStream out = null;

        try {
            if(!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            } else if(destFile.exists()) {
                destFile.delete();
            }

            out = new FileOutputStream(destFile);
            byte[] e = new byte[4096];

            int bytesRead;
            while((bytesRead = inputStream.read(e)) >= 0) {
                out.write(e, 0, bytesRead);
            }

            result = true;
        } catch (Exception var18) {
            result = false;
        } finally {
            if(out != null) {
                try {
                    out.flush();
                } catch (Exception var17) {
                    result = false;
                }

                try {
                    out.close();
                } catch (Exception var16) {
                    result = false;
                }
            }

        }

        if(!result) {
            destFile.delete();
        }

        return result;
    }

    public static void deleteDir(String filepath) {
        File f = new File(filepath);
        if(f.exists() && f.isDirectory()) {
            File[] delFiles = f.listFiles();
            if(delFiles != null) {
                if(delFiles.length == 0) {
                    f.delete();
                } else {
                    File[] arr$ = delFiles;
                    int len$ = delFiles.length;

                    for(int i$ = 0; i$ < len$; ++i$) {
                        File delFile = arr$[i$];
                        if(delFile != null) {
                            if(delFile.isDirectory()) {
                                deleteDir(delFile.getAbsolutePath());
                            }

                            delFile.delete();
                        }
                    }

                    f.delete();
                }
            }
        }

    }

    public static void deleteFile(String filepath) {
        File file = new File(filepath);
        if(file.exists()) {
            if(file.isDirectory()) {
                File[] delFiles = file.listFiles();
                if(delFiles != null) {
                    File[] arr$ = delFiles;
                    int len$ = delFiles.length;

                    for(int i$ = 0; i$ < len$; ++i$) {
                        File delFile = arr$[i$];
                        if(delFile != null) {
                            deleteFile(delFile.getAbsolutePath());
                        }
                    }
                }
            }

            file.delete();
        }

    }

    public static boolean safeRenameTo(File src, File dst) {
        if(!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }

        boolean ret = src.renameTo(dst);
        if(!ret) {
            ret = copyFile(src, dst);
            if(ret) {
                if(src.isDirectory()) {
                    try {
                        deleteDir(src.getAbsolutePath());
                    } catch (Exception var4) {
                        ;
                    }
                } else {
                    src.delete();
                }
            }
        }

        return ret;
    }

    public static long countDirSize(File aDir) {
        long ret = 0L;
        if(aDir != null && aDir.isDirectory()) {
            File[] files = aDir.listFiles();
            if(files != null) {
                File[] arr$ = files;
                int len$ = files.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    File file = arr$[i$];
                    if(file != null) {
                        if(file.isDirectory()) {
                            ret += countDirSize(file);
                        } else {
                            ret += file.length();
                        }
                    }
                }
            }
        }

        return ret;
    }

    public static void deleteFile(File file) {
        if(file != null && file.exists()) {
            if(file.isDirectory()) {
                File[] delFiles = file.listFiles();
                if(delFiles != null) {
                    File[] arr$ = delFiles;
                    int len$ = delFiles.length;

                    for(int i$ = 0; i$ < len$; ++i$) {
                        File delFile = arr$[i$];
                        deleteFile(delFile);
                    }
                }
            }

            file.delete();
        }

    }

    public static boolean copyFolder(String oldPath, String newPath) {
        try {
            File e = new File(newPath);
            if(!e.exists()) {
                e.mkdirs();
            }

            File oldFolder = new File(oldPath);
            String[] file = oldFolder.list();
            File temp = null;
            if(file != null) {
                String[] arr$ = file;
                int len$ = file.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    String fileName = arr$[i$];
                    if(oldPath.endsWith(File.separator)) {
                        temp = new File(oldPath + fileName);
                    } else {
                        temp = new File(oldPath + File.separator + fileName);
                    }

                    if(temp.isFile()) {
                        FileInputStream input = new FileInputStream(temp);
                        FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName().toString());
                        byte[] b = new byte[5120];

                        int len;
                        while((len = input.read(b)) != -1) {
                            output.write(b, 0, len);
                        }

                        output.flush();
                        output.close();
                        input.close();
                    }

                    if(temp.isDirectory()) {
                        copyFolder(oldPath + "/" + fileName, newPath + "/" + fileName);
                    }
                }
            }

            return true;
        } catch (Exception var14) {
            return false;
        }
    }

    public static long getFileTimestamp(Context c, String filename) {
        FileInputStream fis = null;

        try {
            fis = c.openFileInput(filename + ".timestamp");
        } catch (Exception var4) {
            ;
        }

        return fis != null?getTimestampFromStream(fis):0L;
    }

    public static long getAssetTimestamp(Context c, String filename) {
        InputStream fis = null;

        try {
            fis = c.getAssets().open(filename + ".timestamp");
        } catch (Exception var4) {
            ;
        }

        return fis != null?getTimestampFromStream(fis):0L;
    }

    private static long getTimestampFromStream(InputStream fis) {
        DataInputStream dis = null;

        try {
            dis = new DataInputStream(fis);
            String e = dis.readLine();
            long var3 = Long.parseLong(e);
            return var3;
        } catch (Exception var14) {
            ;
        } finally {
            try {
                if(dis != null) {
                    dis.close();
                }

                if(fis != null) {
                    fis.close();
                }
            } catch (Exception var13) {
                ;
            }

        }

        return 0L;
    }

    public static void setFileTimestamp(Context c, String filename, long timeStamp) {
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {
            fos = c.openFileOutput(filename + ".timestamp", 0);
            dos = new DataOutputStream(fos);
            dos.writeBytes(String.valueOf(timeStamp));
        } catch (IOException var15) {
            ;
        } finally {
            try {
                if(dos != null) {
                    dos.close();
                }

                if(fos != null) {
                    fos.close();
                }
            } catch (Exception var14) {
                ;
            }

        }

    }

    public static boolean copyAssetToFile(Context c, String name, File target, boolean setTimestamp) {
        InputStream sourceStream = null;
        FileOutputStream targetStream = null;

        try {
            sourceStream = c.getAssets().open(name);
            targetStream = new FileOutputStream(target);
            copyStream(sourceStream, targetStream);
            if(setTimestamp) {
                setFileTimestamp(c, name, getAssetTimestamp(c, name));
            }

            boolean e = true;
            return e;
        } catch (IOException var20) {
            ;
        } finally {
            if(sourceStream != null) {
                try {
                    sourceStream.close();
                } catch (Exception var19) {
                    ;
                }
            }

            if(targetStream != null) {
                try {
                    targetStream.close();
                } catch (Exception var18) {
                    ;
                }
            }

        }

        return false;
    }

    public static void copyStream(InputStream source, OutputStream target) throws IOException {
        boolean BUF_SIZE = true;
        byte[] buffer = new byte[4096];
        boolean length = false;

        int length1;
        while((length1 = source.read(buffer)) > 0) {
            target.write(buffer, 0, length1);
        }

        target.flush();
    }

    public static void deleteDirectory(File directory) throws IOException {
        if(directory.exists()) {
            if(directory.isDirectory()) {
                cleanDirectory(directory);
            }

            if(!directory.delete()) {
                String message = "Unable to delete directory " + directory + ".";
                throw new IOException(message);
            }
        }
    }

    public static void cleanDirectory(File directory) throws IOException {
        String var9;
        if(!directory.exists()) {
            var9 = directory + " does not exist";
            throw new IllegalArgumentException(var9);
        } else if(!directory.isDirectory()) {
            var9 = directory + " is not a directory";
            throw new IllegalArgumentException(var9);
        } else {
            File[] files = directory.listFiles();
            if(files == null) {
                throw new IOException("Failed to list contents of " + directory);
            } else {
                IOException exception = null;
                File[] arr$ = files;
                int len$ = files.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    File file = arr$[i$];

                    try {
                        forceDelete(file);
                    } catch (IOException var8) {
                        exception = var8;
                    }
                }

                if(null != exception) {
                    throw exception;
                }
            }
        }
    }

    public static void forceDelete(File file) throws IOException {
        if(file.isDirectory()) {
            deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if(!file.delete()) {
                if(!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }

                String message = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }

    }
}
