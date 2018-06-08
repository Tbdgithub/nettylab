package com.saturn.common.recursive;

public class ReverseIntegerRecursive {

    /**
     * 只能处理整数
     *
     * @param val
     * @return
     */
    public String print(int val) {

        int m = val / 10;
        int n = val % 10;
        if (m > 0) {
            return n + print(m);
        } else {
            return "" + n;
        }

    }

}
