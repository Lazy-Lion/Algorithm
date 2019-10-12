package tree;

import java.util.*;

/**
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
public class BTLOTII107 {
	private static final TreeNode LEVEL_SEPERATOR = null; // 使用空指针表示层结束

	public List<List<Integer>> levelOrderBottom(TreeNode root) {
		List<List<Integer>> result = new ArrayList<>();

		if(root == null) return result;

		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		queue.offer(LEVEL_SEPERATOR);

		List<Integer> list = new ArrayList<>();
		while(!queue.isEmpty()) {
			TreeNode node = queue.poll();

			if(node == LEVEL_SEPERATOR) { // 当前行结束
				result.add(list);
				list = new ArrayList<>();

				if(!queue.isEmpty()) queue.offer(LEVEL_SEPERATOR);

				continue;
			}

			list.add(node.val);
			if(node.left != null) queue.offer(node.left);
			if(node.right != null) queue.offer(node.right);
		}

		Collections.reverse(result);

		return result;
	}

	public static void main(String[] args) {
		BTLOTII107 obj = new BTLOTII107();
		TreeNode root = obj.new TreeNode(3);
		root.left = obj.new TreeNode(9);
		root.right = obj.new TreeNode(20);
		root.right.left = obj.new TreeNode(15);
		root.right.right = obj.new TreeNode(7);

		List<List<Integer>> result = obj.levelOrderBottom(root);
		for(List<Integer> list : result) {
			System.out.println(list.toString());
		}
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
}
