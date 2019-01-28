package tree;

/**
 * test
 */
public class Main {

    public static void main(String[] args){
        BinaryTree binaryTree = new BinaryTree("123456789");
        binaryTree.Traversal();

        System.out.println();
        binaryTree = new BinaryTree("12345 6");
        binaryTree.Traversal();
    }
}
