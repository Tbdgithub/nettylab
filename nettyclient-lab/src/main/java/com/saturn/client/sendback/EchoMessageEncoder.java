package com.saturn.client.sendback;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by yibo on 2017-6-22.
 */
public class EchoMessageEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        //out.resetWriterIndex();
       // System.out.println("client:outbound 0");
        try {
            ByteBufOutputStream os = new ByteBufOutputStream(out);

            byte[] msgBuff = msg.getBytes("utf-8");
            os.write(msgBuff);
            os.flush();

        } catch (Exception ex) {
            //  out.resetWriterIndex();

            throw ex;
        }
    }
}
