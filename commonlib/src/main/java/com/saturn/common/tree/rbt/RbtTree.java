package com.saturn.common.tree.rbt;

public class RbtTree {

    public TreeNode root=NullNode.Instance;


    public void insert(TreeNode z) {

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

            this.root = z;
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

        insert_fixup(this, z);

    }

    private void insert_fixup(RbtTree tree, TreeNode z) {


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
                    // printer.print(resultRoot);
                } else {
                    //case 2,case 3
                    if (z == z.parent.right) {
                        //转为case 3
                        z = z.parent;
                        rotateLeft(tree, z);
                        // resultRoot = afterleft;
                        System.out.println("case 2");
                        // printer.print(afterleft);
                    }

                    System.out.println("case 3");
                    //已转为case 3
                    z.parent.color = NodeColor.Black;
                    z.parent.parent.color = NodeColor.Red;
                    rotateRight(tree, z.parent.parent);
                    // printer.print(afterRoot);
                    //resultRoot = afterRoot;

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
                    //printer.print(resultRoot);
                } else {
                    //case 2
                    if (z == z.parent.left) {
                        z = z.parent;
                        rotateRight(tree, z);
                        System.out.println("right case 2");
                        // printer.print(resultRoot);
                    }

                    System.out.println("right case 3");
                    z.parent.color = NodeColor.Black;
                    z.parent.parent.color = NodeColor.Red;
                    rotateLeft(tree, z.parent.parent);
                    // printer.print(resultRoot);
                }
            }
        }

        //root.color = NodeColor.Black;

        tree.root.color = NodeColor.Black;

        //return resultRoot;


    }

    public void rotateRight(RbtTree tree, TreeNode y) {

        //TreeNode result = null;

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

            //result = x;
            tree.root = x;
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
            //result = root;
            //tree.root=

        }
        ///////////////////

        // return result;
    }

    public void rotateLeft(RbtTree tree, TreeNode x) {

        // TreeNode result = null;

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

            //result = y;
            tree.root = y;
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

        //result = root;
        //return result;


    }


}
