package com.saturn.client.sendback;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;

import java.util.List;

/**
 * Created by yibo on 2017-6-22.
 */
public class EchoMessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // in.markReaderIndex();
        //ByteBufInputStream is = new ByteBufInputStream(in, in.readableBytes());

        // byte[] readBuff = new byte[200];
        //  ByteBufInputStream is = new ByteBufInputStream(in, in.readableBytes());

        //  is.read(readBuff);

        // String msg = new String(readBuff);
        //String msg = is.readLine();
        System.out.println("client inbound 0");

        StringBuilder sb = new StringBuilder();

        while (in.isReadable()) { // (1)
            //System.out.print((char) in.readByte());
            //System.out.flush();
            sb.append((char) in.readByte());
        }

        //String msgText=in.toString(io.netty.util.CharsetUtil.US_ASCII);
        //System.out.print(msgText);


        //String msgText = in.toString(CharsetUtil.UTF_8);

       // System.out.println("decode :" + sb.toString());
        out.add(sb.toString());
        //ReferenceCountUtil.release(in);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (cause instanceof DecoderException) {


            ctx.close();
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }
}
