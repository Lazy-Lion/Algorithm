package leetcode;

import leetcode.definition.TreeNode;
import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * leetcode 95: Unique Binary Search Trees II
 *
 * Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.
 *
 * Example:
 *   Input: 3
 *   Output:
 *   [
 *     [1,null,3,2],
 *     [3,2,null,1],
 *     [3,1,null,null,2],
 *     [2,1,3],
 *     [1,null,2,null,3]
 *   ]
 * Explanation:
 *   The above output corresponds to the 5 unique BST's shown below:
 *
 *    1         3     3      2      1
 *     \       /     /      / \      \
 *      3     2     1      1   3      2
 *     /     /       \                 \
 *    2     1         2                 3
 *
 *
 * Constraints:
 *   0 <= n <= 8
 */
public class UniqueBinarySearchTreesII {
    private static List<TreeNode> trees;

    public static List<TreeNode> generateTrees(int n) {
        trees = new ArrayList<>();
        constructTrees(1, n, null, false);
        return trees;
    }

    private static void constructTrees(int start, int end, TreeNode root, boolean isLeft) {
        if (start > end) {
            TreeNode node = trees.get(trees.size() - 1);
            // todo copy treenode
            return;
        }

        for (int i = start; i <= end; i++) {
            TreeNode node = new TreeNode(i);
            constructTrees(start, i - 1, node, true);
            constructTrees(i + 1, end, node, false);
            if (root == null) {
                trees.add(node);
            } else {
                if (isLeft) {
                    root.left = node;
                } else {
                    root.right = node;
                }
            }
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(UniqueBinarySearchTreesII.class
                , new HashSet<String>() {
                    {
                        add("generateTrees");
                    }
                }
                , Arrays.asList(
                        Arrays.asList(3)
                ));
    }
}
