package com.saturn.lab.jcip.CancellationAndShutdown;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;


import java.io.IOException;
import java.util.Date;
import java.net.Socket;
import java.util.concurrent.*;


class SocketUsingTaskImp extends SocketUsingTask<String> {


    @Override
    public String call() throws Exception {

        System.out.println("begin called");
        Thread.sleep(1000);


        //throw  new RuntimeException("run error");
        return "called";
    }
}


/**
 * todo callable
 *
 * @param <T>
 */
public abstract class SocketUsingTask<T> implements CancellableTask<T> {

    @GuardedBy("this")
    private Socket socket;


    public static void main(String[] args) throws IOException

    {
        //  CancellingExecutor cancellingExecutor=new CancellableTask<>()
        // ServerSocket serverSocket=new ServerSocket(80);
        //Socket socket= serverSocket.accept();

        System.out.println(new Date()+"begin test ");
        CancellingExecutor cancellingExecutor =
                new CancellingExecutor(3, 3, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));

        SocketUsingTaskImp task = new SocketUsingTaskImp();


        Future<String> executorFuture = cancellingExecutor.submit(task);

        RunnableFuture<String> myFuture=(RunnableFuture<String>)executorFuture;


        try {

            String result = myFuture.get(5000, TimeUnit.MILLISECONDS);
            System.out.println (new Date()+ " myFuture get succeed:" + result);
            //Thread.sleep(2000);
           // task.cancel();

            System.out.println (new Date()+"begin cancel");
            myFuture.cancel(true);

            Thread.sleep(Integer.MAX_VALUE);
        }

        catch (InterruptedException e) {
            System.out.println("Interrupted ");

            e.printStackTrace();
        }

        catch (ExecutionException e) {

            System.out.println("Execution error ");
            e.printStackTrace();
        } catch (TimeoutException e) {

            System.out.println("Timeout ");
            e.printStackTrace();
        }

    }


    protected synchronized void setSocket(Socket s) {
        socket = s;
    }

    public synchronized void cancel() {
        try {

            System.out.println(this.getClass().getSimpleName()+" canceled");
            if (socket != null) {
                socket.close();
                System.out.println("socket close");
            }
            else
            {
                System.out.println(new Date() +" socket is null ,no need close");
            }


        } catch (IOException ignored) {
        }
    }

    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this) {

            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    System.out.println("newTask->cancel");
                    SocketUsingTask.this.cancel();
                } finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }

//            public T get(long timeout, TimeUnit unit)
//                    throws InterruptedException, ExecutionException, TimeoutException {
//
//                return this.get(timeout,unit);
//            }
//
//
//            public T get() throws InterruptedException, ExecutionException {
//              return this.get();
//            }

        };
    }
}


interface CancellableTask<T> extends Callable<T> {
    void cancel();

    RunnableFuture<T> newTask();
}


@ThreadSafe
class CancellingExecutor extends ThreadPoolExecutor {
    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof CancellableTask) {
            return ((CancellableTask<T>) callable).newTask();
        }
        else
            return super.newTaskFor(callable);
    }
}