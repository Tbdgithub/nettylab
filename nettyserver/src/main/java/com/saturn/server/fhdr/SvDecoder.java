package com.saturn.server.fhdr;

import com.saturn.common.entity.HeaderIdentity;
import com.saturn.common.entity.RequestMsg;
import com.saturn.common.entity.RespBody;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by john.y on 2017-7-3.
 */
public class SvDecoder extends ByteToMessageDecoder {
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
                keepalive(ctx, headerIdentity);

            } else if (headerIdentity.getCommandId() == 0x80000001) {
                System.out.println("link check response," + headerIdentity.printContent());


            } else if (headerIdentity.getCommandId() == 0x00000006) {

                //todo
                System.out.println("notice request," + headerIdentity.printContent());
            } else {
                System.out.println("unknown request," + headerIdentity.printContent());
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

            // RespBody respBody = RespBody.fromBuffer(bodyBuff);
            RequestMsg requestMsg = new RequestMsg();

            requestMsg.setHeaderIdentity(headerIdentity);
            String notice = new String(bodyBuff, "utf-8");
            System.out.println("notice:" + notice);
            requestMsg.setBodyBuff(bodyBuff);

            out.add(requestMsg);

        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {

        System.out.println("SvDecoder find a exception");
        ctx.fireExceptionCaught(cause);
    }


    private void keepalive(ChannelHandlerContext ctx, HeaderIdentity headerIdentity) {
        RequestMsg requestMsg = new RequestMsg();

        requestMsg.setBodyBuff(new byte[0]);
        HeaderIdentity header = new HeaderIdentity();
        header.setLength(requestMsg.getBodyBuff().length + HeaderIdentity.HeaderLen);
        header.setCommandId(0x80000001);
        header.setTransactionID(headerIdentity.getTransactionID());
        //TransactionManager.getNextTid()
        requestMsg.setHeaderIdentity(header);

        ctx.channel().writeAndFlush(requestMsg);
        System.out.println("pong "+header.printContent());

    }

}
