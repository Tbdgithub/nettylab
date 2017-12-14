package com.saturn.sort.merge;

import org.junit.Test;

import java.io.File;

public class FileCutter_test {

    @Test
    public void test1()
    {
        try {
            FileCutter cutter=new FileCutter(new File("/sz/FileCutter/inputDir"),
                    new File("/sz/FileCutter/outputDir"),10000,new ProgressWatcher()
                    );
            cutter.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
