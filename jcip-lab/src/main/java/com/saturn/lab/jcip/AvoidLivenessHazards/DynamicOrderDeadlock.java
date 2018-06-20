package com.saturn.lab.jcip.AvoidLivenessHazards;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class DynamicOrderDeadlock {

    static LongAdder adder=new LongAdder();
    // Warning: deadlock-prone!
    public static void transferMoney(Account fromAccount,
                                     Account toAccount,
                                     DollarAmount amount)
            throws InsufficientFundsException {
        synchronized (fromAccount) {
            synchronized (toAccount) {
                if (fromAccount.getBalance().compareTo(amount) < 0)
                    throw new InsufficientFundsException();
                else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }

                adder.increment();
                System.out.println("succeed adder:"+adder.longValue());
            }
        }
    }

    public  static class DollarAmount implements Comparable<DollarAmount> {
        // Needs implementation

        private int amount;
        public DollarAmount(int amount) {
            this.amount=amount;
        }

        public DollarAmount add(DollarAmount d) {
            this.amount+=d.amount;
            return this;
        }

        public DollarAmount subtract(DollarAmount d) {
            this.amount-=d.amount;
            return this;
        }

        public int compareTo(DollarAmount dollarAmount) {
            return 0;
        }
    }

   public static class Account {
        private DollarAmount balance=new DollarAmount(0);
        private final int acctNo;
        private static final AtomicInteger sequence = new AtomicInteger();

        public Account() {
            acctNo = sequence.incrementAndGet();
        }

        void debit(DollarAmount d) {
            balance = balance.subtract(d);
        }

        void credit(DollarAmount d) {
            balance = balance.add(d);
        }

        DollarAmount getBalance() {
            return balance;
        }

       public void setBalance(DollarAmount balance) {
           this.balance = balance;
       }

       int getAcctNo() {
            return acctNo;
        }
    }

   public static class InsufficientFundsException extends Exception {
    }


}