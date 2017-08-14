package com.saturn.server.transfer;

/**
 * Created by john.y on 2017-8-14.
 */
public class HexDumpProxyMain {

    public static void main(String[] args) throws Exception {

        if (args.length < 3) {
            System.err.println("Format is :java -jar HexDumpProxyMain.jar LOCAL_PORT REMOTE_HOST REMOTE_PORT");
            return;
        }

        System.err.println("Proxying *  LOCAL_PORT:" + args[0] + " to REMOTE_HOST:" + args[1] + " REMOTE_PORT:" + args[2] + " ...");

        int localPort = Integer.parseInt(args[0]);
        String remoteHost = args[1];
        int remotePort = Integer.parseInt(args[2]);

        HexDumpProxy proxy = new HexDumpProxy(localPort, remoteHost, remotePort);
        proxy.Start();
    }
}
