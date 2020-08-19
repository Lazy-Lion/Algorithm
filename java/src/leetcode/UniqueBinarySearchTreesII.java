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
    /**
     * 自底向上递归
     * @see UniqueBinarySearchTrees#numTrees(int)
     * （自顶向下递归：当一个根节点有多种子树时，新增一条记录较为复杂）
     */
    public static List<TreeNode> generateTrees(int n) {
        return constructTrees(1, n);
    }

    private static List<TreeNode> constructTrees(int start, int end) {
        List<TreeNode> result = new ArrayList<>();

        if (start > end) {
            return result;
        }

        for (int i = start; i <= end; i++) {
            List<TreeNode> leftList = constructTrees(start, i - 1);
            List<TreeNode> rightList = constructTrees(i + 1, end);

            // add root
            if (leftList.size() > 0 && rightList.size() > 0) {
                for (int x = 0; x < leftList.size(); x++) {
                    TreeNode left = leftList.get(x);
                    for (int y = 0; y < rightList.size(); y++) {
                        TreeNode right = rightList.get(y);

                        TreeNode root = new TreeNode(i);
                        root.left = left;
                        root.right = right;
                        result.add(root);
                    }
                }
            } else if (leftList.size() > 0) {
                for (int x = 0; x < leftList.size(); x++) {
                    TreeNode root = new TreeNode(i);
                    root.left = leftList.get(x);
                    result.add(root);
                }
            } else if (rightList.size() > 0) {
                for (int y = 0; y < rightList.size(); y++) {
                    TreeNode root = new TreeNode(i);
                    root.right = rightList.get(y);
                    result.add(root);
                }
            } else {
                TreeNode root = new TreeNode(i);
                result.add(root);
            }
        }
        return result;
    }

    /**
     * optimize {@link #constructTrees(int, int)}
     */
    private static List<TreeNode> constructTrees_2(int start, int end) {
        List<TreeNode> result = new ArrayList<>();

        if (start > end) {
            result.add(null); // simplify adding root
            return result;
        }

        for (int i = start; i <= end; i++) {
            List<TreeNode> leftList = constructTrees(start, i - 1);
            List<TreeNode> rightList = constructTrees(i + 1, end);

            // add root
            for (TreeNode left : leftList) {
                for (TreeNode right : rightList) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    result.add(root);
                }
            }
        }
        return result;
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
