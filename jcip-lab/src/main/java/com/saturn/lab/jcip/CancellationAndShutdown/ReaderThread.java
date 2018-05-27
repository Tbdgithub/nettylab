package com.saturn.lab.jcip.CancellationAndShutdown;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReaderThread extends Thread {


    private static final int BUFSZ = 512;
    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    public void interrupt() {
        try {
            socket.close();

            System.out.println("socket closed");
        } catch (IOException ignored) {
        } finally {
            super.interrupt();
        }
    }

    public void run() {
        try {
            byte[] buf = new byte[BUFSZ];
            while (true) {
                int count = in.read(buf);
                if (count < 0)
                    break;
                else if (count > 0)
                    processBuffer(buf, count);
            }
        } catch (IOException e) { /* Allow thread to exit */
        }
    }

    public void processBuffer(byte[] buf, int count) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < count; i++) {
            sb.append((char) buf[i]);
        }

        System.out.print(sb.toString());
    }


    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(80);

        Socket socket = serverSocket.accept();

        ReaderThread thread = new ReaderThread(socket);

        thread.start();

        System.out.println("started thread");
        Thread.sleep(10000);
        System.out.println("begin interrupt");
        thread.interrupt();

    }

}