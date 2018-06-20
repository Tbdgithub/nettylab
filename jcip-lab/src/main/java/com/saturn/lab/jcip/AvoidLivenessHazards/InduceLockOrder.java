package com.saturn.lab.jcip.AvoidLivenessHazards;

import java.util.concurrent.atomic.LongAdder;

public class InduceLockOrder {
    //private static final Object tieLock = new Object();
    private static final Object tieLock=new Object();


    LongAdder adder = new LongAdder();

    public void transferMoney(final Account fromAcct,
                              final Account toAcct,
                              final DollarAmount amount)
            throws InsufficientFundsException {
        class Helper {
            public void transfer() throws InsufficientFundsException {
                if (fromAcct.getBalance().compareTo(amount) < 0)
                    throw new InsufficientFundsException();
                else {
                    fromAcct.debit(amount);
                    toAcct.credit(amount);
                }

                adder.increment();
                System.out.println("transaction count:" + adder.longValue());
            }
        }
        //int fromHash = System.identityHashCode(fromAcct);
        int fromHash=System.identityHashCode(fromAcct);
        //int toHash = System.identityHashCode(toAcct);
        int toHash=System.identityHashCode(toAcct);

        //key:lock 始终有顺序
//        if (fromHash < toHash) {
//            synchronized (fromAcct) {
//                synchronized (toAcct) {
//                    new Helper().transfer();
//                }
//            }
//        }
//
        if(fromHash<toHash)
        {
            synchronized (fromAcct)
            {
                synchronized (toAcct)
                {
                    new Helper().transfer();
                }
            }
        }
        else if (fromHash > toHash) {
            synchronized (toAcct) {
                synchronized (fromAcct) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (fromAcct) {
                    synchronized (toAcct) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }

    public interface DollarAmount extends Comparable<DollarAmount> {
    }

    public interface Account {
        void debit(DollarAmount d);

        void credit(DollarAmount d);

        DollarAmount getBalance();

        int getAcctNo();
    }

    public class InsufficientFundsException extends Exception {
    }
}
