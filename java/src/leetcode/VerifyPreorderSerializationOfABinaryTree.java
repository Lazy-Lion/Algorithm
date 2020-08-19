package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 331. Verify Preorder Serialization of a Binary Tree
 *   One way to serialize a binary tree is to use pre-order traversal. When we encounter a non-null node, we record
 * the node's value. If it is a null node, we record using a sentinel value such as #.
 *
 *               _9_
 *             /     \
 *           3         2
 *         /  \      /   \
 *      4      1    #     6
 *    /  \    / \       /  \
 *  #     # #   #     #     #
 *   For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where # represents
 * a null node.
 *
 *   Given a string of comma separated values, verify whether it is a correct preorder traversal serialization of a
 * binary tree. Find an algorithm without reconstructing the tree.
 *
 * Each comma separated value in the string must be either an integer or a character '#' representing null pointer.
 *
 *   You may assume that the input format is always valid, for example it could never contain two consecutive commas
 * such as "1,,3".
 * Example 1:
 *   Input: "9,3,4,#,#,1,#,#,2,#,6,#,#"
 *   Output: true
 * Example 2:
 *   Input: "1,#"
 *   Output: false
 * Example 3:
 *   Input: "9,#,#,1"
 *   Output: false
 */
public class VerifyPreorderSerializationOfABinaryTree {

    /**
     * 利用栈模拟过程：
     *   从左向右遍历preorder并入栈，满足 N## 模式， 则表示一个完整节点，使用 # 替代， 直到最后栈只有一个 #
     *
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static boolean isValidSerialization(String preorder) {
        if (preorder == null) {
            return false;
        }

        String[] array = preorder.split(",");
        int size = array.length;
        if (size == 0) return false;
        if (size == 1) return "#".equals(array[0]);

        Deque<String> stack = new ArrayDeque<>(size);
        String str, top;
        for (int i = 0; i < size; i++) {
            str = array[i];
            if ("#".equals(str)) {
                if (stack.isEmpty()) return false;
                top = stack.peek();
                while ("#".equals(top)) {
                    if (stack.size() < 2) return false;
                    stack.pop();
                    stack.pop();
                    top = stack.peek(); // if deque empty, top is null
                }
                stack.push("#");
            } else {
                stack.push(str);
            }
        }
        return stack.size() == 1 && stack.pop().equals("#");
    }

    /**
     * 插槽方式：
     *   1.开始时只有一个插槽可用，可以放入数字节点(NUM)和空节点(NULL)
     *   2.每个NUM节点和NULL节点消耗一个插槽
     *   3.新增一个NULL节点不添加插槽，新增一个NUM节点添加两个子节点插槽
     *   4.直到遍历完preorder，且剩余插槽数为0
     *
     * time complexity： O(n)
     * space complexity： O(n) - split to array
     */
    public static boolean isValidSerialization_2(String preorder) {
        if (preorder == null) {
            return false;
        }

        String[] array = preorder.split(",");
        int slot = 1;
        int i = 0;
        while ((i < array.length) && slot > 0) {
            String str = array[i++];
            if ("#".equals(str)) {
                slot--;
            } else {
                slot += 1;
            }
        }
        return slot == 0 && i == array.length;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(VerifyPreorderSerializationOfABinaryTree.class
                , new HashSet<String>() {
                    {
                        add("isValidSerialization");
                        add("isValidSerialization_2");
                        add("isValidSerialization_3");
                    }
                }, Arrays.asList(
                        Arrays.asList("9,3,4,#,#,1,#,#,2,#,6,#,#"),
                        Arrays.asList("1,#"),
                        Arrays.asList("9,#,#,#,#")
                ));
    }
}
