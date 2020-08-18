package leetcode;

import leetcode.definition.TreeNode;
import util.TreeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode 872: Leaf-Similar Trees
 *   Consider all the leaves of a binary tree.  From left to right order, the values of those leaves form a leaf
 * value sequence.
 *                                       3
 *                                  ↙       ↘
 *                                5             1
 *                           ↙     ↘       ↙   ↘
 *                         6         2     9       8
 *                               ↙   ↘
 *                              7      4
 *
 *   For example, in the given tree above, the leaf value sequence is (6, 7, 4, 9, 8).
 *   Two binary trees are considered leaf-similar if their leaf value sequence is the same.
 *   Return true if and only if the two given trees with head nodes root1 and root2 are leaf-similar.
 *
 * Note:
 *   Both of the given trees will have between 1 and 100 nodes.
 */
public class LeafSimilarTrees {
    /***
     * time complexity: O(n1 + n2)
     * space complexity: O(n1 + n2)
     */
    public static boolean leafSimilar(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return root1 == root2;
        }

        List<Integer> sequence1 = new ArrayList<>();
        List<Integer> sequence2 = new ArrayList<>();
        leafValueSequence(root1, sequence1);
        leafValueSequence(root2, sequence2);
        return sequence1.equals(sequence2);
    }

    /**
     * dfs (note: bfs is not applicable)
     */
    private static void leafValueSequence(TreeNode root, List<Integer> sequence) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            sequence.add(root.val);
        }

        leafValueSequence(root.left, sequence);
        leafValueSequence(root.right, sequence);
    }

    public static void main(String[] args) {
        System.out.println(leafSimilar(TreeUtils.constructBinaryTree(new Integer[]{3, 5, 1, 6, 2, 9, 8, null, null, 7, 4})
                , TreeUtils.constructBinaryTree(new Integer[]{3, 5, 1, 6, 7, 4, 2, null, null, null, null, null, null, 9, 8})));
    }
}

