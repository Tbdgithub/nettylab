package com.saturn.sort.merge;

import org.junit.Test;

import java.io.File;

/**
 * Created by lyz on 2017/12/10.
 */
public class FileMerger_test {


    @Test
    public void test1()
    {
        FileMerger fileMerger=new FileMerger( new FilePair(new File("/sz/tempDir/sorted_3.txt"),new File("/sz/tempDir/sorted_4.txt")),
                new File("/sz/tempDir"),100,true);
        fileMerger.start();
    }

}
