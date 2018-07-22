package com.saturn.common.queue;

import com.saturn.common.util.Action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LazyQueue<T> {

    private String name;

    private int capacity;

    private Queue<T> queue;

    private ThreadProc thread;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }


    public LazyQueue(String name, int lazyMs, int batchCount, Action<List<T>> dequeueAction) {
        this.name = name;
        this.capacity = 65536;
        this.queue = new ConcurrentLinkedQueue<T>();

        this.thread = new ThreadProc(queue, batchCount, lazyMs, dequeueAction);
        this.thread.start();
    }


    public void enQueue(T a) {
        if (queue.size() > capacity) {
            return;
        }
        queue.add(a);
    }

    public void deQueue (T a) {
        if (queue.contains(a)) {
            queue.remove(a);
        }
    }


    public void flush() {
        queue.clear();
    }


    public int size() {
        return this.queue.size();
    }


    public Iterator<T> iterator() {
        return this.queue.iterator();
    }


    public boolean setRunStatus(boolean runStatus) {
        return this.thread.setRunFlag(runStatus);
    }
}


class ThreadProc<T> extends Thread {

    private Queue<T> queue;

    private int batchCount;

    private int lazyMs;

    private long lastMs;

    private Action<List<T>> dequeueAction;

    private boolean runFlag;


    public ThreadProc(Queue<T> queue, int batchCount, int lazyMs, Action<List<T>> dequeueAction) {
        this.queue = queue;
        this.batchCount = batchCount;
        this.lazyMs = lazyMs;
        this.dequeueAction = dequeueAction;
        this.lastMs = System.currentTimeMillis();
        this.runFlag = true;
    }


    public void run() {
        while (runFlag) {
            long iniMs = System.currentTimeMillis();
            int dequeueCount = 0;

            if (queue.size() >= batchCount) {
                dequeueCount = batchCount;
            } else {
                long diffMs = iniMs - lastMs;

                if ((diffMs > lazyMs || diffMs < -lastMs) && queue.size() > 0) {
                    dequeueCount = queue.size();
                }
            }
            if (dequeueCount > 0) {

                List<T> items = dequeueItems(dequeueCount);

                dequeueAction(items);

                lastMs = System.currentTimeMillis();
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    flushCache();
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private List<T> dequeueItems(int count) {

        if (count > queue.size()) {
            count = queue.size();
        }
        List<T> items = new ArrayList<T>(count);
        for (int i = 0; i < count; i++) {
            T item = queue.poll();
            items.add(item);
        }
        return items;
    }

    private void dequeueAction(List<T> items) {

        if (items.size() == 0)
            return;
        try {
            dequeueAction.run(items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void flushCache() {
        while (queue.size() > 0) {
            int queueCount = queue.size();
            int dequeueCount = queueCount > batchCount ? batchCount : queueCount;

            List<T> items = dequeueItems(dequeueCount);

            dequeueAction(items);
        }
    }


    public boolean setRunFlag(boolean runFlag) {
        this.runFlag = runFlag;
        return this.runFlag;
    }
}
