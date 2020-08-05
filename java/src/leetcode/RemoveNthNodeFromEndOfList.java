package leetcode;

import leetcode.definition.ListNode;

/**
 * leetcode 19: Remove Nth Node From End of List
 *
 * Given a linked list, remove the n-th node from the end of list and return its head.
 *
 * Example:
 *   Given linked list: 1->2->3->4->5, and n = 2.
 *   After removing the second node from the end, the linked list becomes 1->2->3->5.
 *
 * Note:
 *   Given n will always be valid.
 *
 * Follow up:
 *   Could you do this in one pass?
 */
public class RemoveNthNodeFromEndOfList {
    /**
     * two pointers:
     *    the first pointer leads the second pointer by k+1
     *
     * time complexity: O(n + k) - n: list's length; k - remove kth node from end of list
     * space complexity: O(1)
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode node = head;
        head = new ListNode();
        head.next = node;  // add sentinel to simplify operations

        ListNode first = head;
        int count = 0;
        while (first != null && count <= n) {
            first = first.next;
            count++;
        }

        ListNode second = head;
        while (first != null) {
            first = first.next;
            second = second.next;
        }

        // delete second.next node
        ListNode temp = second.next;
        second.next = second.next.next;
        temp.next = null;

        return head.next;
    }
}
