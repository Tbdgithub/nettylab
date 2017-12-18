package com.saturn.sort.merge;

import com.google.gson.Gson;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Created by lyz on 2017/12/10.
 */
public class MergeSorter {

    private File inputDirFile;
    private File outputDirFile;
    private File tempDirFile;

    Config config;


    MergeSorter(Config config) throws Exception {
        this.config = config;
    }

    private void ensureEmptyTempDir(File tempDir) {

        if (tempDirFile.exists()) {
            FileHelper.rmSubDirsForce(tempDir);
        }

        if (!tempDirFile.exists()) {
            tempDirFile.mkdir();
        }


    }


    public void start() throws Exception {
        System.out.println("starting...");


        ProgressWatcher watcher=new ProgressWatcher();
        watcher.setShowIntervalSecond(config.getProgressShowIntervalSecond());
        watcher.start();

        inputDirFile = new File(config.getInputDir());
        outputDirFile = new File(config.getOutputDir());
        tempDirFile = new File(config.getTempDir());

        if(config.getMaxLinePerFile()<=0)
        {
            throw new Exception("bad config getMaxLinePerFile:"+config.getMaxLinePerFile());
        }

        ensureEmptyTempDir(tempDirFile);
        if (!outputDirFile.exists()) {
            outputDirFile.mkdir();
        }


        if(config.isNeedCut()) {
            FileCutter cutter = new FileCutter(inputDirFile, tempDirFile, config.getMaxLinePerFile(), watcher);
            cutter.start();
            System.out.println("File cut finished");
        }
        else
        {
            System.out.println("skip cut process");
        }

        DirMerger dirMerger = new DirMerger(tempDirFile,config.isAntiDuplicate(),watcher);
        dirMerger.start();

        File mergeFinished = dirMerger.getFinishedFile();
        File tagetOutput = new File(outputDirFile, "succ.txt");
        FileHelper.copyFile(mergeFinished, tagetOutput);
        System.out.println("finished file:" + tagetOutput.getAbsolutePath());

        FileHelper.rmSubDirsForce(tempDirFile);
        System.out.println("temp dir cleaned:" + tempDirFile.getAbsolutePath());
        System.out.println("All finished");
        watcher.close();
    }


    public static void main(String[] args) {
        try {
            if (args.length < 2) {
                System.out.println("usage: MergeSorter -c ./config.json");
                return;
            }

            File configFile = new File(args[1]);
            String configText = FileHelper.readAllText(configFile, Charset.forName("utf-8"));

            Config config = new Gson().fromJson(configText, Config.class);

            MergeSorter processor = new MergeSorter(config);
            processor.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
