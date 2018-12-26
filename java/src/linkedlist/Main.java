package linkedlist;

/**
 * test
 */
public class Main {
    public static void main(String[] args){

        System.out.println("LinkedListAlgo:");
        // 反转
        System.out.println("反转");
        Node list = LinkedListAlgo.createLinkedList(new int[]{1,2,3,4,5,6});
        LinkedListAlgo.printAll(LinkedListAlgo.reverse(list));

        list = LinkedListAlgo.createLinkedList(new int[]{});
        LinkedListAlgo.printAll(LinkedListAlgo.reverse(list));

        list = LinkedListAlgo.createLinkedList(new int[]{1});
        LinkedListAlgo.printAll(LinkedListAlgo.reverse(list));

        list = LinkedListAlgo.createLinkedList(new int[]{1,2});
        LinkedListAlgo.printAll(LinkedListAlgo.reverse(list));

        //合并
        System.out.println("合并");
        Node la = LinkedListAlgo.createLinkedList(new int[]{1,3,4,6});
        Node lb = LinkedListAlgo.createLinkedList(new int[]{1,2,3,4,5,7});
        LinkedListAlgo.printAll((LinkedListAlgo.mergeSortedList(la,lb)));

        // 删除倒数第k个节点
        System.out.println("删除倒数第k个节点");
        list = LinkedListAlgo.createLinkedList(new int[]{1,2,3,4,5,7});
        LinkedListAlgo.printAll((LinkedListAlgo.deleteLastKth(list, 3)));

        list = LinkedListAlgo.createLinkedList(new int[]{1,2,3,4,5,7});
        LinkedListAlgo.printAll((LinkedListAlgo.deleteLastKth(list, 7)));

        // 求中间节点
        System.out.println("求中间节点");
        list = LinkedListAlgo.createLinkedList(new int[]{1,2,3,4,5,6});
        System.out.println(LinkedListAlgo.findMiddleNode(list).getData());

        list = LinkedListAlgo.createLinkedList(new int[]{1,2,3,4,5});
        System.out.println(LinkedListAlgo.findMiddleNode(list).getData());

        list = LinkedListAlgo.createLinkedList(new int[]{});
        System.out.println(LinkedListAlgo.findMiddleNode(list));

        list = LinkedListAlgo.createLinkedList(new int[]{1});
        System.out.println(LinkedListAlgo.findMiddleNode(list).getData());

        list = LinkedListAlgo.createLinkedList(new int[]{1,2});
        System.out.println(LinkedListAlgo.findMiddleNode(list).getData());


        // 判读是否回文
        System.out.println("判读是否回文");
        list = LinkedListAlgo.createLinkedList(new int[]{1,2,3,4,3,2,1});
        System.out.println(LinkedListAlgo.palindrome(list));
        LinkedListAlgo.printAll(list);

        list = LinkedListAlgo.createLinkedList(new int[]{1,2,4,3,2,1});
        System.out.println(LinkedListAlgo.palindrome(list));
        LinkedListAlgo.printAll(list);

        System.out.println("-----------------------------");


        System.out.println("LRUBaseLinkedList:");
        LRUBaseLinkedList<Integer> lruList = new LRUBaseLinkedList<>(3);
        lruList.add(1);
        lruList.add(2);
        lruList.add(3);
        lruList.add(4);
        lruList.add(5);
        lruList.add(4);
        lruList.printAll();

        System.out.println("约瑟夫环,JosephCircleImp:");
        System.out.println("循环方式:");
        System.out.println(JosephCircleImp.josephCircle(7,3));
        System.out.println(JosephCircleImp.josephCircle(7,1));
        System.out.println(JosephCircleImp.josephCircle(6,2));

        System.out.println("递归方式:");
        System.out.println(JosephCircleImp.josephCircleByRecursion(7,3));
        System.out.println(JosephCircleImp.josephCircleByRecursion(7,1));
        System.out.println(JosephCircleImp.josephCircleByRecursion(6,2));

        System.out.println("链表模拟方式:");
        System.out.println(JosephCircleImp.simulate(7,3));
        System.out.println(JosephCircleImp.simulate(7,1));
        System.out.println(JosephCircleImp.simulate(6,2));

    }
}
