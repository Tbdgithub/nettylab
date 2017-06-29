package com.saturn.client.fhdr;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;

/**
 * Created by john.y on 2017-6-29.
 */
public class Connection {

    private InetSocketAddress remoteAddr;
    private InetSocketAddress localAddr;
    private Bootstrap bootstrap;
    private Channel channel;

    private RemoteHostAndPort remoteHostAndPort;

    public RemoteHostAndPort getRemoteHostAndPort() {
        return remoteHostAndPort;
    }

    public void setRemoteHostAndPort(RemoteHostAndPort remoteHostAndPort) {
        this.remoteHostAndPort = remoteHostAndPort;
    }

    public Connection(Channel channel) {
        this.channel = channel;

        InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.remoteAddress();
        this.remoteHostAndPort = new RemoteHostAndPort(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort());

    }

    public Transaction createTransaction() {
        return new Transaction(this);
    }

    protected void checkConnection() {
        if (isIdle()) {
            disconnect(null);
        }
    }

    public boolean isIdle() {
        // return keepalive.getNanos() > MAX_IDLE_NANOS;
        return false;
    }

    void disconnect(Throwable cause) {

    }



    public String getConnKey() {
        InetSocketAddress address = (InetSocketAddress) channel.localAddress();
        String localAddr = address.getAddress().getHostAddress() + ":" + address.getPort();

        InetSocketAddress remoteAddress = (InetSocketAddress) channel.remoteAddress();
        String remoteAddr = remoteAddress.getAddress().getHostAddress() + ":" + remoteAddress.getPort();

        String key = localAddr + "-" + remoteAddr;
        return key;
    }

    public void sendRequest(RequestMsg request) {
        this.channel.writeAndFlush(request);
    }

}
