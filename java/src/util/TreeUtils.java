package util;

import leetcode.definition.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class TreeUtils {
    private static final Integer SPACE = null;

    /**
     * @param tree consist of numbers and spaces
     *             the input string conforms to the traversal result of binary tree hierarchy, ' ' indicates an empty node
     * @return the root of binary tree
     */
    public static TreeNode constructBinaryTree(Integer[] tree) {
//        assert valid input

        if (tree == null || tree.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode(tree[0]);
        Queue<TreeNode> queue = new LinkedList<>(); // ArrayDeque can not add null object
        queue.offer(root);

        int idx = 0;
        int size = tree.length;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            if (node == null) {
                idx++;
                continue;
            }

            int leftIdx = 2 * idx + 1;

            if (leftIdx >= size) { // subsequent nodes are all leaf nodes
                break;
            }

            int rightIdx = 2 * idx + 2;


            TreeNode left = null, right = null;
            if (leftIdx < size && tree[leftIdx] != SPACE) {
                left = new TreeNode(tree[leftIdx]);
            }

            if (rightIdx < size && tree[rightIdx] != SPACE) {
                right = new TreeNode(tree[rightIdx]);
            }

            node.left = left;
            node.right = right;
            queue.offer(left);
            queue.offer(right);

            idx++;
        }

        return root;
    }
}
