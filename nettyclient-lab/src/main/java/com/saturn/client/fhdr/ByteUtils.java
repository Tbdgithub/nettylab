package com.saturn.client.fhdr;

/**
 * Created by john.y on 2017-6-26.
 */
public class ByteUtils {

    public static int byteArrayToInt32(byte[] buffer, int offset, boolean bigIndian) {

        if (bigIndian) {
            int n = (buffer[offset] & 0x000000FF) << 24;
            n |= (buffer[offset + 1] & 0x000000FF) << 16;
            n |= (buffer[offset + 2] & 0x000000FF) << 8;
            n |= (buffer[offset + 3] & 0x000000FF);
            return n;
        } else {

            int n = (buffer[offset + 3] & 0x000000FF) << 24;
            n |= (buffer[offset + 2] & 0x000000FF) << 16;
            n |= (buffer[offset + 1] & 0x000000FF) << 8;
            n |= (buffer[offset + 0] & 0x000000FF);
            return n;
        }
    }

    public static void fillByteBufferWithInt32(int n, byte[] buffer, int offset, boolean bigIndian) {

        if (bigIndian) {
            buffer[offset + 0] = (byte) ((n >> 24) & 0xff);
            buffer[offset + 1] = (byte) ((n >> 16) & 0xff);
            buffer[offset + 2] = (byte) ((n >> 8) & 0xff);
            buffer[offset + 3] = (byte) (n & 0xff);
        } else {
            buffer[offset + 3] = (byte) ((n >> 24) & 0xff);
            buffer[offset + 2] = (byte) ((n >> 16) & 0xff);
            buffer[offset + 1] = (byte) ((n >> 8) & 0xff);
            buffer[offset + 0] = (byte) (n & 0xff);
        }
    }


}
