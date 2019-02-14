package linkedlist;

/**
 * leetcode 23 : Merge k Sorted Lists
 *
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
 *
 * Example:
 * Input:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * Output: 1->1->2->3->4->4->5->6
 */
public class MergeSortedLists {
    /**
     * 时间复杂度: O(k * n)
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode head = new ListNode(-1);
        ListNode iter = head;

        boolean hasValue = true;
        while(hasValue){
            int minIndex = -1;
            for(int i = 0; i < lists.length; i ++){
                if(minIndex >= 0) {
                    if (lists[i] != null && lists[minIndex].val > lists[i].val)
                        minIndex = i;
                }else if(lists[i] != null){
                    minIndex = i;
                }
            }
            hasValue = minIndex == -1 ? false : true;

            if(hasValue) {
                iter.next = lists[minIndex];
                iter = iter.next;
                lists[minIndex] = lists[minIndex].next;
            }
        }
        return head.next;
    }

    /**
     * Divide and Conquer:
     *  类似归并排序，两两合并
     *  时间复杂度： O(n*logk)
     * @param lists
     * @return
     */
    public ListNode mergeKLists2(ListNode[] lists){
        int len = lists.length;

        if(len < 1) return null;

        int k, temp;
        int c = len;  // 当前需要处理的数组范围 [0, c)

        while(c > 1) {
            temp = 0;
            for (int i = 0; i <= (k = (c - 1) / 2); i ++) {
                lists[i] = i + k + 1 < c ? merge2Lists(lists[i], lists[i + k + 1]) : lists[i];
                temp ++;
            }
            c = temp;
        }
        return lists[0];
    }

    // 合并两个有序链表
    private ListNode merge2Lists(ListNode list1, ListNode list2){
        ListNode result = null;
        ListNode iter = null;

        while(list1 != null && list2 != null){
            if(list1.val <= list2.val){
                if(iter != null) {
                    iter.next = list1;
                }
                iter = list1;
                list1 = list1.next;
            }else{
                if(iter != null) {
                    iter.next = list2;
                }
                iter = list2;
                list2 = list2.next;
            }

            if(result == null)
                result = iter;
        }

        if(result == null)
            result = list1 == null ? list2 : list1;

        // 两次调用merge，实际只有一次起到连接作用
        merge(iter, list1);
        merge(iter, list2);

        return result;
    }

    /**
     * 将一个链表(list)连接到另一个链表(iter)之后
     * if list == null or iter == null, do nothing
     */
    private void merge(ListNode iter, ListNode list){
        if(list != null){
            if(iter != null) {
                iter.next = list;
            }
        }
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
