package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * leetcode 20: Valid Parentheses
 *
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 *
 * An input string is valid if:
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 * Note that an empty string is also considered valid.
 *
 * Input: "()"
 * Output: true
 *
 * Input: "()[]{}"
 * Output: true
 *
 * Input: "(]"
 * Output: false
 *
 * Input: "([)]"
 * Output: false
 *
 * Input: "{[]}"
 * Output: true
 */
public class ValidParentheses {
    /**
     * stack
     *
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static boolean isValid(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }

        if (s.length() % 2 != 0) {
            return false;
        }

        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<Character, Character>() {  // 匿名内部类的声明方式，引用持有外部类引用
            {
                put(')', '(');
                put(']', '[');
                put('}', '{');
            }
        };

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (map.containsValue(c)) {
                stack.push(c);
            } else if (map.containsKey(c)) {
                if (stack.isEmpty()) return false;

                char top = stack.pop();
                if (map.get(c) != top) return false;
            } else {
                return false;
            }
        }

        return stack.isEmpty();
    }
}
