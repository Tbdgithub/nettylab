package com.saturn.common.peak;

public class RotateImage {

    public static void main(String[] args) {
        RotateImage job = new RotateImage();

        int[][] maxtix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        job.print(maxtix);
        job.rotate(maxtix);
        System.out.println("after rotate---------------------------------");

    }

    public void rotate(int[][] matrix) {

        // 顺时针转90度
        //1. 上，下交换
        //2. 斜角交换

        if (matrix.length == 0) {
            return;
        }


        //输入 :
        // x1 :1 2 3
        // x2 :4 5 6
        // x3 :7 8 9
        //1. 行矩阵上下旋转
        // x1,x2,x3
        //2. 行转列 ,转置
        // ->
        // x3` x2` x1`
         //结果:
        // 7 4 1
        // 8 5 2
        // 9 6 3


        swapUpDown(matrix);
        System.out.println("after swap updown");
        print(matrix);

        //print(matrix);
        System.out.println("after rotate 90 degree");
        rotate90(matrix);
        print(matrix);


    }

    private void rotate90(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        for (int j = 0; j < col; j++) {
            //关键 i=j 开始，只交换一半
            for (int i = j + 1; i < row; i++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;

            }
        }

    }

    private void swapUpDown(int[][] matrix) {

        int row = matrix.length;
        int col = matrix[0].length;

        for (int j = 0; j < col; j++) {

            //关键i ,row-i 交换
            for (int i = 0; i < row / 2; i++) {

                int temp = matrix[i][j];
                matrix[i][j] = matrix[row - i - 1][j];
                matrix[row - i - 1][j] = temp;
            }
        }


    }

    private void print(int[][] matrix) {
        System.out.println("------------");
        //for(int[] row :matrix)
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }

            System.out.println();
        }
    }

}
