package com.saturn.client.fhdr;

import com.saturn.common.entity.HeaderIdentity;
import com.saturn.common.entity.RequestMsg;
import com.saturn.common.entity.RespBody;
import com.saturn.common.entity.TransactionManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by john.y on 2017-6-26.
 */
public class ClientDecoder extends ByteToMessageDecoder {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {


        System.out.println("channelInactive. from server");
        super.channelInactive(ctx);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        int HeaderSize = 12;
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

            if (headerIdentity.getCommandId() == 0x00000001) {
                System.out.println("link check from server ," + headerIdentity.printContent());
                keepalive(ctx);

            } else if (headerIdentity.getCommandId() == 0x80000001) {
                System.out.println("link check response," + headerIdentity.printContent());

            } else if (headerIdentity.getCommandId() == 0x80000006) {
                System.out.println("notice resp," + headerIdentity.printContent());
            } else {
                System.out.println("unknown resp," + headerIdentity.printContent());
            }

            int nextToRead = headerIdentity.getLength() - HeaderIdentity.HeaderLen;

            if (!in.isReadable(nextToRead)) {
                in.resetReaderIndex();
                return;
            }

            if (nextToRead == 0) {
                // System.out.println("keepalive do nothing .date:" + new Date().toString());
                out.add(new RespBody());
                return;
            }

            ByteBufInputStream is = new ByteBufInputStream(in, nextToRead);

            byte[] bodyBuff = new byte[nextToRead];
            is.read(bodyBuff, 0, bodyBuff.length);

            RespBody respBody = RespBody.fromBuffer(bodyBuff);

            respBody.setHeaderIdentity(headerIdentity);
            // System.out.println("decode resp:" + respBody.getRespCode());
            //todo connection response
            out.add(respBody);

        }
    }

    private void keepalive(ChannelHandlerContext ctx) {
        RequestMsg requestMsg = new RequestMsg();

        requestMsg.setBodyBuff(new byte[0]);
        HeaderIdentity header = new HeaderIdentity();
        header.setLength(requestMsg.getBodyBuff().length + HeaderIdentity.HeaderLen);
        header.setCommandId(0x80000001);
        header.setTransactionID(TransactionManager.getNextTid());
        requestMsg.setHeaderIdentity(header);

        ctx.channel().writeAndFlush(requestMsg);

    }


}
