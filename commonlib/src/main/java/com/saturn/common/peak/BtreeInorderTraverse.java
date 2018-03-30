package com.saturn.common.peak;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BtreeInorderTraverse {

    public static void main(String[] args) {


//        [1,null,2,3],

        //[1,3,2].
        BtreeInorderTraverse obj = new BtreeInorderTraverse();
        obj.start();

    }

    public void start() {
        TreeNode root = new TreeNode(1);
        TreeNode n1 = new TreeNode(2);
        TreeNode n2 = new TreeNode(3);
        root.right = n1;
        n1.left = n2;

        //  List<Integer> list = inorderTraversal(root);
        List<Integer> list = inorderTraversal_stack(root);
        System.out.println(new Gson().toJson(list));

    }


    public List<Integer> inorderTraversal_stack(TreeNode root) {

        List<Integer> result = new ArrayList<>();

        Stack<TreeNode> stack = new Stack<>();

        TreeNode current = root;
        while (current != null || !stack.isEmpty()) {

            //有左子，则把当前节点入stack,
            //就是说:当前节点先放stack这,把左子树看完，再来看你
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            //到这里时,左子树到头了,
            //沿stack 返回
            TreeNode finished = stack.pop();
            result.add(finished.val);
            //finished 的左子树和当前节点已走完

            //该去右边了
            current=finished.right;
        }

        return result;

    }

    public List<Integer> inorderTraversal(TreeNode root) {

        List<Integer> result = new ArrayList<>();
        inorderTraversalInner(result, root);
        return result;
    }

    private void inorderTraversalInner(List<Integer> path, TreeNode node) {

        //递归退出条件
        if (node == null) {
            return;
        }

        //先左
        inorderTraversalInner(path, node.left);
        //再中
        path.add(node.val);
        //再右
        inorderTraversalInner(path, node.right);

    }


    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}
