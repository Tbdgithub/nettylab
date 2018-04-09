package com.saturn.common.array;

import java.util.Iterator;
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
        //
//        System.out.println();
//         while (!path.isEmpty())
//        {
//            System.out.print(path.pop()+" (y) ");
//        }

        // boolean exists = exist(board, "a");

        // boolean exists = exist_iter(board, "FC");
        char[][] board = {{'A', 'C', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        // char[][] board = {{'a'}};
        // char[][] board = {{'a', 'a', 'a', 'a'}, {'a', 'a', 'a', 'a'}, {'a', 'a', 'a', 'a'}, {'a', 'a', 'a', 'a'}, {'a', 'a', 'a', 'b'}};
        print(board);
        Stack<Character> path = new Stack<>();
        //boolean exists = exist_recursive(board, "ACU", path);
        //aaaaaaaaaaaaaaaaaaaa
        boolean exists = exist_iter(board, "ACCESEEDASF");


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

//
//    private boolean exist_iter1(char[][] board, String words) {
//
//    }

    private boolean exist_iter(char[][] board, String words) {
        char[] wordChars = words.toCharArray();
        //1. 用一个stack

        if (wordChars.length == 0) {
            return false;
        }

        Stack<Point> stack = new Stack<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] != wordChars[0]) {
                    continue;
                }

                //第一个匹配的
                Point current = new Point(i, j);
                current.wordIndex = 0;
                current.val = board[i][j];
                stack.push(current);
                //标识visited
                board[i][j] ^= 256;

                while (stack.size() > 0) {
                    Point prev = stack.pop();

                    if (prev.wordIndex >= wordChars.length - 1) {
                        //已找完了

                        System.out.println("Path:");
                        Iterator<Point> iterator = stack.iterator();
                        while (iterator.hasNext()) {
                          System.out.print(iterator.next().val+" ");
                        }

                        System.out.print(prev.val+" ");

                        return true;
                    }

                    //上下左右都找了
                    if (prev.arrowIndex >= 4) {
                        //清除visited
                        board[prev.x][prev.y] ^= 256;
                        continue;
                    }

                    //move next;
                    //check 1,2,3,4

                    int nextX = -1;
                    int nextY = -1;
                    int nextArrow = prev.arrowIndex;

                    if (nextArrow == 0) {
                        nextX = prev.x - 1;
                        nextY = prev.y;

                    } else if (nextArrow == 1) {
                        nextX = prev.x;
                        nextY = prev.y + 1;
                    } else if (nextArrow == 2) {
                        nextX = prev.x + 1;
                        nextY = prev.y;
                    } else if (nextArrow == 3) {
                        nextX = prev.x;
                        nextY = prev.y - 1;
                    }

                    //arrow move next
                    nextArrow++;
                    prev.arrowIndex = nextArrow;

                    stack.push(prev);

                    if (nextX < 0 || nextX >= board.length || nextY < 0 || nextY >= board[prev.x].length) {
                        //没找到
                        continue;
                    }

                    int nextWordIndex = prev.wordIndex + 1;
                    if (wordChars[nextWordIndex] == board[nextX][nextY]) {

                        //  System.out.println( board[nextX][nextY]+" ");
                        Point milestone = new Point(nextX, nextY);
                        milestone.wordIndex = prev.wordIndex + 1;
                        milestone.arrowIndex = 0;
                        milestone.val = board[nextX][nextY];
                        stack.push(milestone);
                        //标识已visited
                        board[nextX][nextY] ^= 256;
                        continue;
                    }
                }

            }
        }

        return false;
    }

    class Point {
        int x;
        int y;
        int arrowIndex;
        int wordIndex;
        char val;

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
        System.out.print(board[x][y] + " (" + x + "," + y + ") ");
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
