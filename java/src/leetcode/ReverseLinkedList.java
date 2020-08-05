package leetcode;

import leetcode.definition.ListNode;

/**
 * leetcode 206: Reverse Linked List
 *
 * Reverse a singly linked list.
 *
 * Example:
 *   Input: 1->2->3->4->5->NULL
 *   Output: 5->4->3->2->1->NULL
 *
 * Follow up:
 *   A linked list can be reversed either iteratively or recursively. Could you implement both?
 */
public class ReverseLinkedList {
    /**
     * iteratively
     */
    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }

    /**
     * recursively
     */
    public static ListNode reverseList_2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = reverseList_2(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

}
