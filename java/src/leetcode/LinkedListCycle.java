package leetcode;

import leetcode.definition.ListNode;

/**
 * leetcode 141: Linked List Cycle
 *
 * Given a linked list, determine if it has a cycle in it.
 */
public class LinkedListCycle {

    /**
     * Algorithm: Floyd's Tortoise and Hare
     * @see LinkedListCycleII
     *
     * speed pointer: fast and slow pointer
     *
     * time complexity: if there is a ring: O(x + y), otherwise: O(n)
     *   time complexity analysis: {@link LinkedListCycleII#detectCycle(ListNode)}
     *
     * space complexity: O(1)
     */
    public static boolean hasCycle(ListNode head) {
        ListNode slow, fast;
        slow = fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) return true;
        }
        return false;
    }
}
