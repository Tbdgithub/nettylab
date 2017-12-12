package com.saturn.sort.merge;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProgressWatcher {

    private AtomicInteger currentLevel = new AtomicInteger(0);

    private AtomicLong cutCounter = new AtomicLong(0);
    private AtomicLong readSourceCounter = new AtomicLong();
    private AtomicLong mergeCompareCounter = new AtomicLong();
    private AtomicLong mergeWriteCounter = new AtomicLong();

    private AtomicInteger antiDupMergeCounter=new AtomicInteger();

    private long beginTime = System.nanoTime();

    public volatile boolean allFinished = false;

    public AtomicInteger getAntiDupMergeCounter() {
        return antiDupMergeCounter;
    }

    public void setAntiDupMergeCounter(AtomicInteger antiDupMergeCounter) {
        this.antiDupMergeCounter = antiDupMergeCounter;
    }

    public AtomicInteger getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(AtomicInteger currentLevel) {
        this.currentLevel = currentLevel;
    }

    public AtomicLong getCutCounter() {
        return cutCounter;
    }

    public void setCutCounter(AtomicLong cutCounter) {
        this.cutCounter = cutCounter;
    }

    public AtomicLong getReadSourceCounter() {
        return readSourceCounter;
    }

    public void setReadSourceCounter(AtomicLong readSourceCounter) {
        this.readSourceCounter = readSourceCounter;
    }

    public AtomicLong getMergeCompareCounter() {
        return mergeCompareCounter;
    }

    public void setMergeCompareCounter(AtomicLong mergeCompareCounter) {
        this.mergeCompareCounter = mergeCompareCounter;
    }

    public AtomicLong getMergeWriteCounter() {
        return mergeWriteCounter;
    }

    public void setMergeWriteCounter(AtomicLong mergeWriteCounter) {
        this.mergeWriteCounter = mergeWriteCounter;
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

        String costSecondFormat=CommonHelper.printDecimalRadix2(costSecond);
        String tpsFormat= CommonHelper.printDecimalRadix2(costSecond > 0 ? cutCounter.get() / costSecond : 0);

        System.out.println("cutCounter count:" + cutCounter.get() + " cost second:" + costSecondFormat + " tps:" + tpsFormat);

         tpsFormat= CommonHelper.printDecimalRadix2(costSecond > 0 ? cutCounter.get() / costSecond : 0);
        System.out.println("readSourceCounter count:" + readSourceCounter.get() + " cost second:" + costSecondFormat + " tps:" + tpsFormat);

        tpsFormat= CommonHelper.printDecimalRadix2(costSecond > 0 ? cutCounter.get() / costSecond : 0);
        System.out.println("mergeCompareCounter count:" + mergeCompareCounter.get() + " cost second:" + costSecondFormat + " tps:" + tpsFormat);

        tpsFormat= CommonHelper.printDecimalRadix2(costSecond > 0 ? cutCounter.get() / costSecond : 0);
        System.out.println("mergeWriteCounter count:" + mergeWriteCounter.get() + " cost second:" + costSecondFormat + " tps:" + tpsFormat);

        tpsFormat= CommonHelper.printDecimalRadix2(costSecond > 0 ? cutCounter.get() / costSecond : 0);
        System.out.println("antiDupMergeCounter count:" + antiDupMergeCounter.get() + " cost second:" + costSecondFormat + " tps:" + tpsFormat);

        System.out.println("currentLevel:"+currentLevel.get());

    }
}
