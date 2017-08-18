package com.saturn.client.ppclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by john.y on 2017-8-15.
 */
public class InboundBizHandler extends SimpleChannelInboundHandler<PPMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PPMessage msg) throws Exception {

        System.out.println("InboundBizHandler");

        if (msg instanceof ACTIVE_TEST_RESP) {
            ACTIVE_TEST_RESP empty = (ACTIVE_TEST_RESP) msg;

            ctx.channel().writeAndFlush(empty);

        } else if (msg instanceof AuthResp) {
            AuthResp bizObj = (AuthResp) msg;
            System.out.print("AuthResp status:" + bizObj.getStatus() + " " + (bizObj.getStatus() == 0 ? "succ" : "failed"));
        } else if (msg instanceof SUBMIT_RESP) {
            SUBMIT_RESP obj = (SUBMIT_RESP) msg;
            System.out.println("SUBMIT_RESP result:" + obj.getResult());
        }
    }
}
