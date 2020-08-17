package leetcode;

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

	public boolean leafSimilar(TreeNode root1, TreeNode root2) {
		boolean result = false;

		List<Integer> sequence1 = new ArrayList<>();
		List<Integer> sequence2 = new ArrayList<>();

		leftValueSequence(root1, sequence1);
		leftValueSequence(root2, sequence2);

		// can compare two lists using the equals method implemented by AbstractList
		// result = sequence1.equals(sequence2)
		if(sequence1.size() == sequence2.size()) {
			int i = 0;
			for(; i < sequence1.size(); i ++) {
				if(sequence1.get(i) != sequence2.get(i))
					break;
			}
			if(i == sequence1.size()) result = true;
		}

		return result;
	}

	/**
	 * 二叉树前序遍历
	 */
	private void leftValueSequence(TreeNode root, List<Integer> sequence) {
		if(root == null) return;

		if(root.left == null && root.right == null) {  // leaf
			sequence.add(root.val);
		}

		leftValueSequence(root.left, sequence);
		leftValueSequence(root.right, sequence);
	}


	/**
	 * Definition for a binary tree node.
	 */
	 class TreeNode {
	     int val;
	     TreeNode left;
	     TreeNode right;
	     TreeNode(int x) { val = x; }
	 }

	 public static void main(String[] args) {
	 	LeafSimilarTrees obj = new LeafSimilarTrees();
	 	TreeNode root = obj.new TreeNode(3);
	 	root.left = obj.new TreeNode(5);
	 	root.right = obj.new TreeNode(1);

	 	root.left.left = obj.new TreeNode(6);
	 	root.left.right = obj.new TreeNode(2);

	 	root.left.right.left = obj.new TreeNode(7);
	 	root.left.right.right = obj.new TreeNode(4);

	 	root.right.left = obj.new TreeNode(9);
	 	root.right.right = obj.new TreeNode(8);

	 	System.out.println(obj.leafSimilar(root, root));
	 }
}

