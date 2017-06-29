package com.saturn.client.fhdr;

import com.saturn.infrastructure.Future;
import com.saturn.infrastructure.FutureListener;
import com.saturn.infrastructure.Result;
import com.saturn.infrastructure.util.StringUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by john.y on 2017-6-29.
 */
public class FixedHeaderClient {

    private String host;
    private int port;
    private Bootstrap bootstrap;
    private Channel clientChannel;

    /**
     * mini second
     */
    private int Timeout = 5000;

    public int getTimeout() {
        return Timeout;
    }

    public void setTimeout(int timeout) {
        Timeout = timeout;
    }

    private volatile ConcurrentMap<RemoteHostAndPort, Connection> connections = new ConcurrentHashMap<RemoteHostAndPort, Connection>();

    public static void main(String[] args) throws Exception {

        FixedHeaderClient client = new FixedHeaderClient("218.205.115.242", 55062);
        client.setTimeout(20 * 1000);
        System.out.println("Please input:");
        readInput(client);

        System.out.println("quited!");

    }

    private static void readInput(final FixedHeaderClient client) {

        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new InputStreamReader(System.in));

            String line = bf.readLine();

            while (!line.equals("quit")) {

                if (StringUtils.isNullOrEmpty(line)) {
                    line = bf.readLine();
                    continue;
                }

                RequestMsg requestMsg = new RequestMsg();
                requestMsg.setBodyBuff(line.getBytes("utf-8"));

                HeaderIdentity header = new HeaderIdentity();
                header.setLength(requestMsg.getBodyBuff().length + HeaderIdentity.HeaderLen);
                header.setCommandId(0x00000006);
                header.setTransactionID(IdGenerator.getNextTid());
                requestMsg.setHeaderIdentity(header);

//                RespBody respBody = client.sendMessage(requestMsg);
//                if (respBody != null) {
//                    System.out.println("client resp:" + respBody.getRespCode());
//                }

                client.sendMessageAsync(requestMsg);

                line = bf.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public FixedHeaderClient(String host, int port) throws Exception {
        this.host = host;
        this.port = port;

        EventLoopGroup group = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        //inbound
                        p.addLast("decoder", new ClientDecoder());
                        p.addLast("ClientRespHandler", new ClientRespHandler());

                        //outbound
                        p.addLast("encoder1", new MsgEncoder());

                    }
                });

    }


    public void createConnection() throws Exception {

        final Future<Throwable> future = new Future<Throwable>();
        ChannelFuture cf = bootstrap.connect(host, port);

        cf.addListener(a ->
                {
                    if (a.isSuccess()) {

                        Connection connection = new Connection(cf.channel());

                        connections.put(connection.getRemoteHostAndPort(), connection);
                        future.complete(null);
                    } else {

                        //future.complete(null);
                        a.cause().printStackTrace();
                        future.complete(cf.cause());
                    }
                }

        );

        // cf.get(10, TimeUnit.SECONDS);

        future.setTimeout(Timeout);
        Throwable error = future.getValue();

    }


    private Connection getConnection(RemoteHostAndPort endPoint) throws Exception {

        Connection connection = connections.get(endPoint);
        if (connection == null) {
            createConnection();
            connection = connections.get(endPoint);
        }
        return connection;
    }

    public RespBody sendMessage(RequestMsg requestMsg) throws Exception {

        RespBody respBody = null;
        RemoteHostAndPort remoteHostAndPort = new RemoteHostAndPort(host, port);
        Connection connection = getConnection(remoteHostAndPort);

        if (connection != null) {
            // System.out.println("conn:" + connection.getConnKey());
            Transaction transaction = connection.createTransaction();
            transaction.setRequest(requestMsg);
            RespFuture respFuture = transaction.begin();
            respFuture.setTimeout(Timeout);
            respBody = respFuture.getValue();

        }

        return respBody;
    }

    public void sendMessageAsync(RequestMsg requestMsg) {


        try {
            RemoteHostAndPort remoteHostAndPort = new RemoteHostAndPort(host, port);
            Connection connection = getConnection(remoteHostAndPort);

            if (connection != null) {
                // System.out.println("conn:" + connection.getConnKey());
                Transaction transaction = connection.createTransaction();
                transaction.setRequest(requestMsg);
                RespFuture respFuture = transaction.begin();
                respFuture.setTimeout(Timeout);
                respFuture.addListener(new FutureListener<RespBody>() {
                    @Override
                    public void run(Result<RespBody> result) {

                        try {
                            System.out.println("client resp:" + respFuture.getValue().getRespCode());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


//
//    finally
//
//    {
//        // Shut down the event loop to terminate all threads.
//        group.shutdownGracefully();
//    }


    private final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) {
            if (future.isSuccess()) {
                System.out.println("operationComplete future succeed");
            } else {
                future.cause().printStackTrace();
                future.channel().close();
            }
        }
    };

}
