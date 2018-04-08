package com.saturn.common.array;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class WordSearch {
    public static void main(String[] args) {
        WordSearch job = new WordSearch();
        job.start();

    }

    public void start() {

        //char a='a';
//        char a = 255 ^ 256;
//
//        System.out.println(a);
//        a ^= a;
//      //  System.out.println(a);
        char[][] board = {{'A', 'C', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};

        // char[][] board = {{'a'}};
        print(board);


         Stack<Character> path=new Stack<>();
         boolean exists = exist_recursive(board, "ACU",path);
//
//        System.out.println();
//         while (!path.isEmpty())
//        {
//            System.out.print(path.pop()+" (y) ");
//        }

        // boolean exists = exist(board, "a");

       // boolean exists = exist_iter(board, "FC");
        System.out.println("exists:" + exists);
    }

    private void print(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }


    private boolean exist_iter(char[][] board, String words) {
        char[] wordChars = words.toCharArray();
        //1. 用一个stack


        Stack<Point> stack=new Stack<>();


        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {


               // Point current=new Point(i,j);
              //  stack.push(current);
                int wordIndex=0;
                while ( wordIndex<wordChars.length)
                {
                    if(wordIndex==wordChars.length)
                    {
                        return true;
                    }

                    if(wordChars[wordIndex]!=board[i][j])
                    {

                        //4 个方向
                        //board[current.x][current.y] ^= 256;


                        break;
                    }

                    wordIndex++;
                }


            }
        }


        return false;
    }

    class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;

        }
    }


    private boolean exist_recursive(char[][] board, String word, Stack<Character> path) {
        //1.记住已visit 过的
        //2. left,right,up,down search
        //3.长度达到word时终止

        char[] wordChars = word.toCharArray();


        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (exist_recursive_inner(board, wordChars, i, j, 0, path)) {
                    return true;
                }
            }
        }


        return false;
    }

    private boolean exist_recursive_inner(char[][] board, char[] words, int x, int y, int wordIndex,
                                          Stack<Character> path) {

        //所有长度都找到了
        if (wordIndex == words.length) {
            return true;
        }

        //越界检查
        if (x < 0 || x == board.length || y < 0 || y == board[x].length) {
            return false;
        }

        //检查失败退出
        if (board[x][y] != words[wordIndex]) {
            //System.out.print(board[x][y]+" ("+x+","+y+") ");
            return false;
        }

        //此时说明 wordIndex 命中
        System.out.print(board[x][y]+" ("+x+","+y+") ");
        //标识visited
        //已visited 过的，不能包含在要检查的点中
        board[x][y] ^= 256;



        //大搜四方
        if (exist_recursive_inner(board, words, x - 1, y, wordIndex + 1, path)
                || exist_recursive_inner(board, words, x + 1, y, wordIndex + 1, path)
                || exist_recursive_inner(board, words, x, y + 1, wordIndex + 1, path)
                || exist_recursive_inner(board, words, x, y - 1, wordIndex + 1, path)
                ) {

            //恢复
            board[x][y] ^= 256;
           // System.out.print(board[x][y]+" (Y) ");
            path.add(board[x][y]);
            return true;
        } else {
            //恢复
            board[x][y] ^= 256;

           // System.out.print(board[x][y] + " (N) ");
            return false;
        }

    }

}
