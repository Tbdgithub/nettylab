package com.saturn.sort.merge;

import java.io.*;

/**
 * Created by lyz on 2017/12/10.
 */
public class FileHelper {


    public static  void cleanFilesInDir(File dir)
    {

    }

    public static  void cleanDirForce(File dir)
    {

    }

    public static void copyFile(File src, File target) {

        //todo 1024*1024*16  16M
        copyFile(src, target, 1024*1024*16);
    }

    public static void copyFile(File src, File target, int buffLen) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            fileInputStream = new FileInputStream(src);
            fileOutputStream = new FileOutputStream(target);
            byte[] buff = new byte[buffLen];

            int read = 0;
            do {


                read = fileInputStream.read(buff, 0, buff.length);
                if (read > 0) {
                    fileOutputStream.write(buff, 0, read);
                }

            }
            while (read > 0);


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            close(fileInputStream);
            close(fileOutputStream);

        }
    }

    private static void close(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void close(OutputStream stream) {
        try {
            if (stream != null) {
                stream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
