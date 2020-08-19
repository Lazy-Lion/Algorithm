package leetcode;

import leetcode.definition.TreeNode;
import util.TreeUtils;
import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 98: Validate Binary Search Tree
 * Given a binary tree, determine if it is a valid binary search tree (BST).
 *
 * Assume a BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 * Example 1:
 *     2
 *    / \
 *   1   3
 *
 *   Input: [2,1,3]
 *   Output: true
 *
 *Example 2:
 *     5
 *    / \
 *   1   4
 *      / \
 *     3   6
 *
 *   Input: [5,1,4,null,null,3,6]
 *   Output: false
 *   Explanation: The root node's value is 5 but its right child's value is 4.
 */
public class ValidateBinarySearchTree {
    /**
     * BST需要满足的条件：
     *   1. 左子树满足 BST
     *   2. 右子树满足 BST
     *   3. 左子树的最大值小于根节点， 右子树的最小值大于根节点
     */

    /**
     *
     * way 1 : BST 中序遍历的结果有序递增
     *         递归
     *         time complexity: O(n)
     *         space complexity: O(n)
     */
    public static boolean isValidBST(TreeNode root) {
        List<Integer> traversal = inorderTraversal(root);

        for (int i = 1; i < traversal.size(); i++) {
            if (traversal.get(i - 1) >= traversal.get(i)) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> inorderTraversal(TreeNode root) {
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
     * way 2: 迭代方式，若遍历到某个节点，不符合有序递增，则直接返回false
     * @see #isValidBST(TreeNode)
     */
    public static boolean isValidBST_2(TreeNode root) {
        if (root == null) {
            return true;
        }

        List<Integer> traversal = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                /**
                 * only need to record the value of the last traversed node, no need to use list
                 * @see #isValidBST_4(TreeNode)
                 */
                if (!traversal.isEmpty() && node.val <= traversal.get(traversal.size() - 1)) {
                    return false;
                }
                traversal.add(node.val);
                node = node.right;
            }
        }
        return true;
    }


    /**
     * way 3 : 递归，并添加上下界限定条件
     *   time complexity: O(n)
     *   space complexity: O(n)
     */
    public static boolean isValidBST_3(TreeNode root) {
        return helper(root, null, null);
    }

    private static boolean helper(TreeNode root, Integer lower, Integer upper) {
        if (root == null) return true;

        int val = root.val;
        if (lower != null && val <= lower) return false;
        if (upper != null && val >= upper) return false;

        return helper(root.right, val, upper)
                && helper(root.left, lower, val);
    }

    /**
     * way 4 : way 3 的迭代实现
     */
    public static boolean isValidBST_4(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        Integer min = null;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else if (!stack.isEmpty()) {
                node = stack.pop();
                if (min != null && min >= node.val) return false;
                min = node.val;
                node = node.right;
            }
        }
        return true;
    }


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(ValidateBinarySearchTree.class
                , new HashSet<String>() {
                    {
                        add("isValidBST");
                        add("isValidBST_2");
                        add("isValidBST_3");
                        add("isValidBST_4");
                    }
                }, Arrays.asList(
                        Arrays.asList(TreeUtils.constructBinaryTree(new Integer[]{2, 1, 3})),
                        Arrays.asList(TreeUtils.constructBinaryTree(new Integer[]{5, 1, 4, null, null, 3, 6}))
                ));
    }
}
