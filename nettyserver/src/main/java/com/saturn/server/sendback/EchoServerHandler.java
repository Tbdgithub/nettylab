package com.saturn.server.sendback;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by yibo on 2017-6-19.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        System.out.println("read channel");
        ByteBuf in = (ByteBuf) msg;
//        try {
//            while (in.isReadable()) { // (1)
//                System.out.print((char) in.readByte());
//                System.out.flush();
//            }
//
//            //String msgText=in.toString(io.netty.util.CharsetUtil.US_ASCII);
//            //System.out.print(msgText);
//        } finally {
//           // ReferenceCountUtil.release(msg); // (2)
//        }

        ctx.write(msg);
        String msgText = in.toString(io.netty.util.CharsetUtil.US_ASCII);
        System.out.println("send back :" + msgText);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
