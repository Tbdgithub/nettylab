package com.saturn.client.ppclient;

import com.saturn.common.entity.HeaderIdentity;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Date;
import java.util.List;

/**
 * Created by john.y on 2017-8-15.
 */
public class Decoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int HeaderSize = Constants.HeaderLen;
        while (in.isReadable(HeaderSize)) {
            //System.out.println("decode:");
            in.markReaderIndex();

            byte[] idtBuffer = new byte[HeaderSize];
            in.readBytes(idtBuffer);
            HeaderIdentity headerIdentity = HeaderIdentity.fromBuffer(idtBuffer);

            if (headerIdentity.getLength() > HeaderIdentity.MaxBodyLen) {
                System.out.println("Body size too large");
                ctx.close();
                return;
            }


            int nextToRead = headerIdentity.getLength() - HeaderIdentity.HeaderLen;

            if (!in.isReadable(nextToRead)) {
                in.resetReaderIndex();
                return;
            }

            if (nextToRead == 0) {
                System.out.println("keepalive do nothing .date:" + new Date().toString());
                //out.add(new RespBody());
                return;
            }

            ByteBufInputStream is = new ByteBufInputStream(in, nextToRead);

            byte[] bodyBuff = new byte[nextToRead];
            is.read(bodyBuff, 0, bodyBuff.length);


            //RespBody respBody = RespBody.fromBuffer(bodyBuff);

            //respBody.setHeaderIdentity(headerIdentity);
            // System.out.println("decode resp:" + respBody.getRespCode());
            //todo connection response
            //out.add(respBody);


        }
    }
}
