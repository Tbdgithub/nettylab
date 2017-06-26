package com.saturn.server.sendback;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;

import java.util.List;

/**
 * Created by yibo on 2017-6-22.
 */
public class EchoMessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

       // System.out.println("inbound 0");
        //in.markReaderIndex();
        ByteBufInputStream is = new ByteBufInputStream(in, in.readableBytes());
        String msg = is.readLine();

        //String txt = in.toString(Charset.forName("utf-8"));
        out.add(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (cause instanceof DecoderException) {


            ctx.close();
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//
//        System.out.println("s: active inbound 0");
//        //super.channelActive(ctx);
//
//        //Channel channel = ctx.channel();
//
//
////    }
}
