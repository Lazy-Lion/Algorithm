package tree;

import java.util.Stack;

/**
 * 331. Verify Preorder Serialization of a Binary Tree
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
public class VPSOABT331 {

	/**
	 * 利用栈模拟过程：
	 *   从左向右遍历preorder并入栈，满足 N## 模式， 则表示一个完整节点，使用 # 替代， 直到最后栈只有一个 #
	 */
	public static boolean isValidSerialization(String preorder) {
		String[] array = preorder.split(",");
		if(array.length == 0) return false;
		if(array[0] == "#") {
			if(array.length == 1) return true;
			return false;
		}

		Stack<String> stack = new Stack<>();
		stack.push(array[0]);
		String c, sc;
		for (int i = 1; i < array.length; i ++) {
			c = array[i];
			if(c.equals("#")) {
				if(stack.isEmpty()) return false;
				sc = stack.peek();
				while(sc.equals("#")) {
					if(stack.size() < 2) return false;
					stack.pop();
					stack.pop();
					if(!stack.isEmpty()) sc = stack.peek();
					else sc = "";
				}
				stack.push("#");
			} else {
				stack.push(c);
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
	 * 时间复杂度： O(n)
	 * 空间复杂度： O(n)
	 */
	public static boolean isValidSerializationOptimize(String preorder) {
		int slot = 1;
		String[] array = preorder.split(",");
		int i = 0;
		for(; slot > 0 && i < array.length; i ++) {
			slot --;
			if(!array[i].equals("#")) {
				slot += 2;
			}
		}
		return slot == 0 && i == array.length;
	}

	public static void main(String[] args) {
		System.out.println(isValidSerialization("9,3,4,#,#,1,#,#,2,#,6,#,#"));
		System.out.println(isValidSerialization("1,#"));
		System.out.println(isValidSerialization("9,#,#,#,#"));

		System.out.println(isValidSerializationOptimize("9,3,4,#,#,1,#,#,2,#,6,#,#"));
		System.out.println(isValidSerializationOptimize("1,#"));
		System.out.println(isValidSerializationOptimize("9,#,#,#,#"));
	}
}
