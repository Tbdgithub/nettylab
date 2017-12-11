package com.saturn.sort.merge;

import org.junit.Test;

import java.io.File;

/**
 * Created by lyz on 2017/12/10.
 */
public class FileHelper_test {

    @Test
    public void test1()
    {
        FileHelper.copyFile( new File("/sz/tempDir/sorted_1.txt"), new File("/sz/tempDir/copy_1.txt"),4);
    }

    @Test
    public void countFileLine_5www_test()
    {
        double before=System.nanoTime();
       long count= FileHelper.countFileLine( new File("/sz/MergeSorter/inputDir/5ww-gap-uid.txt"));
       double ms= (System.nanoTime()-before)/1.0e6;
       System.out.println("line :"+count+" cost mini second:"+ms);

    }

    @Test
    public void countFileLine_15ww_test()
    {
        System.out.println("begin...");
        double before=System.nanoTime();
        long count= FileHelper.countFileLine( new File("/sz/MergeSorter/inputDir/15ww-uid.txt"));
        double ms= (System.nanoTime()-before)/1.0e6;
        System.out.println("line :"+count+" cost mini second:"+ms);

    }

    @Test
    public void countFileLine_20y_test()
    {
        System.out.println("begin...");
        double before=System.nanoTime();
        long count= FileHelper.countFileLine( new File("/sz/MergeSorter/outputDir/succ.txt"));
        double ms= (System.nanoTime()-before)/1.0e6;
        System.out.println("line :"+count+" cost mini second:"+ms);

    }
}
