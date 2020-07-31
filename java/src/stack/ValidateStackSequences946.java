package stack;

import java.util.Stack;

/**
 * 946. Validate Stack Sequences
 *   Given two sequences pushed and popped with distinct values, return true if and only if this could have been the
 * result of a sequence of push and pop operations on an initially empty stack.
 *
 * Example 1:
 *   Input: pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
 *   Output: true
 *   Explanation: We might do the following sequence:
 *     push(1), push(2), push(3), push(4), pop() -> 4,
 *     push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
 *
 * Example 2:
 *   Input: pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
 *   Output: false
 *   Explanation: 1 cannot be popped before 2.
 * Note:
 *   0 <= pushed.length == popped.length <= 1000
 *   0 <= pushed[i], popped[i] < 1000
 *   pushed is a permutation of popped.
 *   pushed and popped have distinct values.
 */
public class ValidateStackSequences946 {
	/**
	 * 时间复杂度： O(n)
	 * 空间复杂度： O(n)
	 */
	public static boolean validateStackSequences(int[] pushed, int[] popped) {
		int n = pushed.length;
		int m = popped.length;

		if(n != m) return false;
		if(n == 0) return true;

		Stack<Integer> stack = new Stack<>();
		stack.push(pushed[0]);

		int i = 0, j = 1, val;  // i - index of popped; j - index of pushed
		while(!stack.isEmpty() && i < n) {
			val = stack.peek();
			if(val == popped[i]) {
				i ++;
				stack.pop();
				if(stack.isEmpty() && j < n) stack.push(pushed[j ++]);
			} else {
				if(j < n) {
					stack.push(pushed[j ++]);
				} else {
					break;
				}
			}
		}
		return i == n && j == n;
	}

	public static void main(String[] args) {
		System.out.println(validateStackSequences(new int[] {1,2,3,4,5}, new int[] {4,5,3,2,1}));
		System.out.println(validateStackSequences(new int[] {1,2,3,4,5}, new int[] {4,3,5,1,2}));
	}
}
