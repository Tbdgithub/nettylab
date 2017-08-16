package com.saturn.client.ppclient;

import com.saturn.infrastructure.util.ByteUtils;
import com.saturn.infrastructure.util.Md5Helper;

/**
 * Created by john.y on 2017-8-15.
 */
public class AuthMsg extends ReqMsg {


    private String SpCode;

    private String SharedSecret;

    public String getSpCode() {
        return SpCode;
    }

    public void setSpCode(String spCode) {
        SpCode = spCode;
    }

    public String getSharedSecret() {
        return SharedSecret;
    }

    public void setSharedSecret(String sharedSecret) {
        SharedSecret = sharedSecret;
    }

    public AuthMsg() {
        this.setCommandId(0x00000001);
    }


    @Override
    public byte[] getPayload() throws Exception {

        int len = 39 - Constants.HeaderLen;
        byte[] result = new byte[len];


        byte[] SpCodeBuff = this.SpCode.getBytes("ASCII");


        //byte[] AuthenticatorSourceBuff = new byte[16];

        byte[] buf = new byte[6 + 9 + SharedSecret.length() + 10];
        System.arraycopy(SpCodeBuff, 0, buf, 0, 6);

        byte[] SharedSecretBuff = SharedSecret.getBytes("ASCII");
        System.arraycopy(SharedSecretBuff, 0, buf, 6 + 9, SharedSecretBuff.length);

        byte[] timestampBuff = getTimestamp().getBytes("ASCII");
        System.arraycopy(timestampBuff, 0, buf, 6 + 9 + SharedSecretBuff.length, timestampBuff.length);

        //  System.arraycopy(AuthenticatorSourceBuff, 0, result, 0, 16);


        byte[] AuthenticatorSourceBuff = Md5Helper.md5(buf);

        System.arraycopy(SpCodeBuff, 0, result, 0, 6);

        System.arraycopy(AuthenticatorSourceBuff, 0, result, 6, AuthenticatorSourceBuff.length);

        result[26] = getVersoin();
        setPayloadLen(result.length);

        return result;

    }

    @Override
    public byte[] getBytes() throws Exception {


        int totalLen = Constants.HeaderLen + getPayload().length;
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


        System.arraycopy(getPayload(), 0, totalBuff, 12, getPayloadLen());
        return totalBuff;

    }
}
