package leetcode;

import leetcode.definition.ListNode;
import leetcode.definition.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * leetcode 109: Convert Sorted List to Binary Search Tree
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
public class ConvertSortedListToBinarySearchTree {
    private static ListNode head;

    /**
     * way1:
     * 递归 + 快慢指针
     *  升序排列的列表转换成BST，列表的中位数为根节点
     *
     *  time complexity: O(nlogn)
     *  space complexity: O(logn)
     */
    public static TreeNode sortedListToBST(ListNode head) {
        return formBST(head, null);
    }

    private static TreeNode formBST(ListNode head, ListNode tail) {
        if (head == tail) return null;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != tail) {
            fast = fast.next;
            if (fast != tail) {
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
     * way2:
     *   递归 + 数组(链表转换成数组，求中位数只需要简单的运算，T(n) = O(1))
     *
     *   time complexity: O(n)
     *   space complexity: O(n)
     */
    public static TreeNode sortedListToBST_2(ListNode head) {
        List<Integer> list = new ArrayList<>();
        ListNode node = head;
        while (node != null) {
            list.add(node.val);
            node = node.next;
        }
        return formBST(list, 0, list.size() - 1);
    }

    private static TreeNode formBST(List<Integer> list, int start, int end) {
        if (start > end) {
            return null;
        }

        int middle = start + ((end - start) >>> 1);
        TreeNode root = new TreeNode(list.get(middle));
        root.left = formBST(list, start, middle - 1);
        root.right = formBST(list, middle + 1, end);
        return root;
    }

    /**
     * way3: 递归
     * 升序排列的列表转换成BST，列表的第一个元素为BST的最左节点
     *
     * 模拟中序遍历的过程：需要在原先输出数据的地方添加正确的节点值
     *   中序遍历输出值的顺序和链表顺序一致
     *   =>节点值从head开始，每次添加之后取next即可
     *
     * time complexity: O(n)
     * space complexity: O(logn)
     */
    public static TreeNode sortedListToBST_3(ListNode head) {
        int size = getSize(head);

        ConvertSortedListToBinarySearchTree.head = head;

        return formBST(0, size - 1);
    }

    private static TreeNode formBST(int l, int r) { // l and r, just to divide the left and right subtree
        if (l > r) return null;

        int m = l + (r - l) / 2;

        // recursively form the left child tree
        TreeNode left = formBST(l, m - 1);

        // process the current node
        TreeNode node = new TreeNode(ConvertSortedListToBinarySearchTree.head.val);
        node.left = left;

        // point to the ListNode where index = m + 1
        ConvertSortedListToBinarySearchTree.head = ConvertSortedListToBinarySearchTree.head.next;

        // recursively form the right child tree
        node.right = formBST(m + 1, r);
        return node;
    }

    private static int getSize(ListNode head) {
        int size = 0;

        ListNode node = head;
        while (node != null) {
            node = node.next;
            size++;
        }

        return size;
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
        while (!queue.isEmpty()) {
            node = queue.poll();
            if (node == null) {
                result.add(null);
            } else {
                result.add(Integer.toString(node.val));
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        int i = result.size() - 1;
        for (; i > 0; i--) {
            if (result.get(i) != null) break;
        }

        result = result.subList(0, i + 1);

        return result;
    }
}
