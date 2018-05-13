package com.saturn.lab.jcip.BuildingBlocks;

import java.util.concurrent.BlockingQueue;


public class TaskRunnable implements Runnable {
    BlockingQueue<Task> queue;

    public void run() {
        try {
            processTask(queue.take());
        } catch (InterruptedException e) {
            // restore interrupted status
            //不吃这个异常
            Thread.currentThread().interrupt();

            //Thread.currentThread().resume();
        }
    }

    void processTask(Task task) {
        // Handle the task
    }

    interface Task {
    }
}