package linkedlist;

/**
 * 1) 单链表反转
 * 2) 链表中环的检测
 * 3) 两个有序的链表合并
 * 4) 删除链表倒数第n个结点
 * 5) 求链表的中间结点
 * 6) 单链表判断是否回文
 */
import leetcode.definition.ListNode;

/**
 * 关于链表的边界条件
 * 1) 链表为空
 * 2) 只有一个节点
 * 3) 只有两个节点
 * 4) 头节点和尾节点
 */
public abstract class LinkedListAlgorithm {

    /**
     * 1)单链表反转
     * @see leetcode.ReverseLinkedList#reverseList(ListNode)
     */
    public abstract Node reverse(Node head);

    /**
     * 2)求中间节点
     * @see leetcode.MiddleOfTheLinkedList#middleNode(ListNode)
     */
    public abstract Node findMiddleNode(Node head);

    /**
     * 3)检查环
     * @see leetcode.LinkedListCycle#hasCycle(ListNode)
     * @see leetcode.LinkedListCycleII#detectCycle(ListNode)
     */
    public abstract boolean checkCircle(Node head);


    /**
     * 4)合并两个有序链表
     * @see leetcode.MergeTwoSortedLists#mergeTwoLists(ListNode, ListNode)
     */
    public abstract Node mergeSortedList(Node la, Node lb);

    /**
     * 5)合并多个有序链表
     * @see leetcode.MergeKSortedLists#mergeKLists_2(ListNode[])
     */
    public abstract Node MergeKSortList(Node[] lists);

    /**
     * 5)判断单链表是否回文
     * @see leetcode.PalindromeLinkedList#isPalindrome(ListNode)
     */
    public abstract boolean palindrome(Node head);

    /**
     * 6)删除倒数第k个节点
     * @see leetcode.RemoveNthNodeFromEndOfList#removeNthFromEnd(ListNode, int)
     */
    public abstract Node deleteLastKth(Node list, int k);
}
