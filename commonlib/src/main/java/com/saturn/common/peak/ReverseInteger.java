package com.saturn.common.peak;

public class ReverseInteger {
//
//    Input: 123
//    Output:  321
//    Example 2:
//
//    Input: -123
//    Output: -321
//    Example 3:
//
//    Input: 120
//    Output: 21

    public static void main(String[] args) {

        //  int result = reverse(1534236469);
        int result = reverse(1534236469);
        System.out.println("result:" + result);
    }

    public static int reverse(int x) {

        //1.10进制，循环取余数
        //2. 结束条件,当前除值为0
        //3.正负
        //4. overflow check

        //1.10进制，循环取余数
        //2. 结束条件,当前除值为0
        //3.正负
        //4. overflow check

        long current = Math.abs((long)x);
        long sum = 0;

        do {
            long m = current / 10;
            long n = current % 10;
            //System.out.println(n);
            sum = (sum * 10 + n);
            current = m;
        }
        while (current > 0);

        if (x < 0) {
            sum = sum * -1;
        }

        if (sum > Integer.MAX_VALUE || sum < Integer.MIN_VALUE) {
            sum = 0;
        }

        return (int) sum;
    }

}
