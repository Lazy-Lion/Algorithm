package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 98. Validate Binary Search Tree
 * Given a binary tree, determine if it is a valid binary search tree (BST).
 *
 * Assume a BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 * Example 1:
 *     2
 *    / \
 *   1   3
 *
 *   Input: [2,1,3]
 *   Output: true
 *
 *Example 2:
 *     5
 *    / \
 *   1   4
 *      / \
 *     3   6
 *
 *   Input: [5,1,4,null,null,3,6]
 *   Output: false
 *   Explanation: The root node's value is 5 but its right child's value is 4.
 */
public class ValidateBinarySearchTree98 {
	private static List<Integer> list = new ArrayList<>();

	/**
	 * BST需要满足的条件：
	 *   1. 左子树满足 BST
	 *   2. 右子树满足 BST
	 *   3. 左子树的最大值小于根节点， 右子树的最小值大于根节点
	 *
	 *
	 * way 1 : BST 中序遍历的结果有序递增 (优化：迭代方式，若遍历到某个节点，不符合有序递增，则直接返回false)
	 *         时间复杂度 O(n), 空间复杂度 O(n)
	 */
	public static boolean isValidBST(TreeNode root) {
		list = new ArrayList<>();
		inorder(root);

		if(list.size() <= 1) return true;
		int max = list.get(0);
		for(int i = 1; i < list.size(); i ++) {
			if(max >= list.get(i)) return false;
			max = list.get(i);
		}
		return true;
	}

	private static void inorder(TreeNode root) {
		if(root == null) return;
		if(root.left != null) inorder(root.left);
		list.add(root.val);
		if(root.right != null) inorder(root.right);
	}

	/**
	 * way 2 : 递归，并添加上下界限定条件
	 *  时间复杂度：O(n), 空间复杂度: O(n)
	 */
	public static boolean isValidBST_2(TreeNode root) {
		return helper(root, null, null);
	}

	private static boolean helper(TreeNode root, Integer lower, Integer upper) {
		if(root == null) return true;

		int val = root.val;
		if(lower != null && val <= lower) return false;
		if(upper != null && val >= upper) return false;

		return helper(root.right, val, upper)
				&& helper(root.left, lower, val);
	}

	/**
	 * way 3 : way 2 的迭代实现
	 */
	public static boolean isValidBST_3(TreeNode root) {
		Stack<TreeNode> stack = new Stack<>();
		TreeNode node = root;
		Integer min = null;
		while(!stack.isEmpty() || node != null) {
			if(node != null) {
				stack.push(node);
				node = node.left;
			} else if (!stack.isEmpty()) {
				node = stack.pop();
				if(min != null && min >= node.val) return false;
				min = node.val;
				node = node.right;
			}
		}
		return true;
	}


	/**
	 * Definition for a binary tree node.
	 */
	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(2);
		root.left = new TreeNode(1);
		root.right = new TreeNode(3);
		System.out.println(isValidBST(root));
		System.out.println(isValidBST_2(root));
		System.out.println(isValidBST_3(root));

		root = new TreeNode(5);
		root.left = new TreeNode(1);
		root.right = new TreeNode(4);
		root.right.left = new TreeNode(3);
		root.right.right = new TreeNode(6);
		System.out.println(isValidBST(root));
		System.out.println(isValidBST_2(root));
		System.out.println(isValidBST_3(root));

		System.out.println(isValidBST(null));
		System.out.println(isValidBST_2(null));
		System.out.println(isValidBST_3(null));


		root = new TreeNode(1);
		root.left = new TreeNode(1);
		System.out.println(isValidBST(root));
		System.out.println(isValidBST_2(root));
		System.out.println(isValidBST_3(root));
	}
}
