package com.saturn.common.entity;

import com.saturn.infrastructure.util.ByteUtils;

/**
 * Created by john.y on 2017-6-26.
 */
public class RespBody extends Response {



    private int respCode;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public static RespBody fromBuffer(byte[] buffer) {
        RespBody idt = new RespBody();

        idt.setRespCode(ByteUtils.byteArrayToInt32(buffer, 0, true));

        return idt;
    }
}
