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

    }
}
