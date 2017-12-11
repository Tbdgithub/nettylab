package com.saturn.sort.merge;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lyz on 2017/12/10.
 */
public class FileCutter {

    private File inputDirFile;
    private File outputDirFile;
    private int maxLinePerFile;
    private AtomicInteger numCounter = new AtomicInteger(0);

    static String lineSeperator = "\r\n";
    public static String outputFilehead = "sorted_";
    int outputFileIndex = 0;

    private ProgressWatcher watcher;

    public FileCutter(File inputDirFile, File outputDirFile, int maxLinePerFile, ProgressWatcher watcher) throws Exception {
        this.inputDirFile = inputDirFile;
        this.outputDirFile = outputDirFile;
        this.maxLinePerFile = maxLinePerFile;
        this.watcher=watcher;

        if (maxLinePerFile < 1) {
            throw new Exception("bad maxLinePerFile");
        }
    }

    public void start() {
        List<File> files0 = collectFiles(inputDirFile);


        for (File item : files0) {

            cutAndSort(item, true);
        }

    }


    private void cutAndSort(File file, boolean asc) {

        FileReader fr = null;
        BufferedReader br = null;

        FileWriter fw = null;
        BufferedWriter bw = null;

        long currentLine = 0;
        long totalLine = 0;


        List<Long> bucket = new ArrayList<Long>(maxLinePerFile);

        try {

            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line = br.readLine();
            do {

                if (CommonHelper.isNullOrEmpty(line)) {
                    break;
                }

                this.watcher.getInputCounter().incrementAndGet();
                ++totalLine;
                ++currentLine;

                if (currentLine > maxLinePerFile) {
                    throw new Exception("bug !");
                }

                //new file
                if (currentLine == 1) {
                    ++outputFileIndex;
                    File outputFile = new File(outputDirFile, getOutputFileName(outputFileIndex));
                    fw = new FileWriter(outputFile);
                    bw = new BufferedWriter(fw);
                }

                if (currentLine == maxLinePerFile) {

                    //reset
                    bucket.add(Long.parseLong(line));
                    //sort and write

                    flushBucket(bw, bucket,asc);

                    bucket.clear();
                    currentLine = 0;
                    //++outputFileIndex;
                    bw.close();
                    fw.close();

                } else {

                    bucket.add(Long.parseLong(line));
                }

                line = br.readLine();
            }
            while (!CommonHelper.isNullOrEmpty(line));


            flushBucket(bw, bucket,asc);

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

    private void flushBucket(BufferedWriter bw, List<Long> bucket,boolean sortAsc) throws IOException {


        final  boolean sortDirection=sortAsc;

        if (bucket.size() > 0) {

            bucket.sort(new Comparator<Long>() {
                @Override
                public int compare(Long o1, Long o2) {

                    if (sortDirection) {
                        return o1.compareTo(o2);
                    } else {
                        return o2.compareTo(o1);
                    }
                }
            });

            for (Long item : bucket) {
                bw.write(String.valueOf(item) + lineSeperator);
            }
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

    private   String getOutputFileName(int outputFileIndex) {
        return outputFilehead + outputFileIndex + Constants.outputFileTail;
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


                if (pathname.getName().indexOf(Constants.outputFileTail) != -1) {
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
