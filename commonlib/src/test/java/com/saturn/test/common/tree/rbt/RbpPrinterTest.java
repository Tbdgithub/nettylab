package com.saturn.test.common.tree.rbt;

import com.saturn.common.tree.rbt.RbtPrinter;
import com.saturn.common.tree.rbt.RbtTree;
import com.saturn.common.tree.rbt.TreeNode;
import org.junit.Test;

public class RbpPrinterTest {

    @Test
    public void test1()
    {

        RbtTree tree=buildTree1();
        RbtPrinter printer=new RbtPrinter(true);
        printer.printTree(tree);
        System.out.println("-------------------------------");

        printer=new RbtPrinter(false);
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

}
