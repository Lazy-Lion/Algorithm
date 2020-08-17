package tree;

/**
 * test
 */
public class Test {

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree("123456789");
        binaryTree.Traversal();

        System.out.println();
        binaryTree = new BinaryTree("12345 6");
        binaryTree.Traversal();

        System.out.println("二分查找树：");
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        for (int i = 100; i > 0; i--) {
            binarySearchTree.insert(i);
        }

        binarySearchTree.printTree();
        System.out.println(binarySearchTree.max().val);
        System.out.println(binarySearchTree.min().val);
        System.out.println(binarySearchTree.search(39).val);

        for (int i = 20; i < 80; i++) {
            binarySearchTree.delete(i);
        }
        binarySearchTree.printTree();
        binarySearchTree.insert(6);
        binarySearchTree.printTree();
        binarySearchTree.delete(6);
        binarySearchTree.delete(10);
        binarySearchTree.printTree();

        binarySearchTree = new BinarySearchTree();
        binarySearchTree.insert(1);
        for (int i = 0; i < 10; i++)
            binarySearchTree.insert(6);
        binarySearchTree.insert(7);
        binarySearchTree.printTree();
        binarySearchTree.delete(6);
        binarySearchTree.printTree();
    }
}
