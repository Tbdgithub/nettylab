package com.saturn.client.ppclient;

import com.saturn.common.entity.HeaderIdentity;
import com.saturn.infrastructure.util.ByteUtils;
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

            int nextToRead = headerIdentity.getLength() - HeaderIdentity.HeaderLen;

            if (!in.isReadable(nextToRead)) {
                in.resetReaderIndex();
                return;
            }

            ByteBufInputStream is = new ByteBufInputStream(in, nextToRead);

            byte[] bodyBuff = new byte[nextToRead];
            is.read(bodyBuff, 0, bodyBuff.length);

            String hexStr = ByteUtils.toHexString(bodyBuff, "", false);
            System.out.println("decode networkBytes:" + hexStr);

            int commandId = headerIdentity.getCommandId();

            if (commandId == 0x00000008) {
                System.out.println("keepalive do nothing .date:" + new Date().toString());
                ACTIVE_TEST_RESP test_resp = new ACTIVE_TEST_RESP();
                test_resp.setTransactionID(headerIdentity.getTransactionID());

                out.add(test_resp);
            } else if (commandId == 0x80000001) {
                AuthResp authResp = new AuthResp();

                authResp.setLength(headerIdentity.getLength());
                authResp.setCommandId(headerIdentity.getCommandId());
                authResp.setTransactionID(headerIdentity.getTransactionID());

                authResp.parse(bodyBuff);
                out.add(authResp);

            } else {
                System.out.println("not imp commandId:" + commandId);
            }

        }
    }
}
