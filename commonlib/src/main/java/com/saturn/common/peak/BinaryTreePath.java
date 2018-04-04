package com.saturn.common.peak;

import java.util.*;

public class BinaryTreePath {
    public static void main(String[] args) {
        BinaryTreePath job = new BinaryTreePath();
        job.start();

    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 1
     * /    \
     * 2     3
     * \
     * 5
     */


    //["1->2->5", "1->3"]
    public void start() {

        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n5 = new TreeNode(5);

        n1.left = n2;
        n1.right = n3;
        n2.right = n5;

        List<String> paths = binaryTreePaths(n1);

        for (String item : paths) {
            System.out.println(item);
        }

    }

    public List<String> binaryTreePaths(TreeNode root) {

        //DLR
        //
        //1.List 装每一个path,当进行到leaf 节点时add
        //2.parentPath 每次调用时累加parentPath=parentPath+"->"+current val;

        List<String> paths =  traverse_stack(root);
        return paths;

    }

    private List<String> traverse_stack(TreeNode node) {

        List<String> result = new ArrayList<>();

        //path helper
        Stack<TreeNode> pathStack = new Stack<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = node;

        while (!stack.isEmpty() || current != null) {

            while (current != null) {

                pathStack.push(current);
                //System.out.println("push:" + current.val);
                stack.push(current);
                current = current.left; //L
                //一路向左
            }


            TreeNode parent = stack.pop();//找到回来的路
          //  System.out.println("pop:" + parent.val);

            //直到parent
            //找到helper stack 头与弹出的相同的节点
            TreeNode peek = null;
            do {
                peek = pathStack.peek();
                if (!peek.equals(parent)) {
                    pathStack.pop();
                } else {
                    break;
                }

            } while (peek != null);

            if (parent.left == null && parent.right == null) {

                StringBuilder sb = new StringBuilder();

                Iterator<TreeNode> iterator = pathStack.iterator();
                while (iterator.hasNext()) {
                    if (sb.length() == 0) {
                        sb.append(iterator.next().val);
                    } else {
                        sb.append("->" + iterator.next().val);
                    }
                }

                result.add(sb.toString());
            }

            current = parent.right; //R


        }

        return result;

    }


    private void printPath(Stack<TreeNode> nodes) {

        Iterator<TreeNode> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next().val + "->");
        }
        System.out.println();
    }

    private void walk_recursive(List<String> paths, String parentPath, TreeNode root) {
        if (root == null) {
            return;
        }

        //关键1
        if (parentPath != null) {
            parentPath = parentPath + "->" + root.val;
        } else {
            parentPath = String.valueOf(root.val);
        }

        //关键2 ，叶节点时放到path
        if (root.left == null && root.right == null) {
            paths.add(parentPath);
        }

        walk_recursive(paths, parentPath, root.left);
        walk_recursive(paths, parentPath, root.right);

    }

}
