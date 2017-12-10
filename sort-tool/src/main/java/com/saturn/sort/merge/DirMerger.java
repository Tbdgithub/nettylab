package com.saturn.sort.merge;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyz on 2017/12/10.
 */
public class DirMerger {

    private File rootDir;

    private int outputIndex;

    private File currentSubDir;

    private File mergeFinishedFile;

    public DirMerger(File dir) {
        this.rootDir = dir;

    }




    public void start() throws Exception {
        System.out.println(this.getClass().getSimpleName() + " starting...");

        int outputIndex = 0;

        boolean allFinished = false;
        File parent = rootDir;
        do {

            List<FilePair> filePairs = dispatch(parent);

            File subDir = getSubDir(parent);
            for (FilePair pair : filePairs) {
                ++outputIndex;
                FileMerger merger = new FileMerger(pair, subDir, outputIndex, true);
                merger.start();
            }

            parent = subDir;
            allFinished = merged2One(parent);

        }
        while (!allFinished);


        this.mergeFinishedFile = getMergedFile(parent);
        System.out.println(this.getClass().getSimpleName() + " finished merge file:"+mergeFinishedFile.getAbsolutePath());


    }

    public File getFinishedFile() {
        return mergeFinishedFile;
    }

    private File getMergedFile(File parent) {
        File[] files = parent.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {

                if (pathname.getName().indexOf(FileCutter.outputFilehead) != -1) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        File file = files[0];
        return file;
    }


    private boolean merged2One(File dir) throws Exception {
        if (!dir.isDirectory()) {
            throw new Exception("bug dir:" + dir.getAbsolutePath() + "  is not directory");
        }

        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {


                if (pathname.getName().indexOf(FileCutter.outputFileTail) != -1) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        if (files.length == 0) {
            throw new Exception("bug file len must not 0");
        }

        if (files.length == 1) {
            return true;
        }

        return false;

    }

    private File getSubDir(File parent) {
        File result = new File(parent, "tempDir");
        return result;

    }


    private List<FilePair> dispatch(File parent) {
        List<FilePair> result = new ArrayList<FilePair>();

        File[] files = parent.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {

                if (pathname.getName().indexOf(FileCutter.outputFilehead) != -1) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        for (int i = 0; i < files.length; i += 2) {

            File left = files[i];
            File right = (i + 1 < files.length) ? files[i + 1] : null;
            FilePair pair = new FilePair(left, right);
            result.add(pair);
        }


        System.out.println("dispatch finished");
        return result;
    }


}
