package com.saturn.lab.jcip.BuildingBlocks;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Preloader {


    public static void main(String[] args) {
        try {
            Preloader worker = new Preloader();
            worker.start();

            ProductInfo productInfo = worker.get();

            System.out.println("productInfo:" + productInfo);

            Thread.sleep(Integer.MAX_VALUE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ProductInfo loadProductInfo() throws DataLoadException {

        System.out.println("le me loadProductInfo");

        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {

            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        System.out.println("finished loadProductInfo");

        return null;
    }

    private final FutureTask<ProductInfo> future =

            new FutureTask<ProductInfo>(new Callable<ProductInfo>() {

                public ProductInfo call() throws DataLoadException {
                    return loadProductInfo();
                }
            });

    private final Thread thread = new Thread(future);

    public void start() {
        thread.start();
    }

    public ProductInfo get()
            throws DataLoadException, InterruptedException {
        try {
            /**
             * 异步转同步，拿到结果
             */
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException)
                throw (DataLoadException) cause;
            else
                throw LaunderThrowable.launderThrowable(cause);
        }
    }

    interface ProductInfo {
    }
}

class DataLoadException extends Exception {
}