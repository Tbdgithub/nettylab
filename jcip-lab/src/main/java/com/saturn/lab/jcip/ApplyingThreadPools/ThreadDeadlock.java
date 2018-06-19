package com.saturn.lab.jcip.ApplyingThreadPools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDeadlock {

    ExecutorService exec = Executors.newSingleThreadExecutor();

    //ExecutorService exec = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {

        ThreadDeadlock example = new ThreadDeadlock();
        example.start();


    }

    public void start() {
        try {
            RenderPageTask task = new RenderPageTask();
            Future<String> page = exec.submit(task);
            //  String resp = task.call();
            System.out.println("resp:" + page.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class LoadFileTask implements Callable<String> {
        private final String fileName;

        public LoadFileTask(String fileName) {
            this.fileName = fileName;
        }

        public String call() throws Exception {
            // Here's where we would actually read the file
            return "load file call.file:" + fileName;
        }
    }

    public class RenderPageTask implements Callable<String> {
        public String call() throws Exception {
            Future<String> header, footer;

            System.out.println("begin submit header");
            header = exec.submit(new LoadFileTask("header.html"));
            System.out.println("begin submit footer");
            footer = exec.submit(new LoadFileTask("footer.html"));
            String page = renderBody();

            System.out.println("wait for page");
            // Will deadlock -- task waiting for result of subtask
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            // Here's where we would actually render the page
            return "here is body";
        }
    }
}