package com.saturn.infrastructure.util;

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


    public String dumpHex(byte[] buff) {

        return "";
    }

    public static String toHexString(byte b) {
        String hex = Integer.toHexString(b & 0xFF).toUpperCase();
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex;
    }

    public static String toHexString(byte[] b, String sep, boolean upper) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if (upper) {
                sb.append(toHexString(b[i]).toUpperCase());
            } else {
                sb.append(toHexString(b[i]).toLowerCase());
            }
            sb.append(sep);
        }

        //sb = sb.length() > 0 ? sb.delete(sb.length() - 1, sb.length()) : sb;
        return sb.toString();
    }

}
