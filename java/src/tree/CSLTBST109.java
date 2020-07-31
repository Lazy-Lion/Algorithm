package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * leetcode 109. Convert Sorted List to Binary Search Tree
 *
 * Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.
 *
 *   For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees
 * of every node never differ by more than 1.
 *
 * Example:
 *
 * Given the sorted linked list: [-10,-3,0,5,9],
 *
 * One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:
 *
 *       0
 *      / \
 *    -3   9
 *    /   /
 *  -10  5
 */
public class CSLTBST109 {
	private static ListNode head;
	public static TreeNode sortedListToBST(ListNode head) {
		return formBST(head, null);
	}

	/**
	 * way 1:
	 * 递归 + 快慢指针
	 * ascending 排列的列表转换成BST，列表的中位数为根节点
	 */
	private static TreeNode formBST(ListNode head, ListNode tail) {
		if(head == tail) return null;
		ListNode slow = head;
		ListNode fast = head;

		while(fast != tail) {
			fast = fast.next;
			if(fast != tail) {
				fast = fast.next;
				slow = slow.next;
			}
		}
		TreeNode root = new TreeNode(slow.val);
		root.left = formBST(head, slow);
		root.right = formBST(slow.next, tail);
		return root;
	}

	/**
	 * way 2: 递归
	 * ascending 排列的列表转换成BST，列表的第一个元素为BST的最左节点
	 */
	public static TreeNode sortedListToBST_2(ListNode head) {
		int size = getSize(head);

		CSLTBST109.head = head;

		return formBST(0, size - 1);
	}

	private static TreeNode formBST(int l, int r) {
		if(l > r) return null;

		int m = l + (r - l) / 2;

		// recursively form the left child tree
		TreeNode left = formBST(l, m - 1);

		// process the current node
		TreeNode node = new TreeNode(CSLTBST109.head.val);
		node.left = left;

		// point to the ListNode where index = m + 1
		CSLTBST109.head = CSLTBST109.head.next;

		// recursively form the right child tree
		node.right = formBST(m + 1, r);
		return node;
	}

	private static int getSize(ListNode head) {
		int size = 0;

		ListNode node = head;
		while(node != null) {
			node = node.next;
			size ++;
		}

		return size;
	}

	// Definition for singly-linked list.
	private static class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { val = x; }
	}

	// Definition for a binary tree node.
	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
	}

	public static void main(String[] args) {
		ListNode head = new ListNode(-10);
		head.next = new ListNode(-3);
		head.next.next = new ListNode(0);
		head.next.next.next = new ListNode(5);
		head.next.next.next.next = new ListNode(9);

		TreeNode root = sortedListToBST(head);
		TreeNode root2 = sortedListToBST_2(head);

		// 两个结果不同，但均为符合条件的BST
		System.out.println(levelTraveral(root).toString());
		System.out.println(levelTraveral(root2).toString());
	}

	// just for test printing
	private static List<String> levelTraveral(TreeNode root) {
		List<String> result = new ArrayList<>();
		Queue<TreeNode> queue = new LinkedList<>();

		queue.offer(root);
		TreeNode node;
		while(!queue.isEmpty()) {
			node = queue.poll();
			if(node == null) {
				result.add(null);
			} else {
				result.add(Integer.toString(node.val));
				queue.offer(node.left);
				queue.offer(node.right);
			}
		}

		int i = result.size() - 1;
		for(; i > 0; i --) {
			if(result.get(i) != null) break;
		}

		result = result.subList(0, i + 1);

		return result;
	}
}
