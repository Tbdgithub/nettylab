package com.saturn.test.common.radical;

/**
 * 求根
 */
public class RadicalBinary extends RadicalBuilder {

    public RadicalBinary(int n) {
        this.n = n;
    }

    private double getMiddle(double left, double right) {
        return (left + right) / 2;
    }

    @Override
    public double process() {

        double left = 0;
        double right = n;
        double result = 0;

        while (Math.abs(result * result - n) > small) {
            result = getMiddle(left, right);

            if (result * result < n) {
                left = result;

            } else if (result * result > n) {
                right = result;
            } else {
                break;
            }

            count++;

            if (count >= maxCount) {
                System.out.println("over maxCount:" + maxCount);
                break;
            }
        }

        printTryCount();

        return result;
    }
}
