package com.saturn.common.tree.rbt;

public class Tbter {

    public static void main(String[] args) {
        Tbter job = new Tbter();
        job.start();

    }

    public void start() {
        TreeNode n1 = new TreeNode(2);
        TreeNode n2 = new TreeNode(1);
        TreeNode n3 = new TreeNode(3);

        TreeNode n6 = new TreeNode(4);
        TreeNode n7 = new TreeNode(5);

        n1.left = n2;
        n1.right = n3;
        n2.parent = n1;
        n3.parent = n1;

        n3.left=n6;
        n3.right=n7;
        n6.parent=n3;
        n7.parent=n3;

        RbtPrinter printer = new RbtPrinter();
        printer.print(n1);

        TreeNode r1 = rotateLeft(n1, n3);
        printer.print(r1);
    }

    public TreeNode rotateLeft(TreeNode root, TreeNode x) {

        TreeNode result = null;
        TreeNode y = x.right;
        x.right = y.left;

        if (y.left != null) {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            result = y;
        } else {
            if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
        }

        y.left = x;
        x.parent = y;
        result = root;
        return result;
    }
}
