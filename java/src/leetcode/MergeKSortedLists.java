package leetcode;

import leetcode.definition.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * leetcode 23 : Merge k Sorted Lists
 *
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
 *
 * Example:
 *   Input:
 *     [
 *       1->4->5,
 *       1->3->4,
 *       2->6
 *     ]
 *   Output: 1->1->2->3->4->4->5->6
 */
public class MergeKSortedLists {
    /**
     * time complexity: O(k * n)
     * space complexity: O(1)
     */
    @Deprecated
    public static ListNode mergeKLists(ListNode[] lists) {
        ListNode head = new ListNode(-1);
        ListNode tail = head;

        boolean hasValue = true;
        while (hasValue) {
            int minIndex = -1;
            for (int i = 0; i < lists.length; i++) {  // find current min node
                if (minIndex >= 0) {
                    if (lists[i] != null && lists[minIndex].val > lists[i].val)
                        minIndex = i;
                } else if (lists[i] != null) {
                    minIndex = i;
                }
            }
            hasValue = minIndex == -1 ? false : true;

            if (hasValue) {
                tail.next = lists[minIndex];
                tail = tail.next;
                lists[minIndex] = lists[minIndex].next;
            }
        }
        return head.next;
    }

    /**
     * optimize {@link MergeKSortedLists#mergeKLists(ListNode[])} by using minimum heap
     * time complexity: O(n*logk)
     * space complexity: O(k)
     */
    public static ListNode mergeKLists_2(ListNode[] lists) {
        int length = lists.length;

        if (length < 1) {
            return null;
        }

        if (length == 1) {
            return lists[0];
        }

        PriorityQueue<ListNode> heap = new PriorityQueue<>(length, Comparator.comparingInt(v -> v.val));

        ListNode head = new ListNode();
        ListNode tail = head;
        for (int i = 0; i < length; i++) {
            if (lists[i] != null) {
                heap.offer(lists[i]);
            }
        }

        while (!heap.isEmpty()) {
            ListNode node = heap.poll();
            tail.next = node;
            tail = node;
            if (node.next != null) {
                heap.add(node.next);
            }
        }

        return head.next;
    }

    /**
     * Divide and Conquer:
     *  like merge sort，pairwise merge
     *
     * time complexity: O(n*logk)
     * space complexity: O(1)
     */
    public static ListNode mergeKLists_3(ListNode[] lists) {
        int length = lists.length;

        if (length < 1) {
            return null;
        }

        int k;
        int count = length;  // [0, count): the range of the array to be processed

        while (count > 1) {
            for (int i = 0; i < (k = (count + 1) >> 1); i++) {
                lists[i] = (i << 1) + 1 < count ? merge2Lists(lists[i], lists[count - 1 - i]) : lists[i];
            }
            count = k;
        }
        return lists[0];
    }

    /**
     * merge two linked list
     */
    private static ListNode merge2Lists(ListNode list1, ListNode list2) {
        ListNode head = new ListNode();
        ListNode tail = head;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                tail = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                tail = list2;
                list2 = list2.next;
            }
        }

        if (list1 != null) tail.next = list1;
        if (list2 != null) tail.next = list2;

        return head.next;
    }
}
