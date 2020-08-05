package linkedlist;

/**
 * test
 */
public class Main {
    public static void main(String[] args) {
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
        System.out.println(JosephCircleImp.josephCircle(7, 3));
        System.out.println(JosephCircleImp.josephCircle(7, 1));
        System.out.println(JosephCircleImp.josephCircle(6, 2));

        System.out.println("递归方式:");
        System.out.println(JosephCircleImp.josephCircleByRecursion(7, 3));
        System.out.println(JosephCircleImp.josephCircleByRecursion(7, 1));
        System.out.println(JosephCircleImp.josephCircleByRecursion(6, 2));

        System.out.println("链表模拟方式:");
        System.out.println(JosephCircleImp.simulate(7, 3));
        System.out.println(JosephCircleImp.simulate(7, 1));
        System.out.println(JosephCircleImp.simulate(6, 2));

    }
}
