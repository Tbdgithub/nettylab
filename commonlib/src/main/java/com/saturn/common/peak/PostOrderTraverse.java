package com.saturn.common.peak;

import com.google.gson.Gson;
import com.saturn.common.tree.TreeNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PostOrderTraverse {

//    class TreeNode {
//        int val;
//        TreeNode left;
//        TreeNode right;
//
//        TreeNode(int x) {
//            val = x;
//        }
//    }

    public static void main(String[] args) {


//        [1,null,2,3],

        //return [1,2,3].
//        1
//          \
//           2
//          /
//         3

        PostOrderTraverse obj = new PostOrderTraverse();
        obj.start();

    }


    public void start() {
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(7);
        n1.left = n2;
        n1.right = n3;

        n2.left = n4;
        n2.right = n5;

        n3.left = n6;
        n3.right = n7;


        // List<Integer> list = traverse_recursive(root);

        TreeNodePrinter printer=new TreeNodePrinter();
        printer.print(n1);

        List<Integer> list = traverse_stack(n1);
        System.out.println(new Gson().toJson(list));


    }

    private List<Integer> traverse_stack(TreeNode node) {

        //给每个节点三次机会
        //L(stack),R(stack),D 弹出

        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        Stack<Integer> flag = new Stack<>();

        TreeNode current = node;

        while (!stack.isEmpty() || current != null) {

            while (current != null) {
                stack.push(current);
                System.out.println("stack :" + current.val + " flag:" + 0);
                flag.push(0);
                //第一次机会
                current = current.left; //L
            }

            TreeNode parent = stack.pop();
            int access = flag.pop();
            if (access == 0) {
                //第二次机会
                flag.push(1);
                stack.push(parent);//等等再来
                System.out.println("stack :" + parent.val + " flag:" + 1);
                current = parent.right; //R
            } else {
                //第三次,最后的机会，弹出
                result.add(parent.val);//D
                System.out.println("pop :" + parent.val + " flag:" + 1);
            }
        }

        return result;
    }


    private List<Integer> traverse_recursive(TreeNode node) {
        List<Integer> path = new ArrayList<>();
        traverse_inner(path, node);
        return path;
    }

    private void traverse_inner(List<Integer> path, TreeNode node) {

        if (node == null) {
            return;
        }

        traverse_inner(path, node.left);
        traverse_inner(path, node.right);
        path.add(node.val);

    }
}
