package com.saturn.lab.jcip.TaskExecution;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


    public class SingleThreadWebServer {

        public static void main(String[] args) throws IOException {
            ServerSocket socket = new ServerSocket(80);
            while (true) {
                Socket connection = socket.accept();
                handleRequest(connection);
            }
        }

        private static void handleRequest(Socket connection)  throws IOException{
            // request-handling logic here
            System.out.println("handled request");
            connection.close();
        }
    }

