package com.saturn.common.peak;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PreOrderTraverse {

    public static void main(String[] args) {


//        [1,null,2,3],

        //return [1,2,3].
//        1
//          \
//           2
//          /
//         3

        PreOrderTraverse obj = new PreOrderTraverse();
        obj.start();

    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public void start() {
        TreeNode root = new TreeNode(1);
        TreeNode n1 = new TreeNode(2);
        TreeNode n2 = new TreeNode(3);
        root.right = n1;
        n1.left = n2;


        //  List<Integer> list = traverse_recursive(root);
        List<Integer> list = traverse_stack(root);
         System.out.println(new Gson().toJson(list));


    }


    private List<Integer> traverse_stack(TreeNode node) {

        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        TreeNode current = node;

        while (!stack.isEmpty() || current != null) {

            while (current != null) {
                result.add(current.val);//D;
                stack.push(current);
                current = current.left; //L
                //一路向左
            }

            TreeNode parent = stack.pop();//找到回来的路

            current = parent.right; //R
            //转向右边

        }

        return result;

    }

    private List<Integer> traverse_recursive(TreeNode node) {

        List<Integer> path = new ArrayList<>();
        traverse_recursive_inner(path, node);
        return path;
    }

    private void traverse_recursive_inner(List<Integer> path, TreeNode node) {
        if (node == null) {
            return;
        }

        path.add(node.val);
        traverse_recursive_inner(path, node.left);
        traverse_recursive_inner(path, node.right);
    }


}
