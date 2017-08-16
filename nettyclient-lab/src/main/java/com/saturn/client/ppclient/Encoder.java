package com.saturn.client.ppclient;

import com.saturn.infrastructure.util.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by john.y on 2017-8-15.
 */
public class Encoder extends MessageToByteEncoder<ReqMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ReqMsg msg, ByteBuf out) throws Exception {

        byte[] networkBytes = msg.getBytes();
        // System.out.print("networkBytes:" + msg);
        String hexStr = ByteUtils.toHexString(networkBytes, " ");
        System.out.println("networkBytes:" + hexStr);
        ByteBufOutputStream os = new ByteBufOutputStream(out);
        os.write(networkBytes);
        os.flush();
    }
}
