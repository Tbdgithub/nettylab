package com.saturn.server.fhdr;

import com.saturn.common.entity.HeaderIdentity;
import com.saturn.common.entity.RequestMsg;
import com.saturn.common.entity.RespBody;
import com.saturn.common.entity.TransactionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by john.y on 2017-7-3.
 */
public class SvHandler extends SimpleChannelInboundHandler<RequestMsg> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMsg msg) throws Exception {

        System.out.println("ctx channel:" + ctx.channel().hashCode());
        RespBody respMsg = new RespBody();

        int respCode = getRespCode(msg);
        respMsg.setRespCode(respCode);
        HeaderIdentity header = new HeaderIdentity();
        header.setLength(4 + HeaderIdentity.HeaderLen);
        int commandId = getRespCommandId(msg.getHeaderIdentity().getCommandId());
        header.setCommandId(commandId);
        header.setTransactionID(TransactionManager.getNextTid());
        respMsg.setHeaderIdentity(header);

        ctx.channel().writeAndFlush(respMsg);
    }


    private int getRespCommandId(int srcId) throws Exception {
        int resp = 0;
        switch (srcId) {
            case 0x00000001:
                resp = 0x80000001;
                break;
            case 0x00000006:
                resp = 0x80000006;
                break;
            default:
                throw new Exception("unknown commandId");
        }

        return resp;

    }

    private int getRespCode(RequestMsg requestMsg) {


        int resp = 200;
        if (requestMsg.getHeaderIdentity().getCommandId() == 0x00000006) {

            String msg = new String(requestMsg.getBodyBuff());
            if ("哈哈哈".equals(msg)) {
                resp = 304;
            }
        }

        //ByteUtils.fillByteBufferWithInt32(resp, buff, 0, true);

        return resp;
    }
}
