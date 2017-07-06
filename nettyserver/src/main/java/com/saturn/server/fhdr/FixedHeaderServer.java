package com.saturn.server.fhdr;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by john.y on 2017-6-29.
 */
public class FixedHeaderServer {


    public static void main(String[] args) throws Exception {

        int PORT = 55062;
        int idleSecond=10;

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


                            // p.addLast("outbound 0", new TimeEncoder());
                            p.addLast("decoder", new SvDecoder());
                            p.addLast("SvHandler",new SvHandler());
                            p.addLast("IdleStateHandler",new IdleStateHandler(idleSecond,0,0));
                            p.addLast("idlecheck",new IdleCheckHandler());
                            p.addLast("SvEncoder",new SvEncoder());

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
