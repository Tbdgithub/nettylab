package com.saturn.lab.jcip.TaskExecution;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                public void run() {
                    handleRequest(connection);
                }
            };
            new Thread(task).start();
        }
    }

    private static void handleRequest(Socket connection) {
        // request-handling logic here
        System.out.println("handleRequest");

        try {
            byte[] buff = new byte[1024];

            InputStream is = connection.getInputStream();

            StringBuilder sb = new StringBuilder();

            int readCount = is.read(buff);
            while (readCount > 0) {


                byte[] readBuff = new byte[readCount];
                System.arraycopy(buff, 0, readBuff, 0, readCount);
                String line = new String(readBuff);
                System.out.print(line);

                if ("q".equals(line)) {
                    break;
                }

                sb.append(line);
                readCount = is.read(buff, 0, buff.length);
            }


            System.out.println("read all:" + sb.toString());
            connection.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}