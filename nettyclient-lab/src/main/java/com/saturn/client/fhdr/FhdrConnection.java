package com.saturn.client.fhdr;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;

/**
 * Created by john.y on 2017-6-29.
 */
public class FhdrConnection {

    private InetSocketAddress remoteAddr;
    private InetSocketAddress localAddr;
    private Bootstrap bootstrap;
    private Channel clientChannel;




}
