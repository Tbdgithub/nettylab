package com.saturn.client.rdate;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by john.y on 2017-6-23.
 */
public class TimeDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < 4) {

            System.out.println("zan bao le,读完了再说");
            //处理粘包
            return;
        }

        UnixTime unixTime = new UnixTime(in.readInt());
        out.add(unixTime);
    }
}
