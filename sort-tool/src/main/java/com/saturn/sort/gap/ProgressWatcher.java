package com.saturn.sort.gap;

import com.saturn.sort.merge.CommonHelper;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProgressWatcher {


    private AtomicLong inputCounter = new AtomicLong(0);
    private AtomicLong outputCounter = new AtomicLong();


    private long beginTime = System.nanoTime();

    public volatile boolean allFinished = false;

    private int showIntervalSecond=5;
    final int minVal=5;

    public AtomicLong getInputCounter() {
        return inputCounter;
    }

    public void setInputCounter(AtomicLong inputCounter) {
        this.inputCounter = inputCounter;
    }

    public AtomicLong getOutputCounter() {
        return outputCounter;
    }

    public void setOutputCounter(AtomicLong outputCounter) {
        this.outputCounter = outputCounter;
    }

    public int getShowIntervalSecond() {

        return showIntervalSecond;
    }

    public void setShowIntervalSecond(int showIntervalSecond) {

        int temp= showIntervalSecond>=minVal?showIntervalSecond:minVal;
        this.showIntervalSecond = temp;
    }



    public void start() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                while (!allFinished) {
                    showCurrent();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                showCurrent();
                System.out.println("watcher finished");

            }
        });

        thread.start();

    }

    private void showCurrent() {
        long current = System.nanoTime() - beginTime;
        double costSecond = current / 1.0E9;

        String costSecondFormat= CommonHelper.printDecimalRadix2(costSecond);
        String tpsFormat= CommonHelper.printDecimalRadix2(costSecond > 0 ? inputCounter.get() / costSecond : 0);

        System.out.println("inputCounter count:" + inputCounter.get() + " cost second:" + costSecondFormat + " tps:" + tpsFormat);

         tpsFormat= CommonHelper.printDecimalRadix2(costSecond > 0 ? outputCounter.get() / costSecond : 0);
        System.out.println("outputCounter count:" + outputCounter.get() + " cost second:" + costSecondFormat + " tps:" + tpsFormat);


    }

    public void close()
    {
        this.allFinished=true;
    }
}
