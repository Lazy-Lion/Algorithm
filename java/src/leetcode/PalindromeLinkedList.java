package leetcode;

import leetcode.definition.ListNode;

/**
 * leetcode 234. Palindrome Linked List
 *
 * Given a singly linked list, determine if it is a palindrome.
 *
 * Example 1:
 *   Input: 1->2
 *   Output: false
 * Example 2:
 *   Input: 1->2->2->1
 *   Output: true
 * Follow up:
 *   Could you do it in O(n) time and O(1) space?
 */
public class PalindromeLinkedList {

    /**
     * steps:
     *   1. get middle node
     *   2. reverse right part
     *   3. traverse the left and right parts, judge palindrome
     *   4. recover right part
     *
     *   time complexity: O(n)
     *   space complexity: O(1)
     */
    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode middle = findMiddleNode(head); // odd - middle node, even - second middle node

        ListNode reverseHead = reverse(middle); // reverse right part

        // judge palindrome
        ListNode left = head;
        ListNode right = reverseHead;

        boolean result = true;
        while (left != middle) {
            if (right == null || left.val != right.val) {
                result = false;
                break;
            }
            left = left.next;
            right = right.next;
        }

        if (right != middle && right != null) { // odd or even
            result = false;
        }

        // recover right part
        reverse(reverseHead);

        return result;
    }

    private static ListNode findMiddleNode(ListNode head) {
        ListNode slow, fast;
        slow = fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private static ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode node = head;
        while (node != null) {
            ListNode next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }
        return prev;
    }
}
