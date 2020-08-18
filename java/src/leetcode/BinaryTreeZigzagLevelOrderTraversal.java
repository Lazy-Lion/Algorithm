package leetcode;

import leetcode.definition.TreeNode;
import util.TreeUtils;
import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 103: Binary Tree Zigzag Level Order Traversal
 *
 * Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).
 *
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * return its zigzag level order traversal as:
 * [
 *   [3],
 *   [20,9],
 *   [15,7]
 * ]
 */
public class BinaryTreeZigzagLevelOrderTraversal {
    /**
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<TreeNode> stack1 = new ArrayDeque<>();
        Deque<TreeNode> stack2 = new ArrayDeque<>();
        stack1.offer(root);
        int depth = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            Deque<TreeNode> emptyStack = stack1.isEmpty() ? stack1 : stack2;
            Deque<TreeNode> notEmptyStack = stack1.isEmpty() ? stack2 : stack1;

            int size = notEmptyStack.size();
            List<Integer> list = new ArrayList<>();
            while (size > 0) {
                size--;
                TreeNode node = notEmptyStack.pop();
                list.add(node.val);

                TreeNode node1, node2;

                if (depth % 2 == 0) {
                    node1 = node.left;
                    node2 = node.right;
                } else {
                    node1 = node.right;
                    node2 = node.left;
                }

                if (node1 != null) {
                    emptyStack.push(node1);
                }
                if (node2 != null) {
                    emptyStack.push(node2);
                }
            }
            result.add(list);
            depth++;
        }
        return result;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(BinaryTreeZigzagLevelOrderTraversal.class
                , new HashSet<String>() {
                    {
                        add("zigzagLevelOrder");
                        add("zigzagLevelOrder_2");
                    }
                }
                , Arrays.asList(
                        Arrays.asList(TreeUtils.constructBinaryTree(new Integer[]{3, 9, 20, 8, null, 15, 7}))
                ));
    }
}
