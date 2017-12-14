package com.saturn.sort.merge;

import java.io.BufferedReader;
import java.io.IOException;

public class CustBufferedReader {

    private BufferedReader innerReader;
    ProgressWatcher watcher;

    public CustBufferedReader(BufferedReader br,ProgressWatcher watcher)
    {
        this.innerReader=br;
        this.watcher=watcher;
    }

    public CustBufferedReader(BufferedReader br)
    {
        this.innerReader=br;
    }

    public String readLine() throws IOException {
        if(this.watcher!=null)
        {
            watcher.getReadSourceCounter().incrementAndGet();
        }

        return this.innerReader.readLine();
    }

    public void close() throws IOException
    {
        this.innerReader.close();
    }
}
