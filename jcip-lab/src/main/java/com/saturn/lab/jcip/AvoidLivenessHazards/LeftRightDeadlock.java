package com.saturn.lab.jcip.AvoidLivenessHazards;

public class LeftRightDeadlock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() {
        synchronized (left) {
//            try {
//                Thread.sleep(0);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            synchronized (right) {
                doSomething();
            }
        }
    }

    public void rightLeft() {
        synchronized (right) {

//            try {
//                Thread.sleep(0);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            synchronized (left) {
                doSomethingElse();
            }
        }
    }

    void doSomething() {
        System.out.println("doSomething");
    }

    void doSomethingElse() {

        System.out.println("doSomethingElse");
    }
}