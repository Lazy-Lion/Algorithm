package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 987. Vertical Order Traversal of a Binary Tree
 *
 * Given a binary tree, return the vertical order traversal of its nodes values.
 *   For each node at position (X, Y), its left and right children respectively will be at positions (X-1, Y-1)
 * and (X+1, Y-1).
 *
 *   Running a vertical line from X = -infinity to X = +infinity, whenever the vertical line touches some nodes, we
 * report the values of the nodes in order from top to bottom (decreasing Y coordinates).
 * If two nodes have the same position, then the value of the node that is reported first is the value that is smaller.
 * Return an list of non-empty reports in order of X coordinate.  Every report will have a list of values of nodes.
 *
 * Example 1:
 *              3
 *           /     \
 *         9         20
 *                 /    \
 *              15       7
 *   Input: [3,9,20,null,null,15,7]
 *   Output: [[9],[3,15],[20],[7]]
 *   Explanation:
 *     Without loss of generality, we can assume the root node is at position (0, 0):
 *     Then, the node with value 9 occurs at position (-1, -1);
 *     The nodes with values 3 and 15 occur at positions (0, 0) and (0, -2);
 *     The node with value 20 occurs at position (1, -1);
 *     The node with value 7 occurs at position (2, -2).
 *
 * Example 2:
 *                  1
 *               /     \
 *            2           3
 *          /   \       /   \
 *        4      5     6     7
 *   Input: [1,2,3,4,5,6,7]
 *   Output: [[4],[2],[1,5,6],[3],[7]]
 *   Explanation:
 *      The node with value 5 and the node with value 6 have the same position according to the given scheme.
 *      However, in the report "[1,5,6]", the node value of 5 comes first since 5 is smaller than 6.
 *
 * Note:
 *   The tree will have between 1 and 1000 nodes.
 *   Each node's value will be between 0 and 1000.
 */
public class VOTOABT987 {
	private static List<Location> list;

	/**
	 * steps:
	 *   1.遍历二叉树，记录每个数对应的坐标(自定义类Location)
	 *   2.对记录进行排序，排序规则
	 *     1) x坐标按照从小到大排列
	 *     2) x相同，y坐标按照从大到小排列
	 *     3) x、y相同，val值按照从小到大排列
	 *   3.依次按行输出结果
	 *
	 * 时间复杂度： 遍历二叉树 O(n), 排序 O(nlogn), 遍历节点依次输出 O(n) => 总时间复杂度: O(nlogn)
	 * 空间复杂度： O(n)
	 */
	public static List<List<Integer>> verticalTraversal(TreeNode root) {
		List<List<Integer>> result = new ArrayList<>();

		list = new ArrayList<>();
		dfs(root, 0, 0);
		Collections.sort(list);

		int rowNum = list.get(0).x;
		List<Integer> row = new ArrayList<>();
		result.add(row);
		int x, val;
		for(Location l : list) {
			x = l.x;
			val = l.val;
			if(x == rowNum) {
				row.add(val);
			} else {
				row = new ArrayList<>();
				result.add(row);
				row.add(val);
				rowNum = x;
			}
		}

		return result;
	}

	private static void dfs(TreeNode node, int x, int y) {
		if(node == null) return;

		list.add(new Location(x, y, node.val));
		dfs(node.left, x - 1, y - 1);
		dfs(node.right, x + 1, y - 1);
	}

	private static class Location implements Comparable<Location>{
		int x, y, val;

		Location(int x, int y, int val) {
			this.x = x;
			this.y = y;
			this.val = val;
		}

		@Override
		public int compareTo(Location o) {
			if(this.x != o.x)
				return this.x - o.x;
			else if (this.y != o.y)
				return o.y - this.y;
			else
				return this.val - o.val;
		}
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
		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(9);
		root.right = new TreeNode(20);
		root.right.left = new TreeNode(15);
		root.right.right = new TreeNode(7);
		System.out.println(verticalTraversal(root));

		root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);
		System.out.println(verticalTraversal(root));
	}
}
