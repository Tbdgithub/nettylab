package com.saturn.server.transfer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by john.y on 2017-8-14.
 */
public final class HexDumpProxy {

    int LOCAL_PORT = Integer.parseInt(System.getProperty("localPort", "8443"));
    String REMOTE_HOST = System.getProperty("remoteHost", "192.168.143.27");
    int REMOTE_PORT = Integer.parseInt(System.getProperty("remotePort", "8082"));

    public HexDumpProxy(int LOCAL_PORT, String REMOTE_HOST, int REMOTE_PORT) {
        this.LOCAL_PORT = LOCAL_PORT;
        this.REMOTE_HOST = REMOTE_HOST;
        this.REMOTE_PORT = REMOTE_PORT;
    }

    public void Start() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HexDumpProxyInitializer(REMOTE_HOST, REMOTE_PORT))
                    .childOption(ChannelOption.AUTO_READ, false)
                    .bind(LOCAL_PORT).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {

        if (args.length < 3) {
            System.err.println("Format is :HexDumpProxy LOCAL_PORT REMOTE_HOST REMOTE_PORT");
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