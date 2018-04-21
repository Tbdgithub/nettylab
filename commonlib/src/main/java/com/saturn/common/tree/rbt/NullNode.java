package com.saturn.common.tree.rbt;

public class NullNode extends TreeNode {

    public NullNode() {
        super();
        this.color = NodeColor.Black;
    }

    public static NullNode Instance = new NullNode();

//    @Override
//    public boolean equals(Object obj) {
//
////        if (obj instanceof NullNode) {
////            return false;
////        }
////
////        return (this == obj);
//
//        return false;
//    }
}
