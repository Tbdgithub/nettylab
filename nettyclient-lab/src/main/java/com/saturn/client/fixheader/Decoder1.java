package com.saturn.client.fixheader;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by john.y on 2017-6-26.
 */
public class Decoder1 extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        int HeaderSize = 12;
        while (in.isReadable(HeaderSize)) {

            System.out.println("decode:");
            in.markReaderIndex();

            byte[] idtBuffer = new byte[HeaderSize];
            in.readBytes(idtBuffer);
            HeaderIdentity headerIdentity = HeaderIdentity.fromBuffer(idtBuffer);

            if (headerIdentity.getLength() > HeaderIdentity.MaxBodyLen) {
                System.out.println("Body size too large");
                ctx.close();
                return;
            }

            if (headerIdentity.getCommandId() == 0x00000001) {
                System.out.println("link check req,tranId:" + headerIdentity.getTransactionID());
                //  return;
            } else
            //if (headerIdentity.getCommandId() == 0x00000006) {
            {
                System.out.println("other resp,tranId:" + headerIdentity.getTransactionID() + " " + headerIdentity.getCommandId());
            }

            int nextToRead = headerIdentity.getLength() - HeaderIdentity.HeaderLen;

            if (!in.isReadable(nextToRead)) {
                in.resetReaderIndex();
                return;
            }

            if (nextToRead == 0) {
                out.add(new RespBody());
                return;
            }

            ByteBufInputStream is = new ByteBufInputStream(in, nextToRead);

            byte[] bodyBuff = new byte[nextToRead];
            is.read(bodyBuff, 0, bodyBuff.length);

            RespBody respBody = RespBody.fromBuffer(bodyBuff);

            System.out.println("resp:" + respBody.getRespCode());
            //todo connection response
            out.add(respBody);

        }
    }


}
