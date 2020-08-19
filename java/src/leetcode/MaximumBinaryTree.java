package leetcode;

import leetcode.definition.TreeNode;

/**
 * leetcode 654: Maximum Binary Tree
 *
 * Given an integer array with no duplicates. A maximum tree building on this array is defined as follow:
 *
 * The root is the maximum number in the array.
 * The left subtree is the maximum tree constructed from left part subarray divided by the maximum number.
 * The right subtree is the maximum tree constructed from right part subarray divided by the maximum number.
 * Construct the maximum tree by the given array and output the root node of this tree.
 *
 * Example 1:
 *   Input: [3,2,1,6,0,5]
 *   Output: return the tree root node representing the following tree:
 *
 *       6
 *     /   \
 *    3     5
 *     \    /
 *      2  0
 *        \
 *         1
 * Note:
 *   The size of the given array will be in the range [1,1000].
 */
public class MaximumBinaryTree {
    /**
     * Cartesian Tree(笛卡尔树)：
     *   这种方式构造出来的树是笛卡尔树
     *   性质：
     *     1. 中序遍历即为原数组
     *     2. 父节点的值大于（或小于）左右子节点的值
     */

    /**
     * recursion
     *   time complexity: O(n^2)
     *   space complexity: O(n)
     */
    public static TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructMaxTree(nums, 0, nums.length - 1);
    }

    private static TreeNode constructMaxTree(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        int maxIndex = start;
        for (int i = start + 1; i <= end; i++) {
            if (nums[maxIndex] < nums[i]) {
                maxIndex = i;
            }
        }
        TreeNode root = new TreeNode(nums[maxIndex]);
        root.left = constructMaxTree(nums, start, maxIndex - 1);
        root.right = constructMaxTree(nums, maxIndex + 1, end);
        return root;
    }

    /**
     * 使用单调栈构造笛卡尔树
     * @see <a href="https://oi-wiki.org/ds/cartesian-tree/"></a>
     */
    public static TreeNode constructMaximumBinaryTree_2(int[] nums) {
        return null;
    }
}
