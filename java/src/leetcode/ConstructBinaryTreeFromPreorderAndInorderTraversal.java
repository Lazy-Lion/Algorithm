package leetcode;

import leetcode.definition.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * leetcode 105
 */
public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    private static Map<Integer, Integer> map;

    /**
     * 递归
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = inorder.length;
        if (n == 0) {
            return null;
        }

        map = new HashMap<>(n);

        for (int i = 0; i < n; i++) {
            map.put(inorder[i], i);
        }

        return buildTree(preorder, 0, n - 1, inorder, 0, n - 1);
    }

    private TreeNode buildTree(int[] preorder, int pLeft, int pRight, int[] inorder, int iLeft, int iRight) {
        if (pLeft > pRight) {
            return null;
        }

        int index = map.get(preorder[pLeft]);
        TreeNode root = new TreeNode(preorder[pLeft]);
        int leftSize = index - iLeft;
        root.left = buildTree(preorder, pLeft + 1, pLeft + leftSize, inorder, iLeft, index - 1);
        root.right = buildTree(preorder, pLeft + leftSize + 1, pRight, inorder, index + 1, iRight);
        return root;
    }

    /**
     * 迭代思路分析：
     *        3
     *      / \
     *     9  20
     *   /  \
     *  15   7
     *
     *  inorder:  [15,9,7,3,20]
     *  preorder: [3,9,15,7,20]
     *
     *  对于 preorder, 相邻两个数字 uv, 必然满足以下两个条件之一：
     *    1. v 是 u 的左儿子
     *    2. u 没有左儿子，如果 u 有右儿子，则 v 是 u 的右儿子，否则 v 是 u 的祖先的右儿子。
     *
     *  遍历 preorder：
     *    3 ： 为根节点
     *    9 ： 必然是 3 的左儿子。证明： 如果不是说明 3 没有左儿子， 则 inorder 的第一个数必然是 3
     *    15 ： 必然是 9 的左儿子。证明： 如果不是说明 9 没有左儿子， 则 inorder 的第一个数必然是 9
     *    7 ： 必然不是 15 的左儿子。如果是 15 的右儿子，inorder [15, 7]; 如果是 9 的右儿子 inorder [15, 9, 7]; 如果是
     *         3 的右儿子 inorder [15,9,3,7] => 7 是 9 的右儿子
     *    20： 必然不是 7 的左儿子。=> 20 是 3 的右儿子。
     *
     *
     * time complexity: O(n)
     * spaace complexity: O(n)
     */
    public TreeNode buildTree_2(int[] preorder, int[] inorder) {
        int n = preorder.length;
        if (n == 0) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[0]);
        Deque<TreeNode> stack = new ArrayDeque<>();
        int inorderIndex = 0;
        stack.push(root);
        for (int i = 1; i < n; i++) {
            int preorderVal = preorder[i];
            TreeNode node = stack.peek();
            if (node.val != inorder[inorderIndex]) {
                node.left = new TreeNode(preorderVal);
                stack.push(node.left);
            } else {
                while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                    node = stack.pop();
                    inorderIndex++;
                }
                node.right = new TreeNode(preorderVal);
                stack.push(node.right);
            }
        }
        return root;
    }
}
