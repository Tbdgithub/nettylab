package com.saturn.client.fhdr;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by john.y on 2017-6-29.
 */
public class TransactionManager {

    public static TransactionManager Instance = new TransactionManager();

    private static final AtomicInteger AtomicTid = new AtomicInteger(0);

    public synchronized static int getNextTid() {
        int nextValue = AtomicTid.incrementAndGet();
        if (nextValue == Integer.MAX_VALUE) {
            AtomicTid.set(0);
        }

        return nextValue;
    }

    ConcurrentHashMap<String, Transaction> txs = new ConcurrentHashMap<String, Transaction>();

    private TransactionManager() {

    }

    public Transaction addTransaction(Transaction tx) {
        tx.setTid(String.valueOf(getNextTid()));
        Transaction putedTx = txs.put(tx.getTid(), tx);
        return tx;
    }

    public Transaction getTransaction(String seq) {
        return txs.get(seq);
    }

    public Transaction removeTransaction(String seq) {

        Transaction tx = txs.get(seq);
        if (tx != null) {
            txs.remove(seq);
        }
        return tx;
    }
}
