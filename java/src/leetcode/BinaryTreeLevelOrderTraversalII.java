package leetcode;

import leetcode.definition.TreeNode;
import util.TreeUtils;
import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 107: Binary Tree Level Order Traversal II
 *   Given a binary tree, return the bottom-up level order traversal of its nodes' values. (ie, from left to right,
 * level by level from leaf to root).
 *
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * return its bottom-up level order traversal as:
 * [
 *   [15,7],
 *   [9,20],
 *   [3]
 * ]
 */
public class BinaryTreeLevelOrderTraversalII {
    public static List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        TreeNode node;
        List<Integer> list;
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
        Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(BinaryTreeLevelOrderTraversalII.class
                , new HashSet<String>() {
                    {
                        add("levelOrderBottom");
                    }
                }
                , Arrays.asList(
                        Arrays.asList(TreeUtils.constructBinaryTree(new Integer[]{3, 9, 20, null, null, 15, 7}))
                ));
    }
}
