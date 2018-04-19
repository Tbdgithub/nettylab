package com.saturn.common.tree.rbt;

public class TreeNode {

    public int val;
    public TreeNode left=NullNode.Instance;
    public TreeNode right=NullNode.Instance;
    public TreeNode parent=NullNode.Instance;
    public NodeColor color;

    public TreeNode()
    {

    }

    public TreeNode(int x) {
        val = x;

    }

    public TreeNode(int val,NodeColor color)
    {
        this.val=val;
        this.color=color;

    }

    public void addLeftChild(TreeNode newNode)
    {
        this.left=newNode;
        newNode.parent=this;
    }

    public void addRightChild(TreeNode newNode)
    {
        this.right=newNode;
        newNode.parent=this;
    }

}


