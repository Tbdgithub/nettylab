package com.saturn.server.rdate;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by john.y on 2017-6-23.
 */
public class TimeServer {


    static final int PORT = Integer.parseInt(System.getProperty("port", "37"));

    public static void main(String[] args) throws Exception {

        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();


                            p.addLast("inbound 0", new TimeServerHandler());

                        }
                    });

            System.out.println("begin bind");
            // Start the server.
            ChannelFuture f = b.bind(PORT).sync();

            System.out.println("bind ok");
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
            System.out.println("s:close");
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
