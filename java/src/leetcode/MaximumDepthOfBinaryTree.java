package leetcode;

import leetcode.definition.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * leetcode 104
 */
public class MaximumDepthOfBinaryTree {
    /**
     * dfs
     * time complexity: O(n)
     * space complexity: O(height) - height: the height of the tree
     */
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * bfs
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static int maxDepth_2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        TreeNode node;
        int size;
        while (!queue.isEmpty()) {
            size = queue.size();
            depth++;
            while (size-- > 0) {
                node = queue.poll();

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return depth;
    }
}
