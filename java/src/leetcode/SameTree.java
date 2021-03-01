package leetcode;

import leetcode.definition.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * leetcode 100
 */
public class SameTree {
    /**
     * dfs
     */
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        }

        if (p.val != q.val) {
            return false;
        }

        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * bfs
     */
    public static boolean isSameTree_2(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        }

        Queue<TreeNode> queue1 = new LinkedList<>();
        Queue<TreeNode> queue2 = new LinkedList<>();
        queue1.offer(p);
        queue2.offer(q);

        TreeNode left1, left2, right1, right2;
        TreeNode node1, node2;
        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            node1 = queue1.poll();
            node2 = queue2.poll();

            if (node1.val != node2.val) {
                return false;
            }

            left1 = node1.left;
            left2 = node2.left;
            right1 = node1.right;
            right2 = node2.right;

            if ((left1 == null) ^ (left2 == null)) {
                return false;
            }
            if ((right1 == null) ^ (right2 == null)) {
                return false;
            }

            if (left1 != null) {
                if (left1.val != left2.val) {
                    return false;
                }
                queue1.offer(left1);
                queue2.offer(left2);
            }

            if (right1 != null) {
                if (right1.val != right2.val) {
                    return false;
                }
                queue1.offer(right1);
                queue2.offer(right2);
            }
        }

        return queue1.isEmpty() && queue2.isEmpty();
    }

}
