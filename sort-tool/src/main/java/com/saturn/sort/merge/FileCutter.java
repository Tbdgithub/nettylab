package com.saturn.sort.merge;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lyz on 2017/12/10.
 */
public class FileCutter {

    private File inputDirFile;
    private File outputDirFile;
    private int maxLinePerFile;
    private AtomicInteger numCounter = new AtomicInteger(0);
    static String outputFileTail = ".txt";
    static String lineSeperator = "\r\n";
    static String outputFilehead = "tmpout_";
    int outputFileIndex = 0;

    public FileCutter(File inputDirFile, File outputDirFile, int maxLinePerFile) throws Exception {
        this.inputDirFile = inputDirFile;
        this.outputDirFile = outputDirFile;
        this.maxLinePerFile = maxLinePerFile;

        if (maxLinePerFile < 1) {
            throw new Exception("bad maxLinePerFile");
        }
    }

    public void start() {
        List<File> files0 = collectFiles(inputDirFile);


        for (File item : files0) {
            cutFile(item);
        }

    }


    private void cutFile(File file) {

        FileReader fr = null;
        BufferedReader br = null;

        FileWriter fw = null;
        BufferedWriter bw = null;

        ++outputFileIndex;
        int currentLine = 0;
        long totalLine = 0;

        try {

            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line = br.readLine();
            do {

                if (CommonHelper.isNullOrEmpty(line)) {
                    break;
                }

                ++totalLine;
                ++currentLine;

                if (currentLine > maxLinePerFile) {
                    throw new Exception("bug !");
                }

                //new file
                if (currentLine == 1) {
                    File outputFile = new File(outputDirFile, getOutputFileName(outputFileIndex));
                    fw = new FileWriter(outputFile);
                    bw = new BufferedWriter(fw);
                }

                if (currentLine == maxLinePerFile) {

                    //reset
                    bw.write(line + lineSeperator);
                    currentLine = 0;
                    ++outputFileIndex;
                    bw.close();
                    fw.close();

                } else {
                    bw.write(line + lineSeperator);
                }

                line = br.readLine();
            }
            while (!CommonHelper.isNullOrEmpty(line));

            System.out.println("total line:" + totalLine);


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            close(br);
            close(fr);

            close(bw);
            close(fw);
        }

    }

    private String getOutputFileName(int outputFileIndex) {
        return outputFilehead + outputFileIndex + outputFileTail;
    }

    private void close(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void close(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<File> collectFiles(File inputDir) {
        List<File> result = new ArrayList<File>();
        File[] files = inputDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {


                if (pathname.getName().indexOf(outputFileTail) != -1) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        for (File f : files) {
            result.add(f);
        }

        return result;
    }


}
