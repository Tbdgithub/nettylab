package com.saturn.client.ppclient;

import io.netty.channel.Channel;

/**
 * Created by john.y on 2017-8-15.
 */
public class PPConnection {


    private Channel channel;

    public PPConnection(Channel channel) {
        this.channel = channel;

        System.out.println("remote addr:" + channel.remoteAddress().toString());
    }

    public String getKey() {
        return channel.remoteAddress().toString();
    }

    public boolean isActive() {
        return this.channel.isActive();
    }

    public void auth() {

    }

    public  void write(Object msg)
    {
        this.channel.writeAndFlush(msg);
    }
}
