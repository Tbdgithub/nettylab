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
}
