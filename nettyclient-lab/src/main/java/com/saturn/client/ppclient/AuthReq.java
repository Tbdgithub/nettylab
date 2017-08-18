package com.saturn.client.ppclient;

import com.saturn.infrastructure.util.Md5Helper;

/**
 * Created by john.y on 2017-8-15.
 */
public class AuthReq extends PPMessage {


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

    public AuthReq() {
        this.setCommandId(0x00000001);
    }

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


    @Override
    public byte[] getPayload() throws Exception {

        if (payload == null) {
            int len = 39 - Constants.HeaderLen;
            byte[] result = new byte[len];

            byte[] SpCodeBuff = this.SpCode.getBytes("ASCII");

            byte[] buf = new byte[6 + 9 + SharedSecret.length() + 10];
            System.arraycopy(SpCodeBuff, 0, buf, 0, 6);

            byte[] SharedSecretBuff = SharedSecret.getBytes("ASCII");
            System.arraycopy(SharedSecretBuff, 0, buf, 6 + 9, SharedSecretBuff.length);

            byte[] timestampBuff = getTimestamp().getBytes("ASCII");
            System.arraycopy(timestampBuff, 0, buf, 6 + 9 + SharedSecretBuff.length, timestampBuff.length);

            byte[] AuthenticatorSourceBuff = Md5Helper.md5(buf);

            System.arraycopy(SpCodeBuff, 0, result, 0, 6);

            System.arraycopy(AuthenticatorSourceBuff, 0, result, 6, AuthenticatorSourceBuff.length);

            result[26] = getVersoin();

            payload = result;

        }

        return payload;

    }

}
