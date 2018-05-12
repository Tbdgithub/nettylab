package com.saturn.lab.jcip.SharingObjects;

/**
 * window ,windows virtual box (redhat linux)环境 下
 * 加不加volatile 没测出区别
 * mac 测一下？？
 */
public class CountingSheep {
    volatile boolean asleep;

    //boolean asleep;

    public void tryToSleep() {
        while (!asleep) {
            countSomeSheep();
            Thread.yield();
        }

        System.out.println("i am waked up");
    }

    public void notifyMe() {
        this.asleep = true;
    }

    void countSomeSheep() {
        // One, two, three...
        System.out.println("counting...");

    }


}
