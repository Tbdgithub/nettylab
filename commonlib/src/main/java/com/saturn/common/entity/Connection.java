package com.saturn.common.entity;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

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

        System.out.println("disconnect cause:");
        cause.printStackTrace();
        this.channel.disconnect();
        //future


        System.out.println("channel disconnect:");
    }


    public String getConnKey() {
        InetSocketAddress address = (InetSocketAddress) channel.localAddress();
        String localAddr = address.getAddress().getHostAddress() + ":" + address.getPort();

        InetSocketAddress remoteAddress = (InetSocketAddress) channel.remoteAddress();
        String remoteAddr = remoteAddress.getAddress().getHostAddress() + ":" + remoteAddress.getPort();

        String key = localAddr + "-" + remoteAddr;
        return key;
    }

    public void sendRequest(RequestMsg request) throws Exception {

        // if (this.channel.isActive())
        //{
        ChannelFuture future = this.channel.writeAndFlush(request);
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {


                if (future.isSuccess()) {

                } else {
                    System.out.println("send failed");
                    //
                    //
                    RespBody respBody = new RespBody();
                    HeaderIdentity respHeader = new HeaderIdentity();
                    respHeader.setTransactionID(request.getHeaderIdentity().getTransactionID());
                    respBody.setHeaderIdentity(respHeader);
                    respBody.setRespCode(500);
                    //
                    Transaction transaction = TransactionManager.Instance.getTransaction(String.valueOf(respBody.getHeaderIdentity().getTransactionID()));
                    if (transaction != null) {
                        //有异常时马上通知future
                        transaction.handleResp(respBody);

                    }
                    Connection.this.disconnect(future.cause());
                    //disconnect
                }
            }
        });


        //}
        // else {
        //     throw new Exception("bad connection");
        //  }

        //System.out.println("send future isSuccess:" + future.isSuccess());

    }


}
