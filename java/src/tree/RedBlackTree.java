package tree;

/**
 * AVL树:
 *   wikipedia定义：
 *     In computer science, an AVL tree (named after inventors Adelson-Velsky and Landis) is a self-balancing
 *   binary search tree. It was the first such data structure to be invented.In an AVL tree, the heights of the
 *   two child subtrees of any node differ by at most one; if at any time they differ by more than one, rebalancing
 *   is done to restore this property.
 *
 *   查找、删除、插入时间复杂度： 最坏情况、平均情况都是 O(log n)
 *
 *
 * 2-3树：
 *   wikipedia定义：
 *     In computer science, a 2–3 tree is a tree data structure, where every node with children (internal node)
 *   has either two children (2-node) and one data element or three children (3-nodes) and two data elements.
 *
 *   T is a 2–3 tree if and only if one of the following statements hold:
 *     1. T is empty.
 *     2. T is a 2-node with data element a. If T has left child L and right child R, then
 *        1) L and R are non-empty 2–3 trees of the same height;
 *        2) a is greater than each element in L;
 *        3) a is less than or equal to each data element in R.
 *    3.  T is a 3-node with data elements a and b, where a < b. If T has left child L, middle child M, and right
 *        child R, then
 *        1) L, M, and R are non-empty 2–3 trees of equal height;
 *        2) a is greater than each data element in L and less than or equal to each data element in M;
 *        3) b is greater than each data element in M and less than or equal to each data element in R.
 *
 *   So 2-3 tree:
 *    1. Every internal node is a 2-node or a 3-node.
 *    2. All leaves are at the same level.
 *    3. All data is kept in sorted order.
 *
 *   2-3 树插入数据保持特性，参考图片 https://github.com/Lazy-Lion/Graphic/blob/master/dataStructure/2-3_insertion.svg
 *
 * 红黑树(Red-Black Tree):
 *   wikipedia定义：
 *     A red–black tree is a kind of self-balancing binary search tree in computer science. Each node of the
 *   binary tree has an extra bit, and that bit is often interpreted as the color (red or black) of the node. These
 *   color bits are used to ensure the tree remains approximately balanced during insertions and deletions.
 *   The balancing of the tree is not perfect, but it is good enough to allow it to guarantee searching in O(log n)
 *   time, where n is the total number of elements in the tree. The insertion and deletion operations, along with
 *   the tree rearrangement and recoloring, are also performed in O(log n) time.
 *
 *   In addition to the requirements imposed on a binary search tree the following must be satisfied by a red–black tree
 *     1. Each node is either red or black.
 *     2. The root is black. This rule is sometimes omitted. Since the root can always be changed from red to black,
 *        but not necessarily vice versa, this rule has little effect on analysis.
 *     3. All leaves (NIL, do not store data) are black.
 *     4. If a node is red, then both its children are black. (为了同时满足条件 4,5，红色节点必然有两个黑色的子节点)
 *     5. Every path from a given node to any of its descendant NIL nodes contains the same number of black nodes.
 *
 *   使用红黑树实现2-3树：
 *     1.red links : 连接两个 2-nodes 来表示一个 3-nodes (为了实现的方便，规定 red links 只能是左连接)
 *     2.black links : 2-3树常规的连接
 *
 *                      (r) - red link; (b) - black link
 *
 *                  a   b                                                        b
 *               ↙   ↓     ↘                 =>                        (r)↙        ↘(b)
 *           less   between   greater                                 a               greater than b
 *         than a   a and b   than b                            (b)↙    ↘(b)
 *                                                       less than a    between
 *                                                                      a and b
 *
 *     3.颜色表示: red link 指向的节点标记为 red，black link 指向的节点标记为 black, 根节点和叶子节点(NIL)标记为 black
 *     4.新插入的节点默认是 red节点, 且都放在叶子节点上
 */
public class RedBlackTree<K extends Comparable<K>, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;   // 颜色定义

    private Node root; // 根节点

    /**
     * @return 红黑树的节点个数
     */
    public int size(){
        return size(root);
    }

    private int size(Node node){
        if(node == null) return 0;
        return node.size;
    }

    /**
     * @return true if red-black tree is empty
     */
    public boolean isEmpty(){
        return root == null;
    }

    /**
     * @param key
     * @return the value associated with the given key if key exists, null if key does not exist
     * @throws NullPointerException if key is null
     */
    public V get(K key){
        if(key == null) throw new NullPointerException();
        return get(root, key);
    }

    private V get(Node node, K key){
        if(node == null) return null;

        while(node != null){
            int compare = key.compareTo(node.key);
            if( compare == 0)
                return node.value;
            else if( compare < 0)
                node = node.left;
            else if( compare > 0)
                node = node.right;
        }
        return null;
    }

    /**
     * @param key
     * @return true if key exists, false if does not exist
     * @throws NullPointerException if key is null
     */
    public boolean contains(K key){
        return get(key) != null;
    }

    /**
     * red-black tree insertion
     * @param key
     * @param value
     * @throws IllegalArgumentException if key is null
     */
    public void put(K key, V value){
        if(key == null) throw new IllegalArgumentException("put(key, value), key is null");

        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node put(Node r, K key, V value){
        if(r == null)
            return new Node(key, value, RED, 1);

        int compare = key.compareTo(r.key);
        if(compare == 0)
            r.value = value;
        else if(compare < 0)
            r.left = put(r.left, key, value);
        else
            r.right = put(r.right, key, value);

        //TODO: implement
        return null;
    }

    private boolean isRed(Node node){
        if(node == null) return false;
        return node.color == RED;
    }

    /**
     * make a right-leaning link lean to left
     * @param node
     * @return
     */
    private Node rotateLeft(Node node){
        // assert node != null && isRed(node.right)
        Node p = node.right;
        node.right = p.left;
        p.left = node;

        p.color = node.color;
        node.color = RED;

        p.size = node.size;
        node.size = size(node.right) + size(node.right) + 1;

        return p;
    }

    private void rotateRight(){

    }

    private void flipColor(Node node){
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    class Node { // 红黑树节点
        private K key;
        private V value;
        private Node left;
        private Node right;
        private boolean color;
        private int size;    // subtree count

        public Node(K key, V value, boolean color, int size) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.size = size;
        }
    }
}
