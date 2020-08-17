package leetcode;

import leetcode.definition.TreeNode;

import java.util.*;

/**
 * leetcode 112: Path Sum
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
 *
 * Note: A leaf is a node with no children.
 *
 * Example:
 *   Given the below binary tree and sum = 22,
 *
 *       5
 *      / \
 *     4   8
 *    /   / \
 *   11  13  4
 *  /  \      \
 * 7    2      1
 *   return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
 */
public class PathSum {
    /**
     * recursion
     *
     * time complexity: O(n)
     * space complexity: O(logn) -- recursion stack
     */
    public static boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null) {
            return root.val == sum;
        }

        return hasPathSum(root.left, sum - root.val)
                || hasPathSum(root.right, sum - root.val);
    }

    /**
     * iteration + queue (BFS)
     *
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static boolean hasPathSum_bfs(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode node;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                size--;
                node = queue.poll();
                if (node.left == null && node.right == null && node.val == sum) {
                    return true;
                }
                if (node.left != null) {
                    node.left.val += node.val;
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    node.right.val += node.val;
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    /**
     * iteration + stack (DFS)
     *
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static boolean hasPathSum_dfs(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;
        int s = 0; // record the sum of parent node
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                node.val += s;
                s = node.val;
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                if (node.left == null && node.right == null && node.val == sum) {
                    return true;
                }
                s = node.val;
                node = node.right;
            }
        }
        return false;
    }
}
