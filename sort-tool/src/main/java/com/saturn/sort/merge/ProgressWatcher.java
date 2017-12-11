package com.saturn.sort.merge;

import java.util.concurrent.atomic.AtomicLong;

public class ProgressWatcher {

    private AtomicLong inputCounter=new AtomicLong(0);

    private AtomicLong tempCounter=new AtomicLong(0);
    private AtomicLong outputCounter=new AtomicLong(0);

    public AtomicLong getInputCounter() {
        return inputCounter;
    }

    public void setInputCounter(AtomicLong inputCounter) {
        this.inputCounter = inputCounter;
    }

    public AtomicLong getTempCounter() {
        return tempCounter;
    }

    public void setTempCounter(AtomicLong tempCounter) {
        this.tempCounter = tempCounter;
    }

    public AtomicLong getOutputCounter() {
        return outputCounter;
    }

    public void setOutputCounter(AtomicLong outputCounter) {
        this.outputCounter = outputCounter;
    }

    private long beginTime=System.nanoTime();

    public volatile boolean allFinished=false;

    public void start()
    {

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {


                while (!allFinished)
                {
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

    private void showCurrent()
    {
        long current=System.nanoTime()-beginTime;

        double costSecond= current/1.0E9;
        double tps=costSecond>0 ? inputCounter.get()/costSecond:0;
        System.out.println("input count:"+inputCounter.get() +" cost second:"+costSecond+" tps:"+tps);
    }
}
