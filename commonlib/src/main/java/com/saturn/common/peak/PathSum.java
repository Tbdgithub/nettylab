package com.saturn.common.peak;

import java.util.ArrayList;
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
        TreeNode n1 = new TreeNode(0);
        TreeNode n2 = new TreeNode(1);
        TreeNode n3 = new TreeNode(1);
        //TreeNode n11 = new TreeNode(2);

        n1.left = n2;
        n1.right = n3;
        //n5.right = n11;

        //boolean flag = hasPathSum(n1, 22);
        //boolean flag = hasPathSum(n1, 1);
        boolean flag = hasPathSum1(n1, 1);
        System.out.println(flag);

    }

    public boolean hasPathSum(TreeNode root, int sum) {

        //  List<Integer> paths = new ArrayList<>();
        TreeNode dummy = new TreeNode(0);

        hasPathSum_recursive(dummy, root, null, sum);

        return dummy.val > 0;
    }

    public boolean hasPathSum1(TreeNode root, int sum) {

        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null
                && sum - root.val == 0) {
            return true;
        }

        //关键在sum-root.val
        return hasPathSum1(root.right, sum - root.val)
                ||
                hasPathSum1(root.left, sum - root.val);


    }


    public void hasPathSum_recursive(TreeNode find, TreeNode root, Integer add, int target) {

        if (find.val > 0) {
            return;
        }

        if (root == null) {
            return;
        }

        if (add == null) {
            add = root.val;
        } else {
            add = add + root.val;
        }

        if (root.left == null && root.right == null) {
            if (add == target) {
                find.val = 1;
            }
        }

        hasPathSum_recursive(find, root.left, add, target);
        hasPathSum_recursive(find, root.right, add, target);

    }
}
