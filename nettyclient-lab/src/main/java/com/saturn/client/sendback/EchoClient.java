package com.saturn.client.sendback;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lyz on 2017/6/18.
 */
public class EchoClient {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host", "thinkpad");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.git
        final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

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
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                            }


                            //inbound
                            p.addLast("decoder", new EchoMessageDecoder());
                            p.addLast("inbound", new InboundHandler1());

                            //outbound
                            p.addLast("encoder", new EchoMessageEncoder());
                            p.addLast("out1", new Msg2MsgHandler());
                            //p.addLast("out2", new Msg2MsgHandler());


                        }
                    });


            // int readInt = System.in.read();

            ChannelFuture cf = b.connect(HOST, PORT);


            Channel channel = cf.channel();


            readInput(channel);
            // Start the client.
            //   ChannelFuture f = b.connect(HOST, PORT).sync();

            // Wait until the connection is closed.
            // f.channel().closeFuture().sync();

        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

    private static final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) {
            if (future.isSuccess()) {
                System.out.println("future succeed");
            } else {
                future.cause().printStackTrace();
                future.channel().close();
            }
        }
    };

    private static void readInput(Channel channel) {

        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new InputStreamReader(System.in));

            String line = bf.readLine();

            while (!line.equals("quit")) {
                // System.out.println(line);
                //ChannelFuture f = b.connect(HOST, PORT).sync();
                ChannelFuture channelFuture = channel.writeAndFlush(line);

                // channelFuture.addListener(trafficGenerator);

                line = bf.readLine();
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
