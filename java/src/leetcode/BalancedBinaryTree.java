package leetcode;

import leetcode.definition.TreeNode;
import util.TreeUtils;
import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * leetcode 110: Balanced Binary Tree
 *
 * Given a binary tree, determine if it is height-balanced.
 *
 * For this problem, a height-balanced binary tree is defined as:
 *
 * a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
 *
 * Example 1:
 *   Given the following tree [3,9,20,null,null,15,7]:
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 *   Return true.
 *
 * Example 2:
 *   Given the following tree [1,2,2,3,3,null,null,4,4]:
 *
 *        1
 *       / \
 *      2   2
 *     / \
 *    3   3
 *   / \
 *  4   4
 *
 *   Return false.
 */
public class BalancedBinaryTree {
    /**
     * 自顶向下递归
     *   time complexity: O(n^2)
     *   space complexity: O(n)
     */
    public static boolean isBalanced(TreeNode root) {
        return balanced(root);
    }

    private static boolean balanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left == null && root.right == null) {
            return true;
        }

        if (Math.abs(height(root.left) - height(root.right)) > 1) {
            return false;
        }

        return balanced(root.left) && balanced(root.right);
    }

    private static int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return Math.max(height(root.left), height(root.right)) + 1;
    }

    /**
     * 自底向上递归
     *   time complexity: O(n)
     *   space complexity: O(n)
     */
    public static boolean isBalanced_2(TreeNode root) {
        return height_2(root) >= 0;
    }

    private static int height_2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = height_2(root.left);
        int rightHeight = height_2(root.right);

        if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(BalancedBinaryTree.class
                , new HashSet<String>() {
                    {
                        add("isBalanced");
                        add("isBalanced_2");
                    }
                }
                , Arrays.asList(
                        Arrays.asList(TreeUtils.constructBinaryTree(new Integer[]{3, 9, 20, null, null, 15, 7})),
                        Arrays.asList(TreeUtils.constructBinaryTree(new Integer[]{1, 2, 2, 3, 3, null, null, 4, 4})),
                        Arrays.asList(TreeUtils.constructBinaryTree(new Integer[]{1, 2, 2, 3, null, null, 3, 4, null, null, 4}))
                ));
    }
}
