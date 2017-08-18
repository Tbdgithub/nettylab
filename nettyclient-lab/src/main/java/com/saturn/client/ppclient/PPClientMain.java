package com.saturn.client.ppclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
        //0:mt ;1:mo
        authMsg.setVersoin((byte) 0x00);
        //authMsg.setVersoin((byte) 0x01);

        manager.writeMsg(authMsg);

        Thread.sleep(1000);

        readInput(manager);


    }

    private static void readInput(ConnManager manager) {

        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new InputStreamReader(System.in));

            String line = bf.readLine();

            while (!line.equals("quit")) {


                if (line.length() < 1) {
                    line = bf.readLine();
                    continue;
                }

                byte[] lineBuff = line.getBytes("ascii");

                SUBMIT requestMsg = new SUBMIT();
                requestMsg.setTransactionID(0);
                requestMsg.setPackageTotal(1);
                requestMsg.setRegisteredDelivery(0);
                requestMsg.setMsgLevel(1);
                requestMsg.setServiceID("+MCHAT");

                requestMsg.setFeeCode("500");
                requestMsg.setFeeUserType(0);
                requestMsg.setFeeType("03");

                requestMsg.setTpPid(0);
                requestMsg.setUdhi(0);
                requestMsg.setMsgFormat(0);
                requestMsg.setMsgSrc("901487");
                requestMsg.setSrcID("12520");

                requestMsg.setDestUserTotal(1);
                requestMsg.setDestTerminalIDs(new String[]{"13683339065"});
                requestMsg.setMsgLength(lineBuff.length);
                requestMsg.setRawMsg(lineBuff);
                //xxx

                System.out.println("write line:" + line);
                manager.writeMsg(requestMsg);
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


}
