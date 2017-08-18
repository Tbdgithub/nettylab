package com.saturn.client.ppclient;

/**
 * Created by john.y on 2017-8-18.
 */
public class SUBMIT_RESP extends PPMessage {

    private int Result;

    public SUBMIT_RESP() {
        this.setCommandId(0x80000004);
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    @Override
    public PPMessage parse(byte[] buff) throws Exception {

        if (buff.length != 9) {
            throw new Exception("bad len");
        }

        byte status = buff[8];
        this.setResult(status);

        return this;
    }


}
