package com.saturn.client.fh;

/**
 * Created by john.y on 2017-6-26.
 */
public class RequestMsg {

    private HeaderIdentity headerIdentity;

    private byte[] bodyBuff;

    public byte[] getBodyBuff() {
        return bodyBuff;
    }

    public void setBodyBuff(byte[] bodyBuff) {
        this.bodyBuff = bodyBuff;
    }

    public HeaderIdentity getHeaderIdentity() {
        return headerIdentity;
    }

    public void setHeaderIdentity(HeaderIdentity headerIdentity) {
        this.headerIdentity = headerIdentity;
    }


}