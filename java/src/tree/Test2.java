package tree;

/**
 * test
 */
public class Test2 {
    public static void main(String[] args) {
        String test = "S E A R C H E X A M P L E";
        String[] keys = test.split(" ");
        RedBlackTree<String, Integer> rbTree = new RedBlackTree<>();
        for (int i = 0; i < keys.length; i++)
            rbTree.put(keys[i], i);

        System.out.println("size = " + rbTree.size());  // 10
        System.out.println("min  = " + rbTree.min());
        System.out.println("max  = " + rbTree.max());
        System.out.println();

        // print keys in order using allKeys()
        System.out.println("Testing keys() --------------------------------");
        for (String s : rbTree.keys())
            System.out.println(s + " " + rbTree.get(s));
        System.out.println();

        // print keys in order using select
        System.out.println("Testing printTree --------------------------------");
        rbTree.printTree();
        System.out.println();

        // delete the smallest keys
        for (int i = 0; i < rbTree.size() / 2; i++) {
            rbTree.deleteMin();
        }
        System.out.println("After deleting the smallest " + rbTree.size() / 2 + " keys -----------");
        for (String s : rbTree.keys())
            System.out.println(s + " " + rbTree.get(s));
        System.out.println();

        System.out.println("After deleting one key R --------------------------------");
        rbTree.delete("R");

        for (String s : rbTree.keys())
            System.out.println(s + " " + rbTree.get(s));
        System.out.println();

        System.out.println("After deleting the remaining keys --------------------------------");
        for (String s : rbTree.keys())
            rbTree.delete(s);

        for (String s : rbTree.keys())
            System.out.println(s + " " + rbTree.get(s));
        System.out.println();

        System.out.println("After adding back n keys --------------------------------");
        for (int i = 0; i < keys.length; i++)
            rbTree.put(keys[i], i);
        for (String s : rbTree.keys())
            System.out.println(s + " " + rbTree.get(s));

    }
}
