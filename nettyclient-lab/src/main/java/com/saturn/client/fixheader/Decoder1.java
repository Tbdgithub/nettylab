package com.saturn.client.fixheader;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Date;
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
                System.out.println("link check request,tranId:" + headerIdentity.getTransactionID());

                keepalive(ctx);

            } else if (headerIdentity.getCommandId() == 0x80000001) {
                System.out.println("link check response,tranId:" + headerIdentity.getTransactionID());

            } else {
                System.out.println("other resp,tranId:" + headerIdentity.getTransactionID() + " " + headerIdentity.getCommandId());
            }

            int nextToRead = headerIdentity.getLength() - HeaderIdentity.HeaderLen;

            if (!in.isReadable(nextToRead)) {
                in.resetReaderIndex();
                return;
            }

            if (nextToRead == 0) {
                System.out.println("keepalive do nothing .date:" + new Date().toString());
                // out.add(new RespBody());
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

    private void keepalive(ChannelHandlerContext ctx) {
        RequestMsg requestMsg = new RequestMsg();

        requestMsg.setBodyBuff(new byte[0]);
        HeaderIdentity header = new HeaderIdentity();
        header.setLength(requestMsg.getBodyBuff().length + HeaderIdentity.HeaderLen);
        header.setCommandId(0x00000001);
        header.setTransactionID(IdGenerator.getNextTid());
        requestMsg.setHeaderIdentity(header);

        ctx.channel().writeAndFlush(requestMsg);
    }


}
