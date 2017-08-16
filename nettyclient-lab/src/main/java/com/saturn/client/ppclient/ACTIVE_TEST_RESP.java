package com.saturn.client.ppclient;

/**
 * Created by john.y on 2017-8-16.
 */
public class ACTIVE_TEST_RESP extends PPMessage {
    public ACTIVE_TEST_RESP() {
        this.setCommandId(0x80000008);
        this.setLength(Constants.HeaderLen);


    }

    @Override
    public byte[] getPayload() throws Exception {


        if (payload == null) {

            payload = new byte[1];
            setPayloadLen(1);
        }

        return payload;
    }

//    @Override
//    public byte[] getBytes() throws Exception {
//
//
//        int totalLen = Constants.HeaderLen + getPayload().length;
//        byte[] headerLenBuff = new byte[4];
//        ByteUtils.fillByteBufferWithInt32(totalLen, headerLenBuff, 0, true);
//
//        byte[] commandIdBuff = new byte[4];
//        ByteUtils.fillByteBufferWithInt32(getCommandId(), commandIdBuff, 0, true);
//
//        byte[] tranIdBuff = new byte[4];
//        ByteUtils.fillByteBufferWithInt32(getTransactionID(), tranIdBuff, 0, true);
//
//        byte[] totalBuff = new byte[totalLen];
//
//        System.arraycopy(headerLenBuff, 0, totalBuff, 0, 4);
//        System.arraycopy(commandIdBuff, 0, totalBuff, 4, 4);
//        System.arraycopy(tranIdBuff, 0, totalBuff, 8, 4);
//
//        System.arraycopy(getPayload(), 0, totalBuff, 12, getPayloadLen());
//        return totalBuff;
//
//    }
}
