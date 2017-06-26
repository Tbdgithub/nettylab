package com.saturn.server.rdate;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by john.y on 2017-6-23.
 */
public class TimeEncoder extends MessageToByteEncoder<UnixTime> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) throws Exception {
//
//        if (in.readableBytes() < 4) {
//            return; // (3)
//        }

         out.writeInt((int) msg.getValue());

//        if (in.readableBytes() < 4) {
//            return; // (3)
//        }
//
//        out.add(in.readBytes(4)); // (4)

    }
}
