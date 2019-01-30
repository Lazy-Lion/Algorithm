package tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**                               节点高度   节点深度   节点层数
 *               A                   3         0         1
 *         ↙       ↘
 *       B            C              2         1         2
 *    ↙   ↘       ↙  ↘
 *  D       E     F       G          1         2         3
 *   ↘                 ↙
 *     H               I             0         3         4
 *
 *  叶子节点： 没有子节点的节点
 *  根节点： 没有父节点的节点
 *  父节点、子节点： 上图 A是C的父节点，C是A的子节点
 *  兄弟节点 (sibling node)： 父节点是同一个节点的节点
 *  节点的高度： 节点到叶子节点的最长路径(边数)
 *  节点的深度： 根节点到这个节点所经历的边的个数
 *  节点的层数： 节点深度 + 1
 *  树的高度： 根节点的高度
 *
 *  二叉树(Binary Tree)： 每个节点最多只有两个分支(左子节点和右子节点),二叉树分支具有左右次序，不能随意颠倒。二叉树的第i层最多
 *                        拥有 2 ^ (i - 1) 个节点，深度为 k 的二叉树至多总共有 2 ^(k+1) - 1 个节点
 *  满二叉树：深度为 k 的二叉树，有 2 ^(k+1) - 1 个节点 => 叶子节点全部在最底层，非叶子节点都有2个子节点
 *  完全二叉树：深度为 k 有 n 个节点的二叉树，当且仅当其中每一个节点都可以和同样深度 k 的满二叉树，序号为 1 到 n的节点一一对应
 *             => 叶子节点都在最底下两层，最后一层的叶子节点都靠左排列，除了最后一层，其他层节点个数都有 2 ^ (i - 1) 个
 *
 *
 * 二叉树存储方式：
 *     1) 基于指针或者引用的二叉链式存储法
 *     2) 基于数组的顺序存储法(适用于完全二叉树，对于非完全二叉树会浪费较多空间)
 */
public class BinaryTree {
    private Node root;

    public BinaryTree(String tree){
        createTree(tree);
    }

    public void Traversal(){
        System.out.print("前序遍历     ：");
        preOrderTraversal(root);
        System.out.println();

        System.out.print("前序遍历(迭代)：");
        preOrderTraversalIteration(root);
        System.out.println();

        System.out.print("中序遍历     ：");
        inOrderTraversal(root);
        System.out.println();

        System.out.print("中序遍历(迭代)：");
        inOrderTraversalIteration(root);
        System.out.println();

        System.out.print("后序遍历     ：");
        postOrderTraversal(root);
        System.out.println();

        System.out.print("后序遍历(迭代)：");
        postOrderTraversalIteration(root);
        System.out.println();

        System.out.print("层次遍历     ：");
        levelTraversal(root);
        System.out.println();
    }

    //构造二叉树
    private void createTree(String tree){ // assume : 输入字符串符合完全二叉树层次遍历结果，' '表示空节点
        if(tree == null || tree.length() < 1) return;

        // char[] chars = tree.toCharArray();

        int len = tree.length();
        Queue<Node> queue = new LinkedList<>();
        Node node = new Node(tree.charAt(0) - '0');
        root = node;
        queue.add(node);
        int index = 0;
        // index from 0 to len - 1, if node i, left child node 2 * i + 1; right child node 2 * i + 2
        while (queue != null && !queue.isEmpty()){
            node = queue.poll();

            if(node == null){
                index ++;
                continue;
            }

            Node left, right;
            left = right = null;
            if(2 * index + 1 >= len) break;  // 后续节点都是叶子节点

            if(2 * index + 1 < len) {
                char lc = tree.charAt(2 * index + 1);
                if(lc != ' ') left = new Node(lc - '0');
            }
            if(2 * index + 2 < len) {
                char rc = tree.charAt(2 * index + 2);
                if(rc != ' ') right = new Node(rc - '0');
            }

            node.left = left;
            node.right = right;

            queue.add(left);
            queue.add(right);
            index ++;
        }
    }

    /**
     * 二叉树遍历 L、D、R分别表示遍历左子树、访问根节点、遍历右子树：
     *   1) 前序遍历：遍历顺序 DLR    ↘
     *   2) 中序遍历: 遍历顺序 LDR        深度优先搜索 (Depth First Search, DFS)
     *   3) 后序遍历：遍历顺序 LRD    ↗
     *
     *   4) 层次遍历              => 广度优先搜索 (Breadth First Search, BFS)
     *
     * 遍历时间复杂度 O(n) -- n为节点数
     */

    // 前序遍历
    private void preOrderTraversal(Node node){
        if(node == null) return;

        System.out.print(node.getData() + " ");
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);

    }

    //前序遍历迭代实现
    private void preOrderTraversalIteration(Node node){
        if(node == null) return;

        Stack<Node> stack = new Stack<>();
        while(node != null || (stack != null && !stack.isEmpty())){
            if(node != null) {
                System.out.print(node.getData() + " ");
                stack.push(node);
                node = node.left;
            }else{
                node = stack.pop();
                node = node.right;
            }
        }
    }

    // 中序遍历
    private void inOrderTraversal(Node node){
        if(node == null) return;

        inOrderTraversal(node.left);
        System.out.print(node.getData() + " ");
        inOrderTraversal(node.right);
    }

    // 中序遍历迭代实现
    private void inOrderTraversalIteration(Node node){
        if(node == null) return;

        Stack<Node> stack = new Stack<>();
        while(node != null || (stack != null && !stack.isEmpty())){
            if(node != null) {
                stack.push(node);
                node = node.left;
            }else{
                node = stack.pop();
                System.out.print(node.getData() + " ");
                node = node.right;
            }
        }
    }

    // 后序遍历
    private void postOrderTraversal(Node node){
        if(node == null) return;

        postOrderTraversal(node.left);
        postOrderTraversal(node.right);
        System.out.print(node.getData() + " ");
    }

    // 后序遍历迭代实现
    private void postOrderTraversalIteration(Node node){
        if(node == null) return;

        Stack<Node> stack = new Stack<>();
        Node pLastVisit = null;     // 标识最近一次访问的节点
        while(node != null || (stack != null && !stack.isEmpty())){
            if(node != null){
                stack.push(node);
                node = node.left;
            }else{
                node = stack.peek();
                if(node.right == null || node.right == pLastVisit){  // 右子树已经访问，则输出当前节点，否则访问右子树
                    System.out.print(node.getData() + " ");
                    pLastVisit = node;
                    stack.pop();
                    node = null;
                }else{
                    node = node.right;
                }
            }
        }
    }

    // 层次遍历
    private void levelTraversal(Node node){
        if(node == null) return;

        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while(queue != null && !queue.isEmpty()){
            node = queue.poll();
            System.out.print(node.getData() + " ");

            if(node.left != null) queue.add(node.left);
            if(node.right != null) queue.add(node.right);
        }
    }

    static final class Node {
        int data;
        Node left;
        Node right;

        public Node(int data){
            this.data = data;
        }

        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public int getData() {
            return data;
        }
    }
}
