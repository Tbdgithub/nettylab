package com.saturn.client.fhdr;

/**
 * Created by john.y on 2017-6-26.
 */
public class RequestMsg extends Request {

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
