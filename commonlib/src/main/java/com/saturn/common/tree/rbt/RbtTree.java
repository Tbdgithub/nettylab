package com.saturn.common.tree.rbt;

public class RbtTree {

    public TreeNode root = NullNode.Instance;


    public TreeNode find(int key) {

        TreeNode current = this.root;
        while (current != NullNode.Instance && key != current.val) {
            if (key < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return current;

    }

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

    public TreeNode deleteNode(TreeNode z) {

        if (z == NullNode.Instance) {
            return z;
        }

        TreeNode resultRoot = this.root;

        TreeNode y = NullNode.Instance;
        TreeNode x = NullNode.Instance;


        if (z.left == NullNode.Instance || z.right == NullNode.Instance) {
            y = z;
        } else {
            y = successor(z);
        }

        if (y.left != NullNode.Instance) {
            x = y.left;
        } else {
            x = y.right;
        }

        x.parent = y.parent;

        if (y.parent == NullNode.Instance) {

            this.root = x;
        } else {
            if (y == y.parent.left) {
                y.parent.left = x;
            } else {
                y.parent.right = x;
            }
        }

        if (y != z) {
            int temp = y.val;
            y.val = z.val;
            z.val = temp;

        }

        if (y.color == NodeColor.Black) {
            deleteFixup(this, x);
        }

        return y;

    }

    public void deleteFixup(RbtTree tree, TreeNode x) {

//        if(x==NullNode.Instance)
//        {
//            return;
//        }

        while (x != tree.root && x.color == NodeColor.Black) {
            if (x == x.parent.left) {
                TreeNode w = x.parent.right;

                if (w.color == NodeColor.Red) {//case 1
                    w.color = NodeColor.Black;
                    x.parent.color = NodeColor.Red;
                    rotateLeft(tree, x.parent);
                    w = x.parent.right; //
                }

                if (w.left.color == NodeColor.Black && w.right.color == NodeColor.Black) {
                    //case 2
                    w.color = NodeColor.Red;
                    x = x.parent;
                    //退出循环
                } else if (w.right.color == NodeColor.Black) {
                    //case 3
                    w.left.color = NodeColor.Black;
                    w.color = NodeColor.Red;
                    rotateRight(tree, w);
                    w = x.parent.right;
                }


                w.color = x.parent.color;
                x.parent.color = NodeColor.Black;
                rotateLeft(tree, x.parent);
                x = tree.root;


            } else {
                //right part

                System.out.println("right part");
                TreeNode w = x.parent.left;

                if (w.color == NodeColor.Red) {
                    //case 1
                    w.color = NodeColor.Black;
                    x.parent.color = NodeColor.Red;
                    rotateRight(tree, x.parent);
                    w = x.parent.left; //
                }

                if (w.right.color == NodeColor.Black && w.left.color == NodeColor.Black) {
                    //case 2
                    w.color = NodeColor.Red;
                    x = x.parent;
                    //退出循环
                } else if (w.left.color == NodeColor.Black) {
                    //case 3
                    w.left.color = NodeColor.Black;
                    w.color = NodeColor.Red;
                    rotateLeft(tree, w);
                    w = x.parent.left;
                }

                w.color = x.parent.color;
                x.parent.color = NodeColor.Black;
                rotateRight(tree, x.parent);
                x = tree.root;


            }
        }

        x.color = NodeColor.Black;

    }

    public TreeNode successor(TreeNode x) {
        if (x == NullNode.Instance) {
            return NullNode.Instance;
        }

        if (x.right != NullNode.Instance) {
            return getMinSubTreeHead(x.right);
        }

        //没有右子树
        TreeNode y = x.parent;
        while (y != NullNode.Instance && y.right == x) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    private TreeNode getMinSubTreeHead(TreeNode x) {
//        if (x == NullNode.Instance) {
//            return NullNode.Instance;
//        }

        TreeNode current = x;
        while (current.left != NullNode.Instance) {
            current = current.left;
        }

        return current;
    }


}
