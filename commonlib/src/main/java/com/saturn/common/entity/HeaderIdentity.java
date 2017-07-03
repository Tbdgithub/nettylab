package com.saturn.common.entity;

import com.saturn.infrastructure.util.ByteUtils;

/**
 * Created by john.y on 2017-7-3.
 */
public class HeaderIdentity {

    private int Length;
    private int CommandId;
    private int TransactionID;

    public static int HeaderLen = 12;
    public static int MaxBodyLen = 1024 * 1024 * 4;

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

    public static HeaderIdentity fromBuffer(byte[] buffer) {
        HeaderIdentity idt = new HeaderIdentity();

        idt.setLength(ByteUtils.byteArrayToInt32(buffer, 0, true));
        idt.setCommandId(ByteUtils.byteArrayToInt32(buffer, 4, true));
        idt.setTransactionID(ByteUtils.byteArrayToInt32(buffer, 8, true));

        return idt;
    }

    public String printContent() {
        String result = "tranId:" + this.getTransactionID() + " commandId:0x" + Integer.toHexString(this.getCommandId());
        return result;
    }
}