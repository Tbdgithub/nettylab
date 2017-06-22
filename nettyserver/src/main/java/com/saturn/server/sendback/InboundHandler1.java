package com.saturn.server.sendback;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by yibo on 2017-6-22.
 */
public class InboundHandler1 extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        //System.out.println("read :" + msg);
        System.out.println("InboundHandler1 read:"+msg);

        //下一个handler
        ctx.fireChannelRead(msg);


        //   ctx.channel().writeAndFlush("back:" + msg);
        //  System.out.println("writeback:" + msg);
        // ctx.writeAndFlush(msg);

        // ctx.channel().writeAndFlush("back:" + msg);

//        String backMsg = "s:" + msg;
//        System.out.println("send back:" + backMsg);
//        Channel channel = ctx.channel();
//        channel.writeAndFlush(backMsg);


//                .addListener((ChannelFutureListener) future -> {
//            if (!future.isSuccess()) {
//                //System.out.println("send response failed");
//            } else {
//               // System.out.println("send response succeed");
//            }
//        });
//        System.out.println("writeback:" + msg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);


    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("s: active inbound 1");
       // super.channelActive(ctx);

        //Channel channel = ctx.channel();


    }


}
