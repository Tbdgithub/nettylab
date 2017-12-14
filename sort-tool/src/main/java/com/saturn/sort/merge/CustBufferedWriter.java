package com.saturn.sort.merge;

import java.io.BufferedWriter;
import java.io.IOException;

public class CustBufferedWriter {

    private BufferedWriter innerWriter;
    ProgressWatcher watcher;

    public CustBufferedWriter(BufferedWriter writer,ProgressWatcher watcher)
    {
        this.innerWriter=writer;
        this.watcher=watcher;
    }

    public CustBufferedWriter(BufferedWriter writer)
    {
        this.innerWriter=writer;
    }

   public void close() throws IOException
    {
         this.innerWriter.close();
    }

    public void write(String str) throws IOException
    {
        this.innerWriter.write(str);
        if(this.watcher!=null)
        {
            this.watcher.getMergeWriteCounter().incrementAndGet();
        }
    }

}
