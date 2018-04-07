package com.saturn.common.peak;

import com.saturn.common.tree.TreeNode;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class TreeNodePrinter {

    int wordWidth = 2;
    int siblingWidth = 2;


    public static void main(String[] args) {

        TreeNodePrinter job = new TreeNodePrinter();
        job.start();
    }

    public void start() {
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(7);
        TreeNode n8 = new TreeNode(8);
        TreeNode n9 = new TreeNode(9);
        TreeNode n10 = new TreeNode(10);
        TreeNode n11 = new TreeNode(11);
        TreeNode n12 = new TreeNode(12);
        TreeNode n13 = new TreeNode(13);
        TreeNode n14 = new TreeNode(14);
        TreeNode n15 = new TreeNode(15);

        n1.left = n2;
        n1.right = n3;

        n2.left = n4;
        n2.right = n5;

        n3.left = n6;
        n3.right = n7;

        n4.left = n8;
        n4.right = n9;
        n5.left = n10;
        n5.right = n11;
        n6.left = n12;
        n6.right = n13;
        n7.left = n14;
        n7.right = n15;
        //n8.left=n16;


        print(n1);
    }


    public void print(TreeNode root) {
        if (root == null) {
            return;
        }

        int height = getHeight(root);

        Stack<List<MetaItem>> layers = new Stack<List<MetaItem>>();
        Queue<MetaItem> queue = new LinkedBlockingDeque<>();
        queue.add(new MetaItem(root, 1, false));

        List<MetaItem> line = new ArrayList<>();

        int lastLevel = 1;
        while (queue.size() > 0) {
            MetaItem current = queue.remove();

            if (current.level != lastLevel) {

                lastLevel = current.level;

                layers.add(line);
                line = new ArrayList<>();
            }


            line.add(current);


            if(current.isNull)
            {

                MetaItem emptyItem = new MetaItem(null, current.level + 1, true);
                current.left = emptyItem;
                emptyItem.parent = current;

                MetaItem dataItem = new MetaItem(null, current.level + 1, true);
                current.right = dataItem;
                dataItem.parent = current;

            }
            else {

                if (current.node.left == null) {
                    MetaItem emptyItem = new MetaItem(null, current.level + 1, true);
                    current.left = emptyItem;
                    emptyItem.parent = current;
                } else {
                    MetaItem dataItem = new MetaItem(current.node.left, current.level + 1, false);
                    current.left = dataItem;
                    dataItem.parent = current;
                }

                if (current.node.right == null) {
                    MetaItem emptyItem = new MetaItem(null, current.level + 1, true);
                    current.right = emptyItem;
                    emptyItem.parent = current;
                } else {
                    MetaItem dataItem = new MetaItem(current.node.right, current.level + 1, false);
                    current.right = dataItem;
                    dataItem.parent = current;
                }
            }


            if (current.level < height) {

                 queue.add(current.left);
                 queue.add(current.right);

            }

        }

        layers.add(line);

        //////////////////////////
        calcIndex(layers);

        ///////////


    }


    private void calcIndex(Stack<List<MetaItem>> layers) {


        if (layers.size() == 0) {
            return;
        }

        Stack<List<MetaItem>> printStack = new Stack<>();

        List<MetaItem> bottomLine = layers.pop();
        printStack.push(bottomLine);

        int lastIndex = 0;
        for (MetaItem col : bottomLine) {

            col.startIndex = lastIndex;
            lastIndex = lastIndex + wordWidth + siblingWidth;

        }


        while (layers.size() > 0) {
            System.out.println();
            List<MetaItem> popLine = layers.pop();
            printStack.push(popLine);


            for (MetaItem col : popLine) {

                col.startIndex = (col.left.startIndex + col.right.startIndex) / 2;

            }
        }

        ///////////////////////////////////
        // System.out.println("\r\n------------------start------------");

        while (printStack.size() > 0) {
            List<MetaItem> item0 = printStack.pop();

            printValue(wordWidth, siblingWidth, item0);
            printConnectionLine(wordWidth, siblingWidth, item0);


        }


    }

    private void printConnectionLine(int wordWidth, int siblingWidth, List<MetaItem> item0) {
        int currentIndex = 0;
        for (MetaItem col : item0) {

            while (currentIndex < col.startIndex) {
                System.out.print(" ");
                currentIndex++;
            }

            String value = "";
            if (!col.isNull) {

                if (col.left != null && !col.left.isNull) {
                    value += "/";
                } else {
                    value += " ";
                }

                if (col.right != null && !col.right.isNull) {
                    value += "\\";
                } else {
                    value += " ";
                }

                System.out.print(fillWidth(value, wordWidth, ' '));

            } else {
                System.out.print(fillWidth(value, wordWidth, ' '));
            }

            currentIndex += wordWidth;

            for (int i = 0; i < siblingWidth; i++) {
                System.out.print(" ");
            }

            currentIndex += wordWidth;

        }

        System.out.println();
    }

    private void printValue(int wordWidth, int siblingWidth, List<MetaItem> item0) {
        int currentIndex = 0;
        for (MetaItem col : item0) {

            while (currentIndex < col.startIndex) {
                System.out.print(" ");
                currentIndex++;
            }

            String value = " ";
            if (!col.isNull) {
                value = String.valueOf(col.node.val);
                System.out.print(fillWidth(value, wordWidth, ' '));

            } else {
                System.out.print(fillWidth(value, wordWidth, ' '));
            }

            currentIndex += wordWidth;

            for (int i = 0; i < siblingWidth; i++) {
                System.out.print(" ");
            }

            currentIndex += wordWidth;

        }

        System.out.println();
    }

    private String fillWidth(String item, int padLen, char padChar) {


        int padLeft = padLen - item.length() > 0 ? padLen - item.length() : 0;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < padLeft; i++) {
            sb.append(padChar);
        }

        sb.append(item);

        return sb.toString();
    }

    public int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Math.max(1 + getHeight(root.left), 1 + getHeight(root.right));
    }


    class MetaItem {
        TreeNode node;
        int level;
        boolean isNull;
        int startIndex;

        MetaItem left;
        MetaItem right;
        MetaItem parent;


        public MetaItem(TreeNode node, int level, boolean isNull) {
            this.level = level;
            this.node = node;
            this.isNull = isNull;
        }

    }

}
