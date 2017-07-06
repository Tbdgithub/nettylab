package com.saturn.client.fhdr;

import com.saturn.common.entity.HeaderIdentity;
import com.saturn.common.entity.RequestMsg;
import com.saturn.infrastructure.util.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;

/**
 * Created by john.y on 2017-6-26.
 */
public class ClientMsgEncoder extends MessageToByteEncoder<RequestMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RequestMsg msg, ByteBuf out) throws Exception {


        System.out.println("client ctx channel:"+ctx.channel().hashCode());
        if (msg.getHeaderIdentity().getCommandId() == 0x00000006) {
            System.out.println("notice request," + msg.getHeaderIdentity().printContent());
            writeMsg(msg, out);
        } else if (msg.getHeaderIdentity().getCommandId() == 0x00000001) {
            writeMsg(msg, out);
            System.out.println("link check request," + msg.getHeaderIdentity().printContent());
        }
        else if (msg.getHeaderIdentity().getCommandId() == 0x80000001) {
            writeMsg(msg, out);
            System.out.println("link check response," + msg.getHeaderIdentity().printContent());
        }


    }

    private void writeMsg(RequestMsg msg, ByteBuf out) throws IOException {
        ByteBufOutputStream os = new ByteBufOutputStream(out);
        byte[] msgBuff = msg.getBodyBuff();

        byte[] headerLenBuff = new byte[4];
        int len = HeaderIdentity.HeaderLen + msgBuff.length;
        ByteUtils.fillByteBufferWithInt32(len, headerLenBuff, 0, true);

        byte[] commandIdBuff = new byte[4];
        ByteUtils.fillByteBufferWithInt32(msg.getHeaderIdentity().getCommandId(), commandIdBuff, 0, true);

        byte[] tranIdBuff = new byte[4];

        ByteUtils.fillByteBufferWithInt32(msg.getHeaderIdentity().getTransactionID(), tranIdBuff, 0, true);

        os.write(headerLenBuff);
        os.write(commandIdBuff);
        os.write(tranIdBuff);
        os.write(msgBuff);
        os.flush();
    }
}
