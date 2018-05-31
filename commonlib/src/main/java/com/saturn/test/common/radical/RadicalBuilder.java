package com.saturn.test.common.radical;

public abstract class RadicalBuilder {

    protected int n;

    protected int count = 0;
   protected static double small = 1e-15;

    protected static int maxCount = 100;

    public RadicalBuilder() {

    }

    protected void printTryCount()
    {
        if (count >= maxCount) {
            System.out.println("I have try my best,try count:" + count);
        } else {
            System.out.println("So easy,try count:" + count);
        }
    }


    public abstract double process();



    @Override
    public String toString() {
        return "n:" + n + " process count:" + count;

    }


}
