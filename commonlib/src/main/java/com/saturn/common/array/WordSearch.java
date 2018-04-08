package com.saturn.common.array;

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
        //  char[][] board = {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
//
//        board[0][0] ^= 256;
//
//        board[0][0] ^= 256;
        char[][] board = {{'a'}};
        //boolean exists = exist(board, "ABCCED");
        boolean exists = exist(board, "a");
        System.out.println("exists:" + exists);
    }

    public boolean exist(char[][] board, String word) {

        return exist_recursive(board, word);

    }

    private boolean exist_recursive(char[][] board, String word) {
        //1.记住已visit 过的
        //2. left,right,up,down search
        //3.长度达到word时终止

        char[] wordChars = word.toCharArray();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (exist_recursive_inner(board, wordChars, i, j, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean exist_recursive_inner(char[][] board, char[] words, int x, int y, int wordIndex) {

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
            return false;
        }

        //标识visited
        //已visited 过的，不能包含在要检查的点中
        board[x][y] ^= 256;

        //大搜四方
        if (exist_recursive_inner(board, words, x - 1, y, wordIndex + 1)
                || exist_recursive_inner(board, words, x + 1, y, wordIndex + 1)
                || exist_recursive_inner(board, words, x, y + 1, wordIndex + 1)
                || exist_recursive_inner(board, words, x, y - 1, wordIndex + 1)
                ) {

            //恢复
            board[x][y] ^= 256;
            return true;
        } else {
            //恢复
            board[x][y] ^= 256;
            return false;
        }

    }

}
