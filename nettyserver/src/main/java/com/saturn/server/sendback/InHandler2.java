package com.saturn.server.sendback;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by yibo on 2017-6-22.
 */
public class InHandler2 extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // System.out.println("s:InHandler2");

        //  String backMsg = "s:" + msg;
        //   System.out.println("send back:" + backMsg);
        String backMsg = msg;
        Channel channel = ctx.channel();
        channel.writeAndFlush(backMsg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("s: active inbound 2");
        super.channelActive(ctx);

        //Channel channel = ctx.channel();


    }
}
