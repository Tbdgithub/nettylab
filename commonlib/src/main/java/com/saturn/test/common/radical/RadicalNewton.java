package com.saturn.test.common.radical;

public class RadicalNewton extends RadicalBuilder {

    public RadicalNewton(int n) {
        this.n = n;
    }

    @Override
    public double process() {

        double result = n/2;
        while (Math.abs(result * result - n) > small) {

            result= (result+ n/result)/2;//这一条关键
            this.count++;
            if (count >= maxCount) {
                System.out.println("over maxCount:" + maxCount);
                break;
            }
        }

        printTryCount();
        return result;
    }
}
