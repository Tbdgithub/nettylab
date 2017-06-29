package com.saturn.client.fhdr;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by john.y on 2017-6-26.
 */
public class FixedHeaderClientMain {

    static final String HOST = "218.205.115.242";
    static final int PORT = Integer.parseInt("55062");


    public static void main(String[] args) throws Exception {

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
                            p.addLast("decoder", new Decoder1());

                            //outbound
                            p.addLast("encoder1", new MsgEncoder());
                            // p.addLast("encoder", new Encoder1());
                            //   p.addLast("KeepaliveEncoder",new KeepaliveEncoder());
                        }
                    });

            ChannelFuture cf = b.connect(HOST, PORT);
            cf.addListener(trafficGenerator);
            Channel channel = cf.channel();
            readInput(channel);
            cf.channel().closeFuture().sync();

        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

    private static final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
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

    private static void readInput(Channel channel) {

        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new InputStreamReader(System.in));

            String line = bf.readLine();

            while (!line.equals("quit")) {

                RequestMsg requestMsg = new RequestMsg();

                requestMsg.setBodyBuff(line.getBytes("utf-8"));

                HeaderIdentity header = new HeaderIdentity();
                header.setLength(requestMsg.getBodyBuff().length + HeaderIdentity.HeaderLen);
                header.setCommandId(0x00000006);
                header.setTransactionID(IdGenerator.getNextTid());
                requestMsg.setHeaderIdentity(header);

                ChannelFuture channelFuture = channel.writeAndFlush(requestMsg);
                channelFuture.addListener(trafficGenerator);

                line = bf.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
