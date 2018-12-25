package linkedlist;

/**
 * 单链表节点定义
 */
public class Node {
    private int data;
    private Node next;

    public Node(int data, Node next){
        this.data = data;
        this.next = next;
    }

    public int getData() {
        return data;
    }

}
