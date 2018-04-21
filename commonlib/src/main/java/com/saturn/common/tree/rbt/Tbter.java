package com.saturn.common.tree.rbt;

import com.saturn.common.peak.BSTBasic;

public class Tbter {

    public static void main(String[] args) {
        Tbter job = new Tbter();
        // job.start();

        //job.test1();
        // job.test2();

        //job.insertTest();
        job.delTest();
    }

    public void insertTest() {


        RbtTree tree = buildTree1();

        printer.printTree(tree);


    }

    private RbtTree buildTree1() {
        RbtTree tree = new RbtTree();

        TreeNode newNode = new TreeNode(41);
        tree.insert(newNode);

        newNode = new TreeNode(38);
        tree.insert(newNode);

        newNode = new TreeNode(31);
        tree.insert(newNode);

        newNode = new TreeNode(12);
        tree.insert(newNode);


        newNode = new TreeNode(19);
        tree.insert(newNode);

        newNode = new TreeNode(8);
        tree.insert(newNode);

        return tree;
    }

    public void delTest() {

        RbtTree tree = buildTree1();
        System.out.println("---------- before delete-------------------");
        printer.printTree(tree);

//        TreeNode node=tree.find(8);
//        tree.deleteNode(node);
//        System.out.println("---------- after delete 8-------------------");
//        printer.printTree(tree);
//
//        node=tree.find(12);
//        tree.deleteNode(node);
//        System.out.println("---------- after delete 12 -------------------");
//        printer.printTree(tree);
//
//        node=tree.find(19);
//        tree.deleteNode(node);
//        System.out.println("---------- after delete 19 -------------------");
//        printer.printTree(tree);
//
//        node=tree.find(31);
//        tree.deleteNode(node);
//        System.out.println("---------- after delete 31 -------------------");
//        printer.printTree(tree);
//
//        node=tree.find(38);
//        tree.deleteNode(node);
//        System.out.println("---------- after delete 38 -------------------");
//        printer.printTree(tree);

        TreeNode node=tree.find(41);
        tree.deleteNode(node);
        System.out.println("---------- after delete 41 -------------------");
        printer.printTree(tree);

    }


    public void test2() {


        TreeNode n1 = genEmptyNode();

        printer.print(n1);


        TreeNode newNode = new TreeNode(41);
        TreeNode afterInsertedRoot = insert(n1, newNode);
        // printer.print(afterInsertedRoot);

        System.out.println("----------------");
        newNode = new TreeNode(38);
        afterInsertedRoot = insert(afterInsertedRoot, newNode);
        printer.print(afterInsertedRoot);
//
        System.out.println("-------insert 31 ---------");
        newNode = new TreeNode(31);
        afterInsertedRoot = insert(afterInsertedRoot, newNode);
        printer.print(afterInsertedRoot);
//
        System.out.println("-----insert 12 -----------");
        newNode = new TreeNode(12);
        afterInsertedRoot = insert(afterInsertedRoot, newNode);
        printer.print(afterInsertedRoot);
//
        System.out.println("------insert 19----------");
        newNode = new TreeNode(19);
        afterInsertedRoot = insert(afterInsertedRoot, newNode);
        printer.print(afterInsertedRoot);
//
        System.out.println("----insert 8------------");
        newNode = new TreeNode(8);
        afterInsertedRoot = insert(afterInsertedRoot, newNode);
        printer.print(afterInsertedRoot);

//        System.out.println("----insert xx------------");
//        newNode = new TreeNode(69);
//        afterInsertedRoot = insert(afterInsertedRoot, newNode);
//        printer.print(afterInsertedRoot);
//
//        System.out.println("----insert xx------------");
//        newNode = new TreeNode(80);
//        afterInsertedRoot = insert(afterInsertedRoot, newNode);
//        printer.print(afterInsertedRoot);
//
//        System.out.println("----insert xx------------");
//        newNode = new TreeNode(83);
//        afterInsertedRoot = insert(afterInsertedRoot, newNode);
//        printer.print(afterInsertedRoot);
    }

    RbtPrinter printer = new RbtPrinter();

    public void test1() {
        //TreeNode n1 = genRbt_before();
        //TreeNode n1 = genOneNode();
        TreeNode n1 = genEmptyNode();

        printer.print(n1);

        TreeNode newNode = new TreeNode(4);
        TreeNode afterInsertedRoot = insert(n1, newNode);

        System.out.println("-----------after inserted------------");
        printer.print(afterInsertedRoot);

        System.out.println("-----------end------------");

    }

    public void start() {


        TreeNode n1 = genTree();
        RbtPrinter printer = new RbtPrinter();
        printer.print(n1);

        TreeNode n3 = search_iter(n1, 10);

        TreeNode r1 = rotateLeft(n1, n3);
        printer.print(r1);

        TreeNode n7 = search_iter(n1, 12);
        TreeNode r2 = rotateRight(n1, n7);
        printer.print(r2);
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


    private TreeNode genEmptyNode() {
        return NullNode.Instance;
    }

    private TreeNode genOneNode() {
        TreeNode n1 = new TreeNode(11, NodeColor.Red);
        return n1;
    }

    private TreeNode genRbt_before() {
        TreeNode n1 = new TreeNode(11, NodeColor.Black);

        TreeNode n2 = new TreeNode(2, NodeColor.Red);
        TreeNode n3 = new TreeNode(14, NodeColor.Black);
        n1.addLeftChild(n2);
        n1.addRightChild(n3);

        TreeNode n4 = new TreeNode(1, NodeColor.Black);
        TreeNode n5 = new TreeNode(7, NodeColor.Black);

        n2.addLeftChild(n4);
        n2.addRightChild(n5);


        TreeNode n7 = new TreeNode(15, NodeColor.Red);
        n3.addRightChild(n7);


        TreeNode n10 = new TreeNode(5, NodeColor.Red);
        TreeNode n11 = new TreeNode(8, NodeColor.Red);
        n5.addLeftChild(n10);
        n5.addRightChild(n11);

        //  TreeNode n20 = new TreeNode(4, NodeColor.Red);
        //n10.addLeftChild(n20);

        return n1;


    }

    private TreeNode insert(TreeNode root, TreeNode z) {
        TreeNode resultRoot = root;

        if (z == null) {
            return resultRoot;
        }

        TreeNode y = NullNode.Instance;
        TreeNode x = root;
        while (x != NullNode.Instance) {
            y = x;
            if (z.val < x.val) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.parent = y;

        if (y == NullNode.Instance) {
            //root 为空，z为新根节点
            resultRoot = z;
        } else {
            if (z.val < y.val) {
                y.left = z;
            } else {
                y.right = z;
            }
        }

        //上色
        z.left = NullNode.Instance;
        z.right = NullNode.Instance;
        z.color = NodeColor.Red;

        resultRoot = insert_fixup(resultRoot, z);
        return resultRoot;
    }

    private TreeNode insert_fixup(TreeNode root, TreeNode z) {

        TreeNode resultRoot = root;
//        if (z.parent == null) {
//
//            return resultRoot;
//        }

        while (z.parent.color == NodeColor.Red) {
            if (z.parent == NullNode.Instance) {
                break;
            }

            if (z.parent.parent == NullNode.Instance) {
                break;
            }

            if (z.parent == z.parent.parent.left) {
                TreeNode y = z.parent.parent.right;

                if (y.color == NodeColor.Red) {
                    z.parent.color = NodeColor.Black;
                    y.color = NodeColor.Black;
                    z.parent.parent.color = NodeColor.Red;
                    z = z.parent.parent;

                    System.out.println("case 1");
                    printer.print(resultRoot);
                } else {
                    //case 2,case 3
                    if (z == z.parent.right) {
                        //转为case 3
                        z = z.parent;
                        TreeNode afterleft = rotateLeft(resultRoot, z);
                        resultRoot = afterleft;
                        System.out.println("case 2");
                        printer.print(afterleft);
                    }

                    System.out.println("case 3");
                    //已转为case 3
                    z.parent.color = NodeColor.Black;
                    z.parent.parent.color = NodeColor.Red;
                    TreeNode afterRoot = rotateRight(resultRoot, z.parent.parent);
                    printer.print(afterRoot);
                    resultRoot = afterRoot;

                }

            } else {
                //右枝

                TreeNode y = z.parent.parent.left;
                if (y.color == NodeColor.Red) {
                    //case 1
                    z.parent.color = NodeColor.Black;
                    y.color = NodeColor.Black;
                    z.parent.parent.color = NodeColor.Red;
                    z = z.parent.parent;
                    System.out.println("right case 1");
                    printer.print(resultRoot);
                } else {
                    //case 2
                    if (z == z.parent.left) {
                        z = z.parent;
                        resultRoot = rotateRight(resultRoot, z);
                        System.out.println("right case 2");
                        printer.print(resultRoot);
                    }

                    System.out.println("right case 3");
                    z.parent.color = NodeColor.Black;
                    z.parent.parent.color = NodeColor.Red;
                    resultRoot = rotateLeft(resultRoot, z.parent.parent);
                    printer.print(resultRoot);
                }
            }
        }

        //root.color = NodeColor.Black;
        resultRoot.color = NodeColor.Black;

        return resultRoot;


    }


    private TreeNode genTree() {
        TreeNode n1 = new TreeNode(7);
        TreeNode n2 = new TreeNode(3);
        TreeNode n3 = new TreeNode(10);

        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(12);

        TreeNode n14 = new TreeNode(11);


        n1.left = n2;
        n1.right = n3;
        n2.parent = n1;
        n3.parent = n1;

        n3.left = n6;
        n3.right = n7;
        n6.parent = n3;
        n7.parent = n3;

        n7.left = n14;
        n14.parent = n7;
        return n1;

    }


    public TreeNode rotateRight(TreeNode root, TreeNode y) {

        TreeNode result = null;
        TreeNode x = y.left;
        //if (x.right != null)
        if (x.right != NullNode.Instance) {
            x.right.parent = y;
        }

        y.left = x.right;
        ///////////
        x.parent = y.parent;

        //if (y.parent == null)
        if (y.parent == NullNode.Instance) {
            result = x;
            x.right = y;
            y.parent = x;
        } else {

            if (y == y.parent.left) {
                y.parent.left = x;
            } else {
                y.parent.right = x;
            }

            x.right = y;
            y.parent = x;
            result = root;

        }
        ///////////////////

        return result;
    }

    public TreeNode rotateLeft(TreeNode root, TreeNode x) {

        TreeNode result = null;
        TreeNode y = x.right;

        // beta 和 x 关系
        //if (y.left != null)
        if (y.left != NullNode.Instance) {
            y.left.parent = x;
        }

        x.right = y.left;
        /////////
        // beta 和 x parent 关系
        //if (x.parent == null)
        if (x.parent == NullNode.Instance) {
            result = y;
        } else {
            if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }

        }

        y.parent = x.parent;

        ////////
        // x ,y关系
        y.left = x;
        x.parent = y;

        result = root;
        return result;


//        //1.x- beta  可交换
//        //2.y - x.parent 可交换
//        //3.x.y 可交换
//        TreeNode result = null;
//        TreeNode y = x.right;
//
//        //beta -> x
//        if (y.left != null) {
//            y.left.parent = x;
//        }
//
//        //x right ->beta
//        x.right = y.left;//  beta
//
//
//        //beta  处理完
//
//        // y-> x parent
//
//        if (x.parent == null) {
//            result = y;
//        } else {
//
//            // x parent ->y
//            if (x == x.parent.left) {
//                x.parent.left = y;
//            } else {
//                x.parent.right = y;
//            }
//        }
//
//        y.parent = x.parent;
//
//        //y ->left x
//        y.left = x;
//
//        // x parent ->y
//        x.parent = y;
//
//        result = root;
//        return result;
    }
}
