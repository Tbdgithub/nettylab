package com.saturn.common.peak;

import java.util.List;

public class PathSum {

    // root-to-leaf path 5->4->11->2 which sum is 22.
          /*  5
             / \
            4   8
           /   / \
          11  13  4
         /  \      \
        7    2      1

		*/

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        PathSum job = new PathSum();
        job.start();

    }

    public void start() {
        TreeNode n1 = new TreeNode(5);
        TreeNode n2 = new TreeNode(4);
        TreeNode n3 = new TreeNode(8);
        TreeNode n4 = new TreeNode(11);

        n1.left = n2;
        n1.right = n3;
        n2.left = n4;

        boolean flag = hasPathSum(n1, 22);
        System.out.println(flag);

    }

    public boolean hasPathSum(TreeNode root, int sum) {


        return hasPathSum_recursive(root, 0, sum);
    }

    public boolean hasPathSum_recursive(TreeNode root, int add, int target) {


        //  boolean flag = hasPathSum_recursive(root.left, );
        // flag = hasPathSum(paths, parentPath, root.right);
        return false;
    }


}
