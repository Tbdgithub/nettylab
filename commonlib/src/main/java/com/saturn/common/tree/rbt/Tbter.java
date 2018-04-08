package com.saturn.common.tree.rbt;

public class Tbter {

    public static void main(String[] args) {
        Tbter job = new Tbter();
        job.start();

    }

    public void start() {
        TreeNode n1 = new TreeNode(7);
        TreeNode n2 = new TreeNode(3);
        TreeNode n3 = new TreeNode(10);

        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(12);

        TreeNode n14 = new TreeNode(11);

        n1.left = n2;
        n1.right = n3;
        n2.parent = n1;
        n3.parent = n1;

        n3.left = n6;
        n3.right = n7;
        n6.parent = n3;
        n7.parent = n3;

        n7.left = n14;
        n14.parent = n7;

        RbtPrinter printer = new RbtPrinter();
        printer.print(n1);

        TreeNode r1 = rotateLeft(n1, n3);
        printer.print(r1);

        TreeNode r2 = rotateRight(n1, n7);
        printer.print(r2);
    }

    public TreeNode rotateRight(TreeNode root, TreeNode y) {

        TreeNode result = null;
        TreeNode x = y.left;
        if (x.right != null) {
            x.right.parent = y;
        }

        y.left = x.right;
        ///////////
        x.parent = y.parent;

        if (y.parent == null) {
            result = x;
        } else {
            if (y == y.parent.left) {
                y.parent.left = x;
            } else {
                y.parent.right = x;
            }
        }
        ///////////////////
        x.right = y;
        y.parent = x;

        result = root;
        return result;
    }

    public TreeNode rotateLeft(TreeNode root, TreeNode x) {

        TreeNode result = null;
        TreeNode y = x.right;

        // beta 和 x 关系
        if (y.left != null) {
            y.left.parent = x;
        }

        x.right = y.left;
        /////////
        // beta 和 x parent 关系
        if (x.parent == null) {
            result = y;
        } else {
            if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }

        }

        y.parent = x.parent;

        ////////
        // x ,y关系
        y.left = x;
        x.parent = y;

        result = root;
        return result;


//        //1.x- beta  可交换
//        //2.y - x.parent 可交换
//        //3.x.y 可交换
//        TreeNode result = null;
//        TreeNode y = x.right;
//
//        //beta -> x
//        if (y.left != null) {
//            y.left.parent = x;
//        }
//
//        //x right ->beta
//        x.right = y.left;//  beta
//
//
//        //beta  处理完
//
//        // y-> x parent
//
//        if (x.parent == null) {
//            result = y;
//        } else {
//
//            // x parent ->y
//            if (x == x.parent.left) {
//                x.parent.left = y;
//            } else {
//                x.parent.right = y;
//            }
//        }
//
//        y.parent = x.parent;
//
//        //y ->left x
//        y.left = x;
//
//        // x parent ->y
//        x.parent = y;
//
//        result = root;
//        return result;
    }
}
