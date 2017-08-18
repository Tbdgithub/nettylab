package com.saturn.client.ppclient;

/**
 * Created by john.y on 2017-8-17.
 */
public class SUBMIT extends PPMessage {

    public SUBMIT() {
        this.setCommandId(0x00000004);
    }

    //index 20
    private int PackageTotal;
    private int PackageNumber;
    private int RegisteredDelivery;

    private int MsgLevel;
    private String ServiceID;

    private int FeeUserType;
    private String FeeTerminalID;
    private int TpPid;
    private int Udhi;
    private int MsgFormat;

    private String MsgSrc;
    private String FeeType;
    private String FeeCode;
    private String ValidTime;
    private String AtTime;
    private String SrcID;
    private int DestUserTotal;

    private String[] DestTerminalIDs;

    private int MsgLength;

    private String MsgContent;
    private byte[] RawMsg;

    public int getPackageTotal() {
        return PackageTotal;
    }

    public void setPackageTotal(int packageTotal) {
        PackageTotal = packageTotal;
    }

    public int getPackageNumber() {
        return PackageNumber;
    }

    public void setPackageNumber(int packageNumber) {
        PackageNumber = packageNumber;
    }

    public int getRegisteredDelivery() {
        return RegisteredDelivery;
    }

    public void setRegisteredDelivery(int registeredDelivery) {
        RegisteredDelivery = registeredDelivery;
    }

    public int getMsgLevel() {
        return MsgLevel;
    }

    public void setMsgLevel(int msgLevel) {
        MsgLevel = msgLevel;
    }

    public String getServiceID() {
        return ServiceID;
    }

    public void setServiceID(String serviceID) {
        ServiceID = serviceID;
    }

    public int getFeeUserType() {
        return FeeUserType;
    }

    public void setFeeUserType(int feeUserType) {
        FeeUserType = feeUserType;
    }

    public String getFeeTerminalID() {
        return FeeTerminalID;
    }

    public void setFeeTerminalID(String feeTerminalID) {
        FeeTerminalID = feeTerminalID;
    }

    public int getTpPid() {
        return TpPid;
    }

    public void setTpPid(int tpPid) {
        TpPid = tpPid;
    }

    public int getUdhi() {
        return Udhi;
    }

    public void setUdhi(int udhi) {
        Udhi = udhi;
    }

    public int getMsgFormat() {
        return MsgFormat;
    }

    public void setMsgFormat(int msgFormat) {
        MsgFormat = msgFormat;
    }

    public String getMsgSrc() {
        return MsgSrc;
    }

    public void setMsgSrc(String msgSrc) {
        MsgSrc = msgSrc;
    }

    public String getFeeType() {
        return FeeType;
    }

    public void setFeeType(String feeType) {
        FeeType = feeType;
    }

    public String getFeeCode() {
        return FeeCode;
    }

    public void setFeeCode(String feeCode) {
        FeeCode = feeCode;
    }

    public String getValidTime() {
        return ValidTime;
    }

    public void setValidTime(String validTime) {
        ValidTime = validTime;
    }

    public String getAtTime() {
        return AtTime;
    }

    public void setAtTime(String atTime) {
        AtTime = atTime;
    }

    public String getSrcID() {
        return SrcID;
    }

    public void setSrcID(String srcID) {
        SrcID = srcID;
    }

    public int getDestUserTotal() {
        return DestUserTotal;
    }

    public void setDestUserTotal(int destUserTotal) {
        DestUserTotal = destUserTotal;
    }

    public String[] getDestTerminalIDs() {
        return DestTerminalIDs;
    }

    public void setDestTerminalIDs(String[] destTerminalIDs) {
        DestTerminalIDs = destTerminalIDs;
    }

    public int getMsgLength() {
        return MsgLength;
    }

    public void setMsgLength(int msgLength) {
        MsgLength = msgLength;
    }

    public String getMsgContent() {
        return MsgContent;
    }

    public void setMsgContent(String msgContent) {
        MsgContent = msgContent;
    }

    public byte[] getRawMsg() {
        return RawMsg;
    }

    public void setRawMsg(byte[] rawMsg) {
        RawMsg = rawMsg;
    }

    @Override
    public byte[] getPayload() throws Exception {

        if (payload == null) {

            int totalLen = 130 + 21 * 1 + RawMsg.length + 1;

            byte[] total = new byte[totalLen];
            total[20] = (byte) PackageTotal;
            total[21] = (byte) PackageNumber;
            total[22] = (byte) RegisteredDelivery;
            total[23] = (byte) MsgLevel;

            byte[] ServiceIDBuff = ServiceID.getBytes("ASCII");
            int len = Math.min(10, ServiceIDBuff.length);
            System.arraycopy(ServiceIDBuff, 0, total, 24, len);

            total[34] = (byte) FeeUserType;

            if(FeeTerminalID!=null) {
                byte[] FeeTerminalIDBuff = FeeTerminalID.getBytes("ASCII");
                len = Math.min(21, FeeTerminalIDBuff.length);
                System.arraycopy(FeeTerminalIDBuff, 0, total, 35, len);
            }

            total[56] = (byte) TpPid;
            total[57] = (byte) Udhi;
            total[58] = (byte) MsgFormat;

            byte[] MsgSrcBuff = MsgSrc.getBytes("ASCII");
            len = Math.min(6, MsgSrcBuff.length);
            System.arraycopy(MsgSrcBuff, 0, total, 59, len);


            byte[] FeeTypeBuff = FeeType.getBytes("ASCII");
            len = Math.min(2, FeeTypeBuff.length);
            System.arraycopy(FeeTypeBuff, 0, total, 65, len);


            byte[] FeeCodeBuff = FeeCode.getBytes("ASCII");
            len = Math.min(6, FeeCodeBuff.length);
            System.arraycopy(FeeCodeBuff, 0, total, 66, len);


            if(ValidTime!=null) {
                byte[] ValidTimeBuff = ValidTime.getBytes("ASCII");
                len = Math.min(17, ValidTimeBuff.length);
                System.arraycopy(ValidTimeBuff, 0, total, 73, len);
            }

            if(AtTime!=null) {
                byte[] AtTimeBuff = AtTime.getBytes("ASCII");
                len = Math.min(17, AtTimeBuff.length);
                System.arraycopy(AtTimeBuff, 0, total, 90, len);
            }


            byte[] SrcIDBuff = SrcID.getBytes("ASCII");
            len = Math.min(21, SrcIDBuff.length);
            System.arraycopy(SrcIDBuff, 0, total, 107, len);

            total[128] = (byte) DestUserTotal;


            //only one mobile
            byte[] DestTerminalIDsBuff = DestTerminalIDs[0].getBytes("ASCII");
            len = Math.min(21, DestTerminalIDsBuff.length);
            System.arraycopy(DestTerminalIDsBuff, 0, total, 129, len);


            total[150] = (byte) MsgLength;

            System.arraycopy(RawMsg, 0, total, 151, RawMsg.length);


            payload = total;

        }

        return payload;

    }
}
