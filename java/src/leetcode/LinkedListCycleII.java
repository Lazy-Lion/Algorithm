package leetcode;

import leetcode.definition.ListNode;

/**
 * leetcode 142: Linked List Cycle II
 *
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
 *
 * Note: Do not modify the linked list.
 */
public class LinkedListCycleII {
    /**
     * speed pointer : fast and slow pointer
     *
     * 1 → 2 → 3
     *     ↖  ↓
     *        4
     * assume: the speed of the fast and slow pointers are 2v, v;
     *         the distance between 1 and 2 is x,
     *         the location of first meeting is 3
     *         the distance between 2 and 3 is y
     *         the length of ring (2->3->4->2) is c
     *    => S1 = x + y + k1*c
     *       S2 = x + y + k2*c  (k1 - slow, k2 - fast; k1 < k2)
     *       S2 = 2*S1
     *    => S1 = S2 - S1 = (k2 - k1) * c
     *       x = S1 - k1*c - y = (k2 - 2k1) * c - y
     *       assert x > 0 and y > 0
     *    => k2 - 2k1 > 0
     *    => x = (k - 1)*c + (c - y)  k > 0
     *
     * 所以当到达交点时，两个指针再以同样的速度 v分别从交点和 head 出发，相交的位置即为环的起点
     *
     * since k2 - 2k1 > 0 => k2 >= 2k1 + 1
     *    => k1 = 0, k2 >= 1
     *       k1 = 1, k2 >= 3
     *       ...
     *    => k1 increases by 1, k2 increases by 2 or more
     *    => consider the actual situation: k1 can only be 0, and k2 >= 1
     *
     *    => time complexity: O(2x + y) → O(x + y)
     *
     * time complexity: if there is a ring: O(x + y), otherwise: O(n)
     * space complexity: O(1)
     */
    public static ListNode detectCycle(ListNode head) {
        ListNode slow, fast;
        slow = fast = head;

        boolean hasCyclce = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                hasCyclce = true;
                break;
            }
        }

        if (!hasCyclce) {
            return null;
        }

        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
