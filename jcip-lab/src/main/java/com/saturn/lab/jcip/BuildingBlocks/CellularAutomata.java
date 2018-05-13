package com.saturn.lab.jcip.BuildingBlocks;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * N次集合，同时运行多个线程
 *
 */
public class CellularAutomata {
    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomata(Board board) {
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        System.out.println("processors:"+count);
        this.barrier = new CyclicBarrier(count,
                new Runnable() {
                    public void run() {
                        mainBoard.commitNewValues();
                    }});

        this.workers = new Worker[count];
        for (int i = 0; i < count; i++)
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
    }

    private class Worker implements Runnable {
        private final Board board;

        public Worker(Board board) { this.board = board; }
        public void run() {
            while (!board.hasConverged()) {
                for (int x = 0; x < board.getMaxX(); x++)
                    for (int y = 0; y < board.getMaxY(); y++)
                        board.setNewValue(x, y, computeValue(x, y));
                try {

                    System.out.println("barrier.await() begin");
                    barrier.await();
                    System.out.println("barrier.await() end");
                } catch (InterruptedException ex) {
                    return;
                } catch (BrokenBarrierException ex) {
                    return;
                }
            }
        }

        private int computeValue(int x, int y) {
            // Compute the new value that goes in (x,y)
            return (x+y)/2;
        }
    }

    public void start() {
        for (int i = 0; i < workers.length; i++)
            new Thread(workers[i]).start();
        mainBoard.waitForConvergence();
    }

    public static void main(String [] args)
    {
        CellularAutomata worker=new CellularAutomata(new BoardImp());
        worker.start();
    }

    interface Board {
        int getMaxX();
        int getMaxY();
        int getValue(int x, int y);
        int setNewValue(int x, int y, int value);
        void commitNewValues();
        boolean hasConverged();
        void waitForConvergence();
        Board getSubBoard(int numPartitions, int index);
    }

   static class BoardImp implements Board
    {
        @Override
        public int getMaxX() {
            return 0;
        }

        @Override
        public int getMaxY() {
            return 0;
        }

        @Override
        public int getValue(int x, int y) {
            return 0;
        }

        @Override
        public int setNewValue(int x, int y, int value) {
            return 0;
        }

        @Override
        public void commitNewValues() {

            System.out.println("begin commitNewValues");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println( Thread.currentThread().getId()+" end commitNewValues");
        }

        @Override
        public boolean hasConverged() {
            return false;
        }

        @Override
        public void waitForConvergence() {

        }

        @Override
        public Board getSubBoard(int numPartitions, int index) {

            Board sub=new BoardImp();
            return sub;
        }
    }
}