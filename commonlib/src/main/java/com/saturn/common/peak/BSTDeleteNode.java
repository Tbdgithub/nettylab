package com.saturn.common.peak;

import com.saturn.common.peak.AddTwoNumbers.AddTwoNumbers;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class BSTDeleteNode {


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {

        BSTDeleteNode job = new BSTDeleteNode();
        job.start();


    }

    private TreeNode initDelete() {
        TreeNode n1 = new TreeNode(15);
        TreeNode n2 = new TreeNode(5);
        TreeNode n3 = new TreeNode(16);
        TreeNode n4 = new TreeNode(3);
        TreeNode n5 = new TreeNode(12);

        TreeNode n7 = new TreeNode(20);
        TreeNode n10 = new TreeNode(10);
        TreeNode n11 = new TreeNode(13);
        TreeNode n14 = new TreeNode(18);
        TreeNode n15 = new TreeNode(23);

        TreeNode n20 = new TreeNode(6);
        TreeNode n41 = new TreeNode(7);

        n1.left = n2;
        n1.right = n3;

        n2.left = n4;
        n2.right = n5;

        n3.right = n7;

        n5.left = n10;
        n5.right = n11;

        n7.left = n14;
        n7.right = n15;

        n10.left = n20;
        n20.right = n41;

        return n1;
    }

    public void start() {
        TreeNode root = initDelete();

        printLevel(root);
        TreeNode deleted = deleteNode(root, 15);

        printLevel(deleted);
    }

    /**
     *1.如果root 为空，则返空
     *2.如果 key 小于root值，则到root的左子树删除;左孩子为删除后左子树根节点
     *3.如果 key 大于root值，则到root右子子树删除;右孩子为删除后右子树根节点
     *4.如果 key 等于root值，则 分情况
     *4.1.如果root 左孩子为空，则返回右孩子
     *4.2.如果root 右孩子为空，则返回左孩子
     *
     *4.左右孩子均为空,则返回空
     *5.左右孩子都不为空，则找到右孩子的最小节点,设置root 值为这个最小节点,设置root的右节点为root的删除了对应节点的子树根
     *
     */
    public TreeNode deleteNode(TreeNode root, int key) {

        if(root==null)
        {
            return null;
        }


        if(key<root.val)
        {
            root.left=deleteNode(root.left,key);
        }
        else if(key>root.val)
        {
            root.right=deleteNode(root.right,key);
        }
        else
        {
            if(root.left==null)
            {
                //root被删除了.因为上层调用的子节点为root.right ，就把root删除了
                return root.right;
            }

            if(root.right==null)
            {
                //root被删除了.因为上层调用的子树节点为root.right=xx ，就把root删除了
                return root.left;
            }

            TreeNode min=findRightSmallest(root.right);
            root.val=min.val; //替换最小值
            //关键
            //右子树最小值要删除掉
            root.right=deleteNode(root.right,min.val);
          //  System.out.println(root.right);
        }


        return root;
    }

    private TreeNode findRightSmallest(TreeNode node){
        while(node.left != null){
            node = node.left;
        }
        return node;
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

    private TreeNode deleteRoot(TreeNode root) {


        if (root.right == null) {
            return root.left;
        }

        TreeNode x = root.right; //
        while (x.left != null) {
            x = x.left;
        }

        // root 的左子树被收到x 的左子树
        x.left = root.left;
        return root.right;
    }

    private void printLevel(TreeNode root) {
        int high = 40;
        if (root == null) {
            return;
        }

        Queue<MetaItem> queue = new LinkedBlockingDeque<>();

        queue.add(new MetaItem(root, 1));

        MetaItem prev = null;
        while (queue.size() > 0) {
            MetaItem current = queue.remove();


            if (prev == null
                    || current.level != prev.level) {
                System.out.println();
                StringBuilder padLeft = new StringBuilder();


                for (int i = 0; i < high - current.level; i++) {
                    padLeft.append(" ");
                }

                System.out.print(padLeft.toString());
            }


            StringBuilder padMiddle = new StringBuilder();

            for (int i = 0; i < current.level; i++) {
                padMiddle.append(" ");
            }

            System.out.print(current.node.val + padMiddle.toString());

            prev = current;
            if (current.node.left != null) {
                queue.add(new MetaItem(current.node.left, current.level + 1));
            }

            if (current.node.right != null) {
                queue.add(new MetaItem(current.node.right, current.level + 1));
            }


        }

        System.out.println("----------------------------");

    }

    class MetaItem {
        TreeNode node;
        int level;

        public MetaItem(TreeNode node, int level) {
            this.level = level;
            this.node = node;
        }
    }
}
