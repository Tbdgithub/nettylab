package com.saturn.sort.merge;

import java.io.*;

/**
 * Created by lyz on 2017/12/10.
 */
public class FileHelper {


    static int MaxRecursiveLevel = 32;

    public static void rmSubDirsForce(File dir) {
        cleanDirRecursize(dir, 0);
        //dir.delete();

    }

    public static void cleanDirRecursize(File dir, int level) {
        if (level >= MaxRecursiveLevel) {
            System.out.println("over limit MaxRecursiveLevel:" + MaxRecursiveLevel);
            return;
        }


        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });

        if (files.length == 0 && dir.isDirectory()) {
            boolean succ = dir.delete();
            System.out.println("clean tmp dir:" + dir.getAbsolutePath() + " result:" + succ);
            return;
        }


        for (File file : files) {
            if (!file.isDirectory()) {

                if (file.getName().indexOf(Constants.outputFileTail) != -1) {
                    boolean succ = file.delete();
                    System.out.println("clean tmp file:" + file.getAbsolutePath() + " result:" + succ);

                } else {
                    System.out.println("only file type :" + Constants.outputFileTail + " can be delete");
                }
            } else {
                cleanDirRecursize(file, level + 1);
                file.delete();
            }


        }


    }

    public static void copyFile(File src, File target) {

        //todo 1024*1024*16  16M
        copyFile(src, target, 1024 * 1024 * 16);
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
