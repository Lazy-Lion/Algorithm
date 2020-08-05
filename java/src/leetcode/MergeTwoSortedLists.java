package leetcode;

import leetcode.definition.ListNode;

/**
 * leetcode 21. Merge Two Sorted Lists
 * Merge two sorted linked lists and return it as a new sorted list.
 * The new list should be made by splicing together the nodes of the first two lists.
 *
 * Example:
 *   Input: 1->2->4, 1->3->4
 *   Output: 1->1->2->3->4->4
 */
public class MergeTwoSortedLists {

    /**
     * time complexity: O(m + n)
     * space complexity: O(1)
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(); // sentinel
        ListNode tail = head;

        ListNode point1 = l1;
        ListNode point2 = l2;

        while (point1 != null && point2 != null) {
            if (point1.val <= point2.val) {
                tail.next = point1;
                tail = point1;
                point1 = point1.next;
            } else {
                tail.next = point2;
                tail = point2;
                point2 = point2.next;
            }
        }

        if (point1 != null) tail.next = point1;
        if (point2 != null) tail.next = point2;

        return head.next;
    }
}
