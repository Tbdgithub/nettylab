package com.saturn.client.sendback;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;

/**
 * Created by yibo on 2017-6-19.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf firstMessage;

    /**
     * Creates a client-side handler.
     */
    public EchoClientHandler() {
        // firstMessage = Unpooled.buffer(EchoClient.SIZE);
//        for (int i = 0; i < firstMessage.capacity(); i++) {
//            firstMessage.writeByte((byte) i);
//        }
        firstMessage = Unpooled.buffer(4);
        byte[] buffer = "a".getBytes(Charset.forName("utf-8"));
        firstMessage.writeBytes(buffer);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //  ctx.write(msg);

        //  String msgText=in.toString(io.netty.util.CharsetUtil.US_ASCII);
//            //System.out.print(msgText);

        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) { // (1)

                System.out.flush();
                System.out.println("echo:" + (char) in.readByte());
            }

        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
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
