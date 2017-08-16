package com.saturn.client.ppclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by john.y on 2017-8-15.
 */
public class ConnManager {

    //todo
    //1. pendings
    //

    private int maxCount = 2;
    private String remoteHost;
    private int remotePort;

    ConcurrentHashMap<String, PPConnection> connMap = new ConcurrentHashMap<>();

    PPConnection[] connectionList = null;

    int balence = 0;

    public void init(int maxCount, String remoteHost, int remotePort) throws Exception {
        this.maxCount = maxCount;
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;

        connectionList = new PPConnection[this.maxCount];


        for (int i = 0; i < maxCount; i++) {
            initConnection(i);
        }

    }

    public void start() {

        System.out.println("started");
    }

    public PPConnection getConn() throws Exception {

        PPConnection result = null;
        synchronized (this) {
            balence++;
            if (balence >= maxCount) {
                balence = 0;
            }

            result = connectionList[balence];
            if (!result.isActive()) {
                initConnection(balence);
                //wait until succ

            }
        }

        return result;
    }

    private void initConnection(final int index) throws Exception {
        // Configure the client.


        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();

                            //inbound
                            p.addLast("Decoder", new Decoder());
                            p.addLast("InboundBizHandler",new InboundBizHandler());

                            //outbound
                            p.addLast("Encoder", new Encoder());

                        }
                    });

            ChannelFuture cf = b.connect(this.remoteHost, this.remotePort);

            cf.addListener(new GenericFutureListener<ChannelFuture>() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {

                    if (future.isSuccess()) {

                        System.out.println("connected succ");
                        PPConnection connection = new PPConnection(future.channel());
                        put(connection, index);


                    } else {

                    }
                }
            });


            // cf.channel().closeFuture().sync();

        } finally {
            // Shut down the event loop to terminate all threads.
            // group.shutdownGracefully();
        }

    }

    private void checkUsable() {

    }

    private void put(PPConnection conn, int index) {

        this.connMap.put(conn.getKey(), conn);
        this.connectionList[index] = conn;
    }

    private void remove(PPConnection conn) {

    }

    public void writeMsg(Object msg) throws Exception {

        PPConnection connection = getConn();
        connection.write(msg);
    }


}
