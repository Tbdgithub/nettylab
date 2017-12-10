package com.saturn.sort.merge;

import java.io.File;

/**
 * Created by lyz on 2017/12/10.
 */
public class FilePair {

    private File leftFile;

    private File rightFile;

    public File getLeftFile() {
        return leftFile;
    }

    public void setLeftFile(File leftFile) {
        this.leftFile = leftFile;
    }

    public File getRightFile() {
        return rightFile;
    }

    public void setRightFile(File rightFile) {
        this.rightFile = rightFile;
    }

    public  FilePair(File leftFile,File rightFile)
    {
        this.leftFile=leftFile;
        this.rightFile=rightFile;

    }

    public String print()
    {
        return  "left file:"+ ( leftFile!=null ?leftFile.getAbsolutePath():"null" )+ "right file:"+( rightFile!=null ?rightFile.getAbsolutePath():"null");
    }
}
