package com.saturn.sort.merge;

import java.io.*;

/**
 * Created by lyz on 2017/12/10.
 */
public class FileMerger {


    private FilePair filePair;

    FileReader frLeft = null;
    BufferedReader brLeft = null;
    CustBufferedReader cbrLeft = null;

    FileReader frRight = null;
    BufferedReader brRight = null;
    CustBufferedReader cbrRight = null;

    FileWriter fw = null;
    BufferedWriter bw = null;
    CustBufferedWriter cbw = null;


    private File subDir;
    private int outputIndex = 0;

    static String outputFileTail = ".txt";
    static String lineSeperator = "\r\n";
    public static String outputFilehead = "sorted_";

    private boolean antiDuplicate;
    long lastSuccVal = Long.MIN_VALUE;

    private long fileLine = 0;
    private ProgressWatcher watcher;

    public long getFileLine() {
        return fileLine;
    }

    public void setFileLine(long fileLine) {
        this.fileLine = fileLine;
    }

    public FileMerger(FilePair filePair, File subDir, int outputIndex, boolean antiDup, ProgressWatcher watcher) {
        this.filePair = filePair;
        this.subDir = subDir;
        this.outputIndex = outputIndex;
        this.antiDuplicate = antiDup;
        this.watcher = watcher;

    }

    private String getOutputFileName(int outputFileIndex) {
        return outputFilehead + outputFileIndex + outputFileTail;
    }


    private void writeValue(Long val) throws Exception {
        if (antiDuplicate) {
            if (val != lastSuccVal) {

                //bw.write(val + lineSeperator);
                cbw.write(val + lineSeperator);

                ++fileLine;
                lastSuccVal = val;
            } else {
                watcher.getAntiDupMergeCounter().incrementAndGet();
            }
        } else {
            //bw.write(val + lineSeperator);
            cbw.write(val + lineSeperator);
            ++fileLine;
        }

        //watcher.getMergeWriteCounter().incrementAndGet();

    }

    public void start() {
        System.out.println("begin merge: " + filePair.print());


        try {

            if (!subDir.exists()) {
                subDir.mkdir();
            }

            if (filePair.getLeftFile() == null && filePair.getRightFile() == null) {
                throw new Exception("bug,can not null all");
            }

            File target = new File(subDir, getOutputFileName(outputIndex));

            //Single File process
            File single = null;

            if (filePair.getLeftFile() != null && filePair.getRightFile() == null) {
                single = filePair.getLeftFile();
            } else if (filePair.getRightFile() != null && filePair.getLeftFile() == null) {
                single = filePair.getRightFile();
            }

            if (single != null) {

                FileHelper.copyFile(single, target);
                return;
            }


            System.out.println("pair processing...");

            //algorithm
            //a  read until end,
            // b read until end,
            //tail copy to

            frLeft = new FileReader(filePair.getLeftFile());
            brLeft = new BufferedReader(frLeft);
            cbrLeft = new CustBufferedReader(brLeft,watcher);

            frRight = new FileReader(filePair.getRightFile());
            brRight = new BufferedReader(frRight);
            cbrRight = new CustBufferedReader(brRight,watcher);

            String leftLine = null;
            String rightLine = null;

            long leftVal = Long.MIN_VALUE;
            long rightVal = Long.MIN_VALUE;
            boolean leftFinished = false;
            boolean rightFinished = false;

            fw = new FileWriter(target);
            bw = new BufferedWriter(fw);
            cbw=new CustBufferedWriter(bw,watcher);

            boolean leftSucc = true;
            boolean rightSucc = true;

            do {

                if (leftSucc) {
                    //leftLine = brLeft.readLine();
                    leftLine = cbrLeft.readLine();
                    if (CommonHelper.isNullOrEmpty(leftLine)) {
                        leftFinished = true;
                    } else {
                        leftVal = Long.parseLong(leftLine);
                    }
                }


                if (rightSucc) {
                    //rightLine = brRight.readLine();
                    rightLine = cbrRight.readLine();
                    if (CommonHelper.isNullOrEmpty(rightLine)) {
                        rightFinished = true;
                    } else {
                        rightVal = Long.parseLong(rightLine);
                    }
                }

                //check value

                if (!leftFinished && !rightFinished) {

                    long succVal;

                    if (leftVal < rightVal) {
                        succVal = leftVal;
                        leftSucc = true;
                        rightSucc = false;
                        // leftLine = brLeft.readLine();
                    } else {
                        succVal = rightVal;
                        //rightLine=brRight.readLine();
                        rightSucc = true;
                        leftSucc = false;
                    }

                    watcher.getMergeCompareCounter().incrementAndGet();
                    //bw.write(succVal + lineSeperator);
                    writeValue(succVal);
                    continue;
                }

                if (!leftFinished && rightFinished) {
                    //read all;
                    //bw.write(leftVal + lineSeperator); //flush current
                    writeValue(leftVal);
                    do {
                        //leftLine = brLeft.readLine();
                        leftLine = cbrLeft.readLine();
                        if (CommonHelper.isNullOrEmpty(leftLine)) {
                            leftFinished = true;
                        } else {
                            leftVal = Long.parseLong(leftLine);
                            //bw.write(leftVal + lineSeperator);
                            writeValue(leftVal);
                        }
                    }
                    while (!leftFinished);
                }


                if (!rightFinished && leftFinished) {
                    //read all;
                    //bw.write(rightVal + lineSeperator); //flush current
                    writeValue(rightVal);
                    do {
                        //rightLine = brRight.readLine();
                        rightLine = cbrRight.readLine();
                        if (CommonHelper.isNullOrEmpty(rightLine)) {
                            rightFinished = true;
                        } else {
                            rightVal = Long.parseLong(rightLine);
                            //bw.write(rightVal + lineSeperator);
                            writeValue(rightVal);
                        }
                    }
                    while (!rightFinished);
                }


            } while ((!leftFinished)
                    &&
                    (!rightFinished)
                    );

            System.out.println("finished merge: " + filePair.print() + " to " + target.getAbsolutePath());

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            close(cbrLeft);
            close(brLeft);
            close(frLeft);

            close(cbrRight);
            close(brRight);
            close(frRight);


            close(cbw);
            close(bw);
            close(fw);
        }


    }


    private void close(CustBufferedReader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void close(CustBufferedWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

}
