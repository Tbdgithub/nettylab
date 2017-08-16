package com.saturn.client.ppclient;

/**
 * Created by john.y on 2017-8-15.
 */
public class PPClientMain {

    public static void main(String[] args) throws Exception {

        ConnManager manager = new ConnManager();
        manager.init(1, "192.168.110.172", 7890);

        manager.start();

        Thread.sleep(1000);

        AuthReq authMsg = new AuthReq();
        authMsg.setTransactionID(0);

        authMsg.setSpCode("901487");
        authMsg.setSharedSecret("ICP");
        authMsg.setVersoin((byte) 0x00);

        manager.writeMsg(authMsg);

        Thread.sleep(1000 * 500);

    }


}
