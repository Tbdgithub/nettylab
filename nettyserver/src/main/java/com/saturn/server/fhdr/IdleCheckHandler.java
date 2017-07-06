package com.saturn.server.fhdr;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by john.y on 2017-7-5.
 */
public class IdleCheckHandler extends ChannelDuplexHandler {


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        //todo 关连接
        System.out.println("channelInactive. close connection");
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            System.out.println("Idle Time out. close connection");


            ctx.close();

            //close connection

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
