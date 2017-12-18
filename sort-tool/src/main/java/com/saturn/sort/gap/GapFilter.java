package com.saturn.sort.gap;

import com.saturn.sort.merge.CommonHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;



/**
 * find out gap
 * for example :
 * input: 1 2 5 6 8 9
 * output: 3 4 7
 */
public class GapFilter {

    private File inputFile;
    private File outputFile;
    private int inputBatch;

    static int bucketCount = 1000;
    static int progressIntervalMs = 3000;

    static volatile boolean outputFinished = false;


    //Stopwatch stopwatch = null;
    private ProgressWatcher watcher;
    //static AtomicLong inputCounter = new AtomicLong(0);
    static AtomicLong filterCounter = new AtomicLong(0);
    //static AtomicLong outputCounter = new AtomicLong(0);

    long lastMinId;

    public GapFilter(File inputFile, File outputFile, int inputBatch) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.inputBatch = inputBatch;
    }

    public GapFilter(File inputFile, File outputFile, int inputBatch,ProgressWatcher watcher) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.inputBatch = inputBatch;
        this.watcher=watcher;
    }


    public static void main(String [] args)
    {
        try {
            if (args.length < 2) {
                System.out.println("usage: GapFilter -i ./input.txt -o ./output.txt -b 10000");
                return;
            }

            File input = new File(args[1]);
            File output = new File(args[3]);
            int batch = Integer.parseInt(args[5]);
            ProgressWatcher watcher=new ProgressWatcher();
            GapFilter filter = new GapFilter(input, output, batch,watcher);
            filter.start();
            System.out.println("finished");
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }

    public void start() {
        System.out.println("begin");
        watcher.start();
       // ProgressWatcher watcher = new ProgressWatcher();

//        Thread tMoniter = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                showProgress();
//            }
//        });
//
//        tMoniter.start();

        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {

            fileWriter = new FileWriter(outputFile);
            bufferedWriter = new BufferedWriter(fileWriter);

            fr = new FileReader(inputFile);
            br = new BufferedReader(fr);
            String line = null;

            lastMinId = Long.MIN_VALUE;
            List<Long> bucket = new ArrayList<>(bucketCount);

            do {
                line = br.readLine();

                if (!CommonHelper.isNullOrEmpty(line)) {

                    if(watcher!=null) {
                        watcher.getInputCounter().incrementAndGet();
                    }

                    //inputCounter.incrementAndGet();
                    if (lastMinId == Long.MIN_VALUE) {
                        lastMinId = Long.parseLong(line);
                    }

                    bucket.add(Long.parseLong(line));
                }

                if (bucket.size() >= bucketCount) {
                    drain(bufferedWriter, bucket);
                }

            }
            while (!CommonHelper.isNullOrEmpty(line));

            drain(bufferedWriter, bucket);

            outputFinished = true;


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            close(fr, br);
            close(fileWriter, bufferedWriter);
            close(watcher);
        }
    }

    private void close(ProgressWatcher watcher)
    {
        if(watcher!=null)
        {
            watcher.close();
        }
    }




    private void drain(BufferedWriter bufferedWriter, List<Long> bucket) {

        if (bucket.size() == 0) {
            return;
        }

        outputRange(bufferedWriter, lastMinId + 1, bucket.get(0));


        List<Long> processed = filter(bucket);
        output(bufferedWriter, processed);

        lastMinId = bucket.get(bucket.size() - 1);

        bucket.clear();
    }


    private void outputRange(BufferedWriter bufferedWriter, long startInclude, long endExclude) {
        try {

            for (Long i = startInclude; i < endExclude; i++) {
                bufferedWriter.write(String.valueOf(i) + "\n");
                //outputCounter.incrementAndGet();
                if(watcher!=null)
                {
                    watcher.getOutputCounter().incrementAndGet();
                }

                //filterCounter.incrementAndGet();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void output(BufferedWriter bufferedWriter, List<Long> processed) {
        try {

            for (Long val : processed) {
                bufferedWriter.write(String.valueOf(val) + "\n");
                //outputCounter.incrementAndGet();
                if(watcher!=null)
                {
                    watcher.getOutputCounter().incrementAndGet();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Long> filter(List<Long> data) {

        List<Long> result = new ArrayList<>();
        Long prevId = data.get(0) - 1;

        long currId = data.get(0);
        for (int i = 0; i < data.size(); i++) {
            currId = data.get(i);

            if (currId - prevId > 1) {
                for (long j = prevId + 1; j < currId; j++) {
                    filterCounter.incrementAndGet();
                    result.add(j);
                }
            }

            prevId = currId;
        }

        return result;
    }

    private void close(FileReader fr, BufferedReader br) {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (fr != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void close(FileWriter fileWriter, BufferedWriter bufferedWriter) {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (fileWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
