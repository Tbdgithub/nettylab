package com.saturn.client.ppclient;

import com.saturn.infrastructure.util.ByteUtils;

/**
 * Created by john.y on 2017-8-15.
 */
public class PPMessage {

    private int Length;
    private int CommandId;
    private int TransactionID;
    protected byte[] payload;
    private byte[] totalBytes;

    public byte[] getBytes() throws Exception {


        if (totalBytes == null) {
            int totalLen = Constants.HeaderLen + getPayload().length;

            //header
            byte[] headerLenBuff = new byte[4];
            ByteUtils.fillByteBufferWithInt32(totalLen, headerLenBuff, 0, true);

            byte[] commandIdBuff = new byte[4];
            ByteUtils.fillByteBufferWithInt32(getCommandId(), commandIdBuff, 0, true);

            byte[] tranIdBuff = new byte[4];
            ByteUtils.fillByteBufferWithInt32(getTransactionID(), tranIdBuff, 0, true);

            byte[] totalBuff = new byte[totalLen];

            System.arraycopy(headerLenBuff, 0, totalBuff, 0, 4);
            System.arraycopy(commandIdBuff, 0, totalBuff, 4, 4);
            System.arraycopy(tranIdBuff, 0, totalBuff, 8, 4);

            //body
            System.arraycopy(getPayload(), 0, totalBuff, 12, getPayload().length);

            totalBytes = totalBuff;
        }

        return totalBytes;
    }


    public byte[] getPayload() throws Exception {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
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

    public PPMessage parse(byte[] buff) throws Exception {

        return this;
    }

}
