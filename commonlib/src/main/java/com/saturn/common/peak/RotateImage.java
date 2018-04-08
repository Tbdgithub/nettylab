package com.saturn.common.peak;

public class RotateImage {


    public static void main(String[] args) {
        RotateImage job = new RotateImage();

        int [][] a1=new int[][] { {1,2},{3,4}};

        job.print(a1);
        job.rotate(a1);

    }

    public void rotate(int[][] matrix) {

        // 每圈右移N-1 格
        //

    }

    private void print(int [][] a)
    {
        for(int [] item :a)
        {
            for(int  subItem: item) {
                System.out.print(subItem+",");
            }

            System.out.println();
        }
    }


}
