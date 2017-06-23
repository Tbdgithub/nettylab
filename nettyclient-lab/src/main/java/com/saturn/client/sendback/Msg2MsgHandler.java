package com.saturn.client.sendback;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by yibo on 2017-6-22.
 */
public class Msg2MsgHandler extends MessageToMessageEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        System.out.println("client:outbound 1");
        String chanedTxt = "hash:" + this.hashCode() + " client append --" + " " + msg;
        out.add(chanedTxt);
    }
}
