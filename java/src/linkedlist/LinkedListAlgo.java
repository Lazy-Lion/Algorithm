package linkedlist;

/**
 * 1) 单链表反转
 * 2) 链表中环的检测
 * 3) 两个有序的链表合并
 * 4) 删除链表倒数第n个结点
 * 5) 求链表的中间结点
 * 6) 单链表判断是否回文
 */

/**
 * 关于链表的边界条件
 * 1) 链表为空
 * 2) 只有一个节点
 * 3) 只有两个节点
 * 4) 头节点和尾节点
 */
public class LinkedListAlgo {

    /**
     * 1)链表反转
     * @param list
     * @return
     */
    public static Node reverse(Node list){
        Node head = null;

        Node previous = null;
        Node current = list;

        while(current != null){
            previous = current;
            current = current.next;

            previous.next = head;
            head = previous;
        }
        return head;
    }


    /**
     * 检查环
     * 快慢指针
     * @param list
     * @return
     */
    public static boolean checkCircle(Node list){
        if(list == null) return false;

        // 起始条件
        Node fast = list.next;
        Node slow = list;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;

            if(fast == slow) return true;
        }

        return false;
    }


    /**
     * 有序链表合并
     * @param la
     * @param lb
     * @return
     */
    public static Node mergeSortedList(Node la, Node lb){
        if(la == null) return lb;
        if(lb == null) return la;

        Node head = new Node(0, null);  //哨兵节点
        Node tail = head;

        while(la != null && lb != null){
            if(la.data <= lb.data){
                tail.next = la;   // 循环内部无需将tail.next.next置空,因为下次进入循环时会修改

                la = la.next;
            }else{
                tail.next = lb;

                lb = lb.next;
            }
            tail = tail.next;
        }

        if(la != null) tail.next = la;
        if(lb != null) tail.next = lb;

        return head.next;



    }


    /**
     * 删除倒数第k个节点
     * @param list
     * @return
     */
    public static Node deleteLastKth(Node list, int k){

        if( k < 1 ) return list;

        Node fast = list;

        int i = 1;
        while(fast != null && i < k){
            fast = fast.next;
            i ++;
        }

        if( fast == null ) return list;

        Node slow = list;
        Node prev = null;
        while(fast.next != null){   // note: 倒数第k个是从最后一个不为null的节点计算
            fast = fast.next;

            prev = slow;
            slow = slow.next;
        }

        if(prev == null){
            return slow.next;
        }else{
            prev.next = slow.next;
            slow.next = null;
        }

        return list;
    }

    /**
     * 求中间节点
     * 快慢指针
     * @param list
     * @return
     */
    public static Node findMiddleNode(Node list){
        if(list == null) return null;

        Node fast = list;
        Node slow = list;

        while(fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
    /**
     * 按照输入的数组，建立无环单向链表
     * @param array
     * @return
     */
    public static Node createLinkedList(int[] array){
        if(array.length <= 0){
            return null;
        }

        Node head = new Node(array[0],null);
        Node previous = head;

        for(int i = 1; i < array.length; i ++){
            Node node = new Node(array[i], null);
            previous.next = node;
            previous = node;
        }

        return head;
    }


    /**
     * 输出链表所有节点值
     * @param list
     */
    public static void printAll(Node list){
        if(list == null) return;

        Node node = list;
        while(node != null){
            System.out.print(node.data + " ");
            node = node.next;
        }
        System.out.println();
    }

    /**
     * 判断单链表是否回文
     * @param list
     * @return palindrome true, if not false
     */
    public static boolean palindrome(Node list){
        if(list == null) return false;
        if(list.next == null) return true;

        Node middle = findMiddleNode(list); // 中间节点

        Node rightHead = inverse(middle);
        Node right = rightHead;
        Node left = list;

        while(right != null){  // 判断条件，适应奇偶个数
            if(left.getData() != right.getData()) {
                revert(middle, rightHead);  // 先恢复链表，再返回
                return false;
            }

            left = left.next;
            right = right.next;
        }

        revert(middle, rightHead);

        return true;

    }

    /**
     * 反转当前节点后的节点
     */
    private static Node inverse(Node node){
        Node head = null;

        if(node == null || node.next == null) return null;

        Node first = node.next;
        node.next = null;
        node = first;
        while(node != null){
            Node next = node.next;
            if(head == null){
                head = node;
                node.next = null;
            }else{
                node.next = head;
                head = node;
            }
            node = next;
        }

        return head;
    }

    /**
     * 恢复链表
     * @param prev
     * @param node
     */
    private static void revert(Node prev, Node node){
        if(prev == null) return;
        prev.next = null;

        while(node != null){
            Node next = node.next;

            if(prev.next == null){
                prev.next = node;
                node.next = null;
            }else {
                node.next = prev.next;
                prev.next = node;
            }
            node = next;
        }
    }
}
