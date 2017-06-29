package com.saturn.client.fh;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by john.y on 2017-6-26.
 */
public class KeepaliveEncoder extends MessageToByteEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {

        ByteBufOutputStream os = new ByteBufOutputStream(out);

        //  byte[] msgBuff = msg.getBytes("utf-8");

        byte[] headerLenBuff = new byte[4];
        int len = HeaderIdentity.HeaderLen + 0;
        ByteUtils.fillByteBufferWithInt32(len, headerLenBuff, 0, true);

        byte[] commandIdBuff = new byte[4];
        ByteUtils.fillByteBufferWithInt32(0x00000001, commandIdBuff, 0, true);

        byte[] tranIdBuff = new byte[4];
        int tranId = IdGenerator.getNextTid();
        ByteUtils.fillByteBufferWithInt32(tranId, tranIdBuff, 0, true);

        System.out.println();
        os.write(headerLenBuff);
        os.write(commandIdBuff);
        os.write(tranIdBuff);
        //  os.write(msgBuff);
        os.flush();
    }
}