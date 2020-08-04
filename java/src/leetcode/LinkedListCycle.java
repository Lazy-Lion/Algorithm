package leetcode;

/**
 * leetcode 141: Linked List Cycle
 *
 * Given a linked list, determine if it has a cycle in it.
 */
public class LinkedListCycle {

    /**
     * todo
     * two points
     */
    public boolean hasCycle(ListNode head) {
        if(head == null || head.next == null)
            return false;

        ListNode slow = head.next;
        ListNode fast = head.next.next;

        while(fast != null){
            if(slow == fast){
                return true;
            }
            slow = slow.next;
            fast = fast.next == null ? null : fast.next.next;
        }
        return false;
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
