package linkedlist;


/**
 * 基于单链表实现LRU
 * LRU : least recently used
 */
public class LRUBaseLinkedList<T> {
    private final static int DEFAULT_CAPACITY = 10;  // 默认容量

    private SNode<T> head;   // 头节点

    private int size = 0;  // 当前链表长度

    private int capacity;  // 链表容量

    public LRUBaseLinkedList(){
        this.capacity = DEFAULT_CAPACITY;
    }

    public LRUBaseLinkedList(int capacity){
        this.capacity = capacity;
    }

    /**
     * 添加节点
     * 1) 数据已存在，删除存在的节点，新增节点到head;
     * 2) 如果数据不存在，容量已满，删除尾节点，新增节点到head;
     * 3) 如果数据不存在，容量未满，新增节点到head;
     * @param data
     */
    public void add(T data){
        if(head == null){
            head = new SNode<>(data, null);
            size ++;
        }

        SNode<T> preNode = findPreNode(data);

        if(head.getElement().equals(data)){
            return;
        }else if(preNode != null){
            deleteElement(preNode);
        }else{
            if(size >= capacity) {
                deleteElementAtEnd();
            }
        }
        insertElementAtBegin(data);
    }


    /**
     * 获取数据等于data的节点的前一个节点
     * @param data
     * @return
     */
    private SNode<T> findPreNode(T data){

        SNode<T> node = head;

        while( node.getNext() != null ){
            if(node.getNext().getElement().equals(data)){
                return node;
            }
            node = node.getNext();
        }

        return null;
    }

    private void deleteElement(SNode<T> preNode){
        SNode<T> node = preNode.getNext();
        preNode.setNext(node.getNext());
        node = null;
        size --;
    }

    /**
     * 链表头部插入节点
     * @param data
     */
    private void insertElementAtBegin(T data){
        SNode<T> node = new SNode<>(data, head);
        head = node;
        size ++;
    }

    /**
     * 删除尾节点
     */
    private void deleteElementAtEnd(){
        if(size <= 0){
            return;
        }

        SNode<T> node = head;
        SNode<T> prev = null;

        while(node.getNext() != null){
            prev = node;
            node = node.getNext();
        }

        if(prev == null){
            head = null;
        }else{
            prev.setNext(null);
            size --;
        }
    }

    /**
     * 输出链表数据
     */
    public void printAll(){
        SNode<T> node = head;

        while( node != null){
            System.out.print(node.getElement() + " ");
            node = node.getNext();
        }

        System.out.println();
    }
}
