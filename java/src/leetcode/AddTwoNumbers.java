package leetcode;

import leetcode.definition.ListNode;

/**
 * leetcode 2
 */
public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 进位
        boolean isCarray = false;

        ListNode node1 = l1, node2 = l2;
        ListNode head = new ListNode(-1);
        ListNode node = head;
        while (node1 != null || node2 != null) {
            int val = isCarray ? 1 : 0;
            if (node1 != null) {
                val += node1.val;
                node1 = node1.next;
            }
            if (node2 != null) {
                val += node2.val;
                node2 = node2.next;
            }

            isCarray = val / 10 > 0 ? true : false;
            val = val % 10;
            node.next = new ListNode(val);
            node = node.next;
        }

        if (isCarray) {
            node.next = new ListNode(1);
        }

        return head.next;
    }
}
