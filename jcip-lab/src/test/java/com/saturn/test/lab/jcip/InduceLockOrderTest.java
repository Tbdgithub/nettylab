package com.saturn.test.lab.jcip;

import com.saturn.lab.jcip.AvoidLivenessHazards.DynamicOrderDeadlock;
import com.saturn.lab.jcip.AvoidLivenessHazards.InduceLockOrder;
import com.saturn.lab.jcip.AvoidLivenessHazards.LeftRightDeadlock;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

public class InduceLockOrderTest {

    @Test
    public void test1() {
        InduceLockOrder.Account account1 = new InduceLockOrder.Account() {
            @Override
            public void debit(InduceLockOrder.DollarAmount d) {

            }

            @Override
            public void credit(InduceLockOrder.DollarAmount d) {

            }

            @Override
            public InduceLockOrder.DollarAmount getBalance() {
                return new InduceLockOrder.DollarAmount() {
                    @Override
                    public int compareTo(InduceLockOrder.DollarAmount o) {
                        return 0;
                    }
                };
            }

            @Override
            public int getAcctNo() {
                return 0;
            }
        };

        //account1.setBalance(new DynamicOrderDeadlock.DollarAmount(1000000));

        InduceLockOrder.Account account2 = new InduceLockOrder.Account() {
            @Override
            public void debit(InduceLockOrder.DollarAmount d) {
                System.out.println("debit");
            }

            @Override
            public void credit(InduceLockOrder.DollarAmount d) {
                System.out.println("credit");
            }

            @Override
            public InduceLockOrder.DollarAmount getBalance() {
                return  new InduceLockOrder.DollarAmount() {
                    @Override
                    public int compareTo(InduceLockOrder.DollarAmount o) {
                        return 0;
                    }
                };
            }

            @Override
            public int getAcctNo() {
                return 0;
            }
        };

      //  account2.setBalance(new DynamicOrderDeadlock.DollarAmount(1000000));

        Random random = new Random();




        InduceLockOrder.DollarAmount amount = new InduceLockOrder.DollarAmount() {
            @Override
            public int compareTo(InduceLockOrder.DollarAmount o) {
                return 0;
            }
        };

        InduceLockOrder lockOrder=new InduceLockOrder();

        for (int i = 0; i < 2; i++) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 1000; i++) {

                            int index = random.nextInt(2);
                            InduceLockOrder.Account a;
                            InduceLockOrder.Account b;
                            if (index == 0) {
                                a = account1;
                                b = account2;
                            } else {
                                a = account2;
                                b = account1;
                            }

                            lockOrder.transferMoney(a, b, amount);
                            Thread.sleep(10);
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
