package com.saturn.client.ppclient;

/**
 * Created by john.y on 2017-8-15.
 */
public class ReqMsg {

    private int Length;
    private int CommandId;
    private int TransactionID;

    private byte[] payload;

    private int payloadLen;

    private String timestamp = "0000000000";

    private byte versoin;//(byte)(_isMo ? 0x01 : 0x00)

    public byte getVersoin() {
        return versoin;
    }

    public void setVersoin(byte versoin) {
        this.versoin = versoin;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getPayloadLen() {
        return payloadLen;
    }

    public void setPayloadLen(int payloadLen) {
        this.payloadLen = payloadLen;
    }

    public byte[] getBytes()  throws Exception{
        return null;
    }


    public byte[] getPayload() throws Exception

    {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
        this.payloadLen = payload.length;
    }

    public int getLength() {
        return Length;
    }

    public void setLength(int length) {
        Length = length;
    }

    public int getCommandId() {
        return CommandId;
    }

    public void setCommandId(int commandId) {
        CommandId = commandId;
    }

    public int getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(int transactionID) {
        TransactionID = transactionID;
    }
}
