package com.saturn.test.lab;

import com.saturn.lab.jcip.AvoidLivenessHazards.DynamicOrderDeadlock;
import org.junit.Test;

import java.nio.channels.AcceptPendingException;
import java.util.Random;

public class DynamicOrderDeadlockTest {

    @Test
    public void test1() {
        DynamicOrderDeadlock.Account account1 = new DynamicOrderDeadlock.Account();
        account1.setBalance(new DynamicOrderDeadlock.DollarAmount(1000000));

        DynamicOrderDeadlock.Account account2 = new DynamicOrderDeadlock.Account();
        account2.setBalance(new DynamicOrderDeadlock.DollarAmount(1000000));

        Random random = new Random();




        DynamicOrderDeadlock.DollarAmount amount = new DynamicOrderDeadlock.DollarAmount(1);

        for (int i = 0; i < 2; i++) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 1000; i++) {

                            int index = random.nextInt(2);
                            DynamicOrderDeadlock.Account a;
                            DynamicOrderDeadlock.Account b;
                            if (index == 0) {
                                a = account1;
                                b = account2;
                            } else {
                                a = account2;
                                b = account1;
                            }

                            DynamicOrderDeadlock.transferMoney(a, b, amount);
                            Thread.sleep(100);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            t1.start();
        }


        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
