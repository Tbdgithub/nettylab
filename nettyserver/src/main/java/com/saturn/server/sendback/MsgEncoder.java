package com.saturn.server.sendback;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by yibo on 2017-6-22.
 */

public class MsgEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {

        // out.resetWriterIndex();
        System.out.println("out handler 0");
        try {
            ByteBufOutputStream os = new ByteBufOutputStream(out);

            byte[] msgBuff = msg.getBytes("utf-8");
            os.write(msgBuff);
            os.flush();

            // ReferenceCountUtil.retain(out);
            //ctx.writeAndFlush(out);

            //ByteBuf outBuff = Unpooled.copiedBuffer(out);
            // ctx.writeAndFlush(outBuff);
            // ReferenceCountUtil.release(out);

        } catch (Exception ex) {
            // out.resetWriterIndex();

            throw ex;
        }
    }
}
