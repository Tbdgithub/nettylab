package com.saturn.lab.jcip.CancellationAndShutdown;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UEHLogger implements Thread.UncaughtExceptionHandler {
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, "Thread terminated with exception: " + t.getName(), e);
    }
}