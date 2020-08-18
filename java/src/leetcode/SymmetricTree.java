package leetcode;

import leetcode.definition.TreeNode;
import util.TreeUtils;
import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 101: Symmetric Tree
 *
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 *
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 *
 * But the following [1,2,2,null,3,null,3] is not:
 *
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 *
 * Follow up: Solve it both recursively and iteratively.
 */
public class SymmetricTree {
    /**
     * recursion
     *
     *   time complexity: O(n)
     *   space complexity: O(n)
     */
    public static boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    public static boolean isMirror(TreeNode node1, TreeNode node2) {
        if (node1 == null || node2 == null) {
            return node1 == node2;
        }

        return node1.val == node2.val
                && isMirror(node1.left, node2.right)
                && isMirror(node1.right, node2.left);
    }

    /**
     * iteration
     *   time complexity: O(n)
     *   space complexity: O(n)
     */
    public static boolean isSymmetric_2(TreeNode root) {
        if (root == null) {
            return true;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<TreeNode> level = new ArrayList<>(size);
            while (size > 0) {
                size--;
                TreeNode node = queue.poll();
                level.add(node);
                if (node != null) {
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
            if (!isSymmetricList(level)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isEqual(TreeNode node1, TreeNode node2) {
        if (node1 == null || node2 == null) {
            return node1 == node2;
        }

        return node1.val == node2.val;
    }

    private static boolean isSymmetricList(List<TreeNode> list) {
        int left = 0;
        int right = list.size() - 1;
        while (left < right) {
            if (!isEqual(list.get(left), list.get(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * iteration
     * @see #isSymmetric(TreeNode)
     *
     *   time complexity: O(n)
     *   space complexity: O(n)
     */
    public static boolean isSymmetric_3(TreeNode root) {
        if (root == null) {
            return true;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();

            if (!isEqual(node1, node2)) {
                return false;
            }

            if (node1 != null) {
                queue.offer(node1.left);
                queue.offer(node2.right);

                queue.offer(node1.right);
                queue.offer(node2.left);
            }
        }
        return true;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(SymmetricTree.class
                , new HashSet<String>() {
                    {
                        add("isSymmetric");
                        add("isSymmetric_2");
                        add("isSymmetric_3");
                    }
                }
                , Arrays.asList(
                        Arrays.asList(TreeUtils.constructBinaryTree(new Integer[]{1, 2, 2, 3, 4, 4, 3})),
                        Arrays.asList(TreeUtils.constructBinaryTree(new Integer[]{1, 2, 2, null, 3, null, 3})),
                        Arrays.asList(TreeUtils.constructBinaryTree(new Integer[]{2, 3, 3, 4, 5, 5, 4, null, null, 8, 9, null, null, 9, 8}))
                )
        );
    }
}
