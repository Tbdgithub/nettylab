package com.saturn.server.fhdr;

import com.saturn.common.entity.HeaderIdentity;
import com.saturn.common.entity.RespBody;
import com.saturn.infrastructure.util.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by john.y on 2017-7-3.
 */
public class SvEncoder extends MessageToByteEncoder<RespBody> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RespBody respBody, ByteBuf out) throws Exception {


        int respCommandId = respBody.getHeaderIdentity().getCommandId();

        ByteBufOutputStream os = new ByteBufOutputStream(out);
        byte[] respBuff = new byte[4];
        ByteUtils.fillByteBufferWithInt32(respBody.getRespCode(), respBuff, 0, true);

        byte[] headerLenBuff = new byte[4];
        int len = HeaderIdentity.HeaderLen + respBuff.length;
        ByteUtils.fillByteBufferWithInt32(len, headerLenBuff, 0, true);

        byte[] commandIdBuff = new byte[4];
        ByteUtils.fillByteBufferWithInt32(respCommandId, commandIdBuff, 0, true);

        byte[] tranIdBuff = new byte[4];

        ByteUtils.fillByteBufferWithInt32(respBody.getHeaderIdentity().getTransactionID(), tranIdBuff, 0, true);

        os.write(headerLenBuff);
        os.write(commandIdBuff);
        os.write(tranIdBuff);
        os.write(respBuff);
        os.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {

        System.out.println("SvEncoder find a exception");
        // ctx.fireExceptionCaught(cause);
    }


}
