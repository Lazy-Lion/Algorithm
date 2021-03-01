package tree;

import java.util.*;

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
    private static TreeNode root;

    public BinaryTree(String tree) {
        createTree(tree);
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
     *
     */

    /**
     * 前序遍历
     *   recursion
     */
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return new ArrayList<>();
        }

        result.add(root.val);
        result.addAll(preorderTraversal(root.left));
        result.addAll(preorderTraversal(root.right));
        return result;
    }

    /**
     * 前序遍历
     *   iteration
     */
    public static List<Integer> preorderTraversal_iteration(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                result.add(node.val);
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                node = node.right;
            }
        }
        return result;
    }

    /**
     * 前序遍历
     *   参考 {@link graph.Graph#dfsIteration(int, int)}的思想，前序遍历的另一种迭代写法
     */
    public static List<Integer> preorderTraversal_iteration2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return result;
    }

    /**
     * 中序遍历
     *   recursion
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        result.addAll(inorderTraversal(root.left));
        result.add(root.val);
        result.addAll(inorderTraversal(root.right));
        return result;
    }

    /**
     * 中序遍历
     *   iteration
     */
    public static List<Integer> inorderTraversal_iteration(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                result.add(node.val);
                node = node.right;
            }
        }
        return result;
    }


    /**
     * 后序遍历
     *   recursion
     */
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        result.addAll(postorderTraversal(root.left));
        result.addAll(postorderTraversal(root.right));
        result.add(root.val);
        return result;
    }

    /**
     * 后序遍历
     *   iteration
     */
    public static List<Integer> postorderTraversal_iteration(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        // 标识最近一次访问的节点
        TreeNode lastVisitNode = null;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.peek();
                // 右子树已经访问，则输出当前节点，否则访问右子树
                if (node.right == null || node.right == lastVisitNode) {
                    stack.pop();
                    result.add(node.val);
                    lastVisitNode = node;
                    node = null;
                } else {
                    node = node.right;
                }
            }
        }

        return result;
    }

    /**
     * 层次遍历
     */
    private static final String LEVEL = "level";

    /**
     * with level mark
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<Object> queue = new LinkedList<>();
        queue.offer(root);
        queue.offer(LEVEL);

        List<Integer> list = new ArrayList<>();
        TreeNode node;
        while (!queue.isEmpty()) {
            Object item = queue.poll();
            // next level
            if (LEVEL.equals(item)) {
                result.add(list);
                list = new ArrayList<>();
                if (!queue.isEmpty()) {
                    queue.offer(LEVEL);
                }
            } else {
                node = (TreeNode) item;
                list.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

        }
        return result;
    }

    /**
     * without level mark
     */
    public static List<List<Integer>> levelOrder_2(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        List<Integer> list;
        TreeNode node;
        while (!queue.isEmpty()) {
            int size = queue.size();
            list = new ArrayList<>();
            while (size > 0) {
                size--;
                node = queue.poll();
                list.add(node.val);

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.add(list);
        }

        return result;
    }

    /**
     * construct binary tree
     * 输入字符串必须符合完全二叉树层次遍历结果，' '表示空节点
     */
    private void createTree(String tree) { // assume : 输入字符串符合完全二叉树层次遍历结果，' '表示空节点
        if (tree == null || tree.length() < 1) return;

        // char[] chars = tree.toCharArray();

        int len = tree.length();
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode node = new TreeNode(tree.charAt(0) - '0');
        root = node;
        queue.add(node);
        int index = 0;
        // index from 0 to len - 1, if node i, left child node 2 * i + 1; right child node 2 * i + 2
        while (index < len) {
            node = queue.poll();

            TreeNode left = null, right = null;

            int leftIndex = 2 * index + 1;
            if (leftIndex >= len) break;  // 后续节点都是叶子节点

            int rightIndex = 2 * index + 2;
            if (leftIndex < len && tree.charAt(leftIndex) != ' ') {
                left = new TreeNode(tree.charAt(leftIndex) - '0');
            }
            if (rightIndex < len && tree.charAt(rightIndex) != ' ') {
                right = new TreeNode(tree.charAt(rightIndex) - '0');
            }

            if (node != null) {
                node.left = left;
                node.right = right;
            }

            queue.add(left);
            queue.add(right);
            index++;
        }
    }

    public void Traversal() {
        System.out.print("前序遍历        ：");
        System.out.println(preorderTraversal(root));

        System.out.print("前序遍历（迭代） ：");
        System.out.println(preorderTraversal_iteration(root));

        System.out.print("前序遍历（迭代2）：");
        System.out.println(preorderTraversal_iteration2(root));

        System.out.print("中序遍历        ：");
        System.out.println(inorderTraversal(root));

        System.out.print("中序遍历（迭代） ：");
        System.out.println(inorderTraversal_iteration(root));

        System.out.print("后序遍历        ：");
        System.out.println(postorderTraversal(root));

        System.out.print("后序遍历（迭代） ：");
        System.out.println(postorderTraversal_iteration(root));

        System.out.print("层次遍历        ：");
        System.out.println(levelOrder(root));
    }
}
