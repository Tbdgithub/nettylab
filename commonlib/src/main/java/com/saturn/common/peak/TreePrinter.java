package com.saturn.common.peak;

import com.google.gson.Gson;

import java.io.File;
import java.util.*;

public class TreePrinter {


    //print dir like:
    //
    //├ ─dir1
    //└─dir2

    /**
     * │  f1.txt
     * │
     * ├─dir1
     * │      f11.txt
     * │
     * └─dir2
     * f21.txt
     * f22.txt
     *
     * @param args
     */

    //dir  4个tab
    //file 2个tab
    public static void main(String[] args) {


        TreePrinter obj = new TreePrinter();
        obj.start();

    }


    public void start() {


        TreeNode root = buildTreeByDir(
                ///Program Files/JetBrains/CLion 2017.2.3/bin/cmake_cygwin
                new File("/sz"));

        List<TreeNode> path = traverse_recursive(root);

        print(path,true);



    }

    public TreeNode buildTreeByDir(File dir) {
        TreeNode root = new TreeNode("dummy");

        buildTreeByDirInner(root, dir);

        TreeNode result = root.childNodes.get(0);
        result.parent = null;
        return result;

    }

    private void buildTreeByDirInner(TreeNode parent, File dir) {

        TreeNode newNode = new TreeNode(dir.getName());

        if (dir.isDirectory()) {
            newNode.isDir = true;
        }

        parent.add(newNode);

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();

            for (File item : files) {
                buildTreeByDirInner(newNode, item);
            }

        }
    }


    public void printSimple(List<TreeNode> nodes) {

        for (TreeNode item : nodes) {

            Stack<TreeNode> stack = new Stack<>();

            TreeNode current = item;

            while (current != null) {


                stack.push(current);
                current = current.parent;
            }

            while (!stack.isEmpty()) {
                System.out.print("----" + stack.pop().name);
            }

            System.out.println();
        }


    }



    public void print(List<TreeNode> nodes ,boolean showFile) {

        //分为a+b+c
        //连接线 + 双亲线 +节点名称
        //
        //1.未出现过的path
        //1.1没有有双亲的，没有连接线，没有双亲线
        //1.2有双亲的

        //已出现过的路径，合并连接线
        Set<TreeNode> beforeSet = new HashSet<>();

        //paths
        for (TreeNode item : nodes) {

            if(!showFile) {
                if (!item.isDir) {
                    continue;
                }
            }

            Stack<TreeNode> stack = new Stack<>();
            TreeNode current = item;
            while (current != null) {
                stack.push(current);
                current = current.parent;

            }

            Deque<String> lines = new ArrayDeque<>();

            while (!stack.isEmpty()) {

                //从节点节到根,反着处理
                TreeNode pop = stack.pop();

                String line = "";
                String C1 = "────";
                String C2 = "│";

                String C3 = "└";
                String C4 = "├";
                String C5 = "    ";
                String C6 = " ";

                String a = "";
                String b = "";
                String c = "";

                //未出现过的路径
                if (!beforeSet.contains(pop))
                {

                    //有双亲
                    if (pop.parent != null) {
                        //是最右的节点

                        if(isRightest(pop,showFile))
                        {
                            a = C3;
                        } else {
                            a = C4;
                        }

                        b = C1;

//                        if(showFile)
//                        {
//                            //todo 格式化file
////                            if(!pop.isDir)
////                            {
////                                a = C5;
////                                b = C5;
////                            }
//                        }

                        c = showName(pop);
                    } else {
                        //没有双亲,根节点
                        a = "";
                        b = "";
                        c = showName(pop);
                    }


                } else {

                    //出现过的,要合并
                    //
                    if (pop.parent != null) {
                        //有双亲的
                        a = C2; //关键连接线总为|
                        //a = C6;
                        b = C5; //合并为空
                        c = "";

                        //节点是最右的

                        if(isRightest(pop,showFile))
                        {
                            a = C6; //最右的去掉顶部连接线
                        }

                    } else {
                        //根节点，全为空
                        a = "";
                        b = "";
                        c = "";
                    }
                }

                line = a + b + c;

                beforeSet.add(pop);
                lines.add(line);
            }


            while (!lines.isEmpty()) {
                System.out.print(lines.pop());
            }

            System.out.println();
        }

    }

    private boolean isRightest(TreeNode node,boolean showFile)
    {

        if(showFile)
        {
            return node.isRightest();
        }
        else
        {
            return node.isRightestDir();

        }
    }


    private String showName(TreeNode node) {
        if (node.isDir) {
            return node.name;
        } else {
            return node.name ;
                    //+ " (file)";
        }
    }


    public TreeNode build() {
        TreeNode root = new TreeNode("sz");
        TreeNode dir1 = new TreeNode("dir1");
        TreeNode dir2 = new TreeNode("dir2");

        TreeNode dir11 = new TreeNode("dir11");
        TreeNode dir12 = new TreeNode("dir12");

        TreeNode f1 = new TreeNode("f1.txt");
        TreeNode f11 = new TreeNode("f11.txt");
        TreeNode f21 = new TreeNode("f21.txt");
        TreeNode f22 = new TreeNode("f22.txt");

        root.add(f1);
        root.add(dir1);
        root.add(dir2);

        dir1.add(dir11);
        dir1.add(dir12);

        dir1.add(f11);
        dir2.add(f21);
        dir2.add(f22);

        return root;

    }

    private List<TreeNode> traverse_recursive(TreeNode node) {

        List<TreeNode> path = new ArrayList<>();
        traverse_recursive_inner(path, node);
        return path;
    }

    private void traverse_recursive_inner(List<TreeNode> path, TreeNode node) {
        if (node == null) {
            return;
        }

        path.add(node);

        for (TreeNode item : node.childNodes) {
            traverse_recursive_inner(path, item);
        }

    }


    //todo 未完成
    private List<String> traverse_stack(TreeNode node) {

        List<String> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        Stack<StackItem> stackItems = new Stack<>();

        TreeNode current = node;

        while (!stack.isEmpty() || current != null) {

            while (current != null) {
                result.add(current.name);//D;

                stack.push(current);
                StackItem stackItem = new StackItem(current.getIndexOfParent());
                stackItems.push(stackItem);
                //todo 上一个兄弟序号放在stack

                //current = current.left;
                current = current.getPrevChild(stackItem.childIndex);
            }

            TreeNode parent = stack.pop();
            StackItem parentStackItem = stackItems.pop();
            parentStackItem.childIndex += 1;


            //current = parent.right;
            current = parent.getNextChild(parentStackItem.childIndex);


        }

        return result;

    }

    class StackItem {
        private int childIndex = 0;

        public StackItem(int childIndex) {
            this.childIndex = childIndex;
        }

        public int getChildIndex() {
            return childIndex;
        }

        public void setChildIndex(int childIndex) {
            this.childIndex = childIndex;
        }
    }


    class TreeNode {
        String name;

        List<TreeNode> childNodes = new ArrayList<>();

        TreeNode parent;

        int level = 0;

        boolean isDir;

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        TreeNode(String x) {
            name = x;
        }

        public void add(TreeNode node) {
            this.childNodes.add(node);
            node.parent = this;
            node.level = level + 1;
        }

        public int getIndexOfParent() {
            if (parent != null) {
                return parent.childNodes.indexOf(this);
            }

            return 0;
        }

        public TreeNode getNextChild(int lastIndex) {

            if (lastIndex < childNodes.size() - 1) {
                return childNodes.get(lastIndex + 1);
            }

            return null;
        }

        public TreeNode getPrevChild(int lastIndex) {

            return childNodes.get(lastIndex);
        }

        public boolean isRightest() {

            if (parent != null) {
                int index = parent.childNodes.indexOf(this);
                if (index == parent.childNodes.size() - 1) {
                    return true;
                }
            }

            return false;
        }

        public boolean isRightestDir() {

            if (parent != null) {

                int rightestIndex=-1;

                for(int i=0;i<parent.childNodes.size();i++)
                {
                    if(parent.childNodes.get(parent.childNodes.size()-1- i ).isDir)
                    {
                        rightestIndex=parent.childNodes.size()-1- i;
                        break;
                    }
                }


                if(parent.childNodes.indexOf(this)==rightestIndex)
                {
                    return true;
                }
            }

            return false;
        }


    }
}
