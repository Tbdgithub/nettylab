package com.saturn.client.ppclient;

/**
 * Created by john.y on 2017-8-16.
 */
public class AuthResp extends PPMessage {

    public AuthResp() {
        this.setCommandId(0x80000001);
    }

    private byte status;

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Override
    public PPMessage parse(byte[] buff) throws Exception {

        //header
        //
        //this.setLength();
        if (buff.length != 18) {
            throw new Exception("bad len");
        }

//        int length = ByteUtils.byteArrayToInt32(buff, 0, true);
//        this.setLength(length);
//
//        int commandId = ByteUtils.byteArrayToInt32(buff, 4, true);
//        this.setCommandId(commandId);
//
//        int tranId = ByteUtils.byteArrayToInt32(buff, 8, true);
//        this.setTransactionID(tranId);

        byte status = buff[0];
        this.setStatus(status);

        return this;
    }
}
