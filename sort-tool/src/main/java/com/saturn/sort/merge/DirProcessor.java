package com.saturn.sort.merge;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyz on 2017/12/10.
 */
public class DirProcessor {

    private String inputDir;
    private String outputDir;
    private String tempDir;

    private File inputDirFile;
    private File outputDirFile;
    private File tempDirFile;

    static int MaxLinePerFile = 5;

    DirProcessor(String inputDir, String outputDir, String tempDir) throws Exception {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
        this.tempDir = tempDir;
    }

    private void ensureEmptyTempDir(File tempDir)
    {

        if (tempDirFile.exists()) {
            FileHelper.rmSubDirsForce(tempDir);
        }

        if (!tempDirFile.exists()) {
            tempDirFile.mkdir();
        }


    }


    public void start() throws Exception {
        System.out.println("starting...");

        inputDirFile = new File(inputDir);
        outputDirFile = new File(outputDir);
        tempDirFile = new File(tempDir);

        ensureEmptyTempDir(tempDirFile);
        if (!outputDirFile.exists()) {
            outputDirFile.mkdir();
        }



        FileCutter cutter = new FileCutter(inputDirFile, tempDirFile, MaxLinePerFile);
        cutter.start();

        System.out.println("File cut finished");

        DirMerger dirMerger = new DirMerger(tempDirFile);
        dirMerger.start();

        File mergeFinished = dirMerger.getFinishedFile();
        File tagetOutput=new File(outputDirFile,"succ.txt");
        FileHelper.copyFile(mergeFinished,tagetOutput);
        System.out.println("finished file:"+tagetOutput.getAbsolutePath());

        FileHelper.rmSubDirsForce(tempDirFile);
        System.out.println("temp dir cleaned:"+tempDirFile.getAbsolutePath());
        System.out.println("All finished");
    }


    public static void main(String[] args) {
        String inputDir = args[1];
        String ouputDir = args[3];
        String tempDir = args[5];

        try {
            DirProcessor processor = new DirProcessor(inputDir, ouputDir, tempDir);
            processor.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
