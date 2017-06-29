package com.saturn.client.fhdr;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by john.y on 2017-6-29.
 */
public class FixedHeaderClient {

    private String host;
    private int port;
    private Bootstrap bootstrap;
    private Channel clientChannel;

    private int Timeout=5000;


    public FixedHeaderClient(String host, int port) throws Exception {
        this.host = host;
        this.port = port;

        EventLoopGroup group = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();

                        //inbound
                        p.addLast("decoder", new Decoder1());

                        //outbound
                        p.addLast("encoder1", new MsgEncoder());
                        // p.addLast("encoder", new Encoder1());
                        //   p.addLast("KeepaliveEncoder",new KeepaliveEncoder());
                    }
                });

    }


    public void start() throws Exception {
        // Configure the client.

        ChannelFuture cf = bootstrap.connect(host, port);
        cf.addListener(trafficGenerator);
        clientChannel = cf.channel();

        //readInput(channel);
        // cf.channel().closeFuture().sync();

    }

    public RespBody sendMessage(RequestMsg requestMsg) {

        return null;
    }


//
//    finally
//
//    {
//        // Shut down the event loop to terminate all threads.
//        group.shutdownGracefully();
//    }


    private final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) {
            if (future.isSuccess()) {
                System.out.println("operationComplete future succeed");
            } else {
                future.cause().printStackTrace();
                future.channel().close();
            }
        }
    };

}
