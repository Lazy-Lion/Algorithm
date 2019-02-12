package linkedlist;

/**
 * leetcode 142: Linked List Cycle II
 *
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
 * To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in
 * the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.
 *
 * Note: Do not modify the linked list.
 */
public class LinkedListCycleII {

    /**
     * two points : 快慢指针
     *
     * 1 → 2 → 3
     *     ↖  ↓
     *        4
     * 如上链表： 假设 快慢指针速度分别为 2v,v; 1->2 距离为 x, 3 为两个指针相遇的位置，2->3距离为 y, 2->3->4->2 环的长度为 c
     *    => 2*(x + y) = n*c + x + y  (慢指针必然没有跑完一圈) => x = n*c - y
     * 所以当到达交点时，两个指针以同样的速度分别从交点和 head 出发，相交的位置即为环的起点
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        if(head == null || head.next == null)
            return null;

        ListNode slow = head.next;
        ListNode fast = head.next.next;
        ListNode intersect = null;

        while(fast != null){
            if(slow == fast) {
                intersect = slow; break;
            }
            slow = slow.next;
            fast = fast.next == null ? null : fast.next.next;
        }

        if(intersect == null) return null;

        fast = head;
        while(fast != slow){
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
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
