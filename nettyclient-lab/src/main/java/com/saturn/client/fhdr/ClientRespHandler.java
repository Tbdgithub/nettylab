package com.saturn.client.fhdr;

import com.saturn.common.entity.RespBody;
import com.saturn.common.entity.Transaction;
import com.saturn.common.entity.TransactionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by john.y on 2017-6-29.
 */
public class ClientRespHandler extends SimpleChannelInboundHandler<RespBody> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RespBody msg) throws Exception {

        // System.out.println("resp:" + msg.getRespCode());
        //1.header->tid
        //tran

        if (msg.getHeaderIdentity() == null) {
            // System.out.println("empty body");
            return;
        }

        int seq = msg.getHeaderIdentity().getTransactionID();
        String seqkey = String.valueOf(seq);
        Transaction tx = TransactionManager.Instance.removeTransaction(seqkey);
        if (tx != null) {
            tx.handleResp(msg);
        }


    }

}
