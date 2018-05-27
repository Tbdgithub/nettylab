package com.saturn.lab.jcip.CancellationAndShutdown;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NoncancelableTask {


    public Task getNextTask(BlockingQueue<Task> queue) {
        boolean interrupted = false;
        try {
            while (true) {
                try {
                    return queue.take();
                } catch (InterruptedException e) {
                    interrupted = true;
                    // fall through and retry
                    e.printStackTrace();
                    break;
                }
            }
        } finally {
            if (interrupted) {

                Thread.currentThread().interrupt();
            }
        }

        System.out.println("finished");
        return null;
    }

    interface Task {
    }

    public static void main(String[] args)
    {
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    BlockingQueue<Task> blockingQueue=new ArrayBlockingQueue<Task>(1);

                   // blockingQueue.take();

                    NoncancelableTask task=new NoncancelableTask();
                    task.getNextTask(blockingQueue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();

        try {
            Thread.sleep(5000);

            t1.interrupt();

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}