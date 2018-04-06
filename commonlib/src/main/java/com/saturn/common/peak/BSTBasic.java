package com.saturn.common.peak;

public class BSTBasic {

    public static void main(String[] args) {

        BSTBasic job = new BSTBasic();
        job.start();


    }

    private void start() {

        TreeNode root = init();
        //TreeNode find= job.search_recursive(root,13);
        TreeNode find = search_iter(root, 15);
        System.out.println(find != null ? find.val : "null");
        //TreeNode min= job.getMinSubTreeHead(find);
        //System.out.println(min!=null ?min.val:"null");

        //  TreeNode max= job.getMaxSubTreeHead(find);
        //System.out.println(max!=null ?max.val:"null");
        // TreeNode succesor=job.getSuccessor(find);
        // System.out.println(succesor!=null ?succesor.val:"null");

        //TreeNode presessor = job.getPresessor(find);
        //System.out.println(presessor != null ? presessor.val : "null");
        TreeNode newNode = new TreeNode(8);
        TreeNode newRoot = insert(root, newNode);
        System.out.println(newRoot);
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode parent;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode deleteNode(TreeNode root, int key) {

        return null;
    }

    private TreeNode init() {
        TreeNode n1 = new TreeNode(15);
        TreeNode n2 = new TreeNode(6);
        TreeNode n3 = new TreeNode(18);
        TreeNode n4 = new TreeNode(3);
        TreeNode n5 = new TreeNode(7);

        TreeNode n6 = new TreeNode(17);
        TreeNode n7 = new TreeNode(20);
        TreeNode n8 = new TreeNode(2);
        TreeNode n9 = new TreeNode(4);
        TreeNode n11 = new TreeNode(13);

        TreeNode n22 = new TreeNode(9);

        n1.left = n2;
        n1.right = n3;

        n2.parent = n1;
        n3.parent = n1;

        n2.left = n4;
        n2.right = n5;
        n4.parent = n2;
        n5.parent = n2;

        n3.left = n6;
        n3.right = n7;
        n6.parent = n3;
        n7.parent = n3;


        n4.left = n8;
        n4.right = n9;
        n8.parent = n4;
        n9.parent = n4;

        n5.right = n11;
        n11.parent = n5;


        n11.left = n22;
        n22.parent = n11;

        return n1;
    }

    TreeNode search_recursive(TreeNode x, int k) {

        if (x == null) {
            return x;
        }

        if (k == x.val) {
            return x;
        }

        if (k < x.val) {
            return search_recursive(x.left, k);
        } else {
            return search_recursive(x.right, k);
        }

    }

    TreeNode search_iter(TreeNode x, int key) {
        TreeNode current = x;
        while (x != null && key != current.val) {
            if (key < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return current;

    }

    TreeNode getMinSubTreeHead(TreeNode x) {
        if (x == null) {
            return null;
        }

        TreeNode current = x;
        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    TreeNode getMaxSubTreeHead(TreeNode x) {
        if (x == null) {
            return null;
        }

        TreeNode current = x;
        while (current.right != null) {
            current = current.right;
        }

        return current;
    }

    private TreeNode getSuccessor(TreeNode x) {
        if (x == null) {
            return null;
        }

        if (x.right != null) {
            return getMinSubTreeHead(x.right);
        }

        //没有右子树
        TreeNode y = x.parent;
        while (y != null && y.right == x) {
            x = y;
            y = y.parent;
        }

        return y;

    }

    private TreeNode getPresessor(TreeNode x) {
        if (x == null) {
            return null;
        }

        if (x.left != null) {
            return getMaxSubTreeHead(x.left);
        }

        //没有左子树
        TreeNode y = x.parent;

        while (y != null && y.left == x) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    private TreeNode insert(TreeNode root, TreeNode newNode) {
        TreeNode newRoot = null;

        if (newNode == null) {
            return null;
        }

        TreeNode y = null;
        TreeNode x = root;
        while (x != null) {
            y = x;

            if (newNode.val < x.val) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        newNode.parent = y;

        if (y == null) {
            //return newNode;
            newRoot = newNode;
        } else {
            if (newNode.val < y.val) {
                y.left = newNode;
            } else {
                y.right = newNode;
            }

            newRoot = root;
        }

        return newRoot;
    }

    private void deleleNode(TreeNode root, int key) {

    }

    private void printLevel(TreeNode root) {

    }
}
