package com.saturn.server.sendback;

import io.netty.channel.*;

/**
 * Created by yibo on 2017-6-22.
 */
public class OutHandler1 extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        //ctx.write(msg, promise);
        System.out.println("OutHandler1 write");
        String append="handler1:"+msg;
        ctx.writeAndFlush(append);


    }

    /**
     * Calls {@link ChannelHandlerContext#flush()} to forward
     * to the next {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     */
//    @Override
//    public void flush(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
//        System.out.println("OutHandler1 flush");
//    }
}
