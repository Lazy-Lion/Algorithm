package linkedlist;

/**
 * leetcode 141: Linked List Cycle
 *
 * Given a linked list, determine if it has a cycle in it.
 * To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in
 * the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.
 */
public class LinkedListCycle {

    /**
     * two points
     * @param head
     * @return
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
