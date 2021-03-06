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

    private File mergeFinishedFile;
    private boolean antiDuplicate;
    private ProgressWatcher watcher;

    public DirMerger(File dir,boolean antiDuplicate,ProgressWatcher watcher) {
        this.rootDir = dir;
        this.antiDuplicate=antiDuplicate;
        this.watcher=watcher;

    }


    public void start() throws Exception {
        System.out.println(this.getClass().getSimpleName() + " starting...");

        int outputIndex = 0;

        boolean allFinished = false;
        File parent = rootDir;

        FileMerger lastMerger=null;
        do {

            List<FilePair> filePairs = dispatch(parent);

            File subDir = getSubDir(parent);
            for (FilePair pair : filePairs) {
                ++outputIndex;
                FileMerger merger = new FileMerger(pair, subDir, outputIndex, antiDuplicate,watcher);
                merger.start();
                lastMerger=merger;
            }

            //clean tempdir
            FileHelper.cleanFilesOnly(parent);
            parent = subDir;
            allFinished = merged2One(parent);
            watcher.getCurrentLevel().incrementAndGet();

            if(watcher.getCurrentLevel().get()>Constants.MaxLevel)
            {
                throw new Exception("over limit MaxLevel:"+watcher.getCurrentLevel().get());
            }

        }
        while (!allFinished);

        this.mergeFinishedFile = getMergedFile(parent);
        System.out.println(this.getClass().getSimpleName() + " finished merge file:"+mergeFinishedFile.getAbsolutePath());

        System.out.println("final file len:"+lastMerger.getFileLine());



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


                if (pathname.getName().indexOf(Constants.outputFileTail) != -1) {
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
