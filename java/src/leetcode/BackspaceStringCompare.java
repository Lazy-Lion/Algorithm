package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

/**
 * leetcode 844: Backspace String Compare
 *
 * Given two strings S and T, return if they are equal when both are typed into empty text editors. # means a backspace character.
 * Note that after backspacing an empty text, the text will continue empty.
 *
 * Example 1:
 *   Input: S = "ab#c", T = "ad#c"
 *   Output: true
 *   Explanation: Both S and T become "ac".
 * Example 2:
 *   Input: S = "ab##", T = "c#d#"
 *   Output: true
 *   Explanation: Both S and T become "".
 * Example 3:
 *   Input: S = "a##c", T = "#a#c"
 *   Output: true
 *   Explanation: Both S and T become "c".
 * Example 4:
 *   Input: S = "a#c", T = "b"
 *   Output: false
 *   Explanation: S becomes "c" while T becomes "b".
 *
 * Note:
 *   1 <= S.length <= 200
 *   1 <= T.length <= 200
 *   S and T only contain lowercase letters and '#' characters.
 *
 * Follow up:
 *   Can you solve it in O(N) time and O(1) space?
 */
public class BackspaceStringCompare {
    private static final char BACKSPACE = '#';

    /**
     * stack
     * time complexity: O(m + n)
     * space complexity: O(m + n)
     */
    public static boolean backspaceCompare(String s, String t) {
        if (s == null) {
            return t == null;
        }

        if (t == null) {
            return s == null;
        }

        Stack<Character> sStack = getStack(s);
        Stack<Character> tStack = getStack(t);

        if (sStack.size() != tStack.size()) return false;

        while (!sStack.isEmpty()) {
            if (sStack.pop() != tStack.pop()) {
                return false;
            }
        }

        return true;
    }


    private static Stack<Character> getStack(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == BACKSPACE) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(s.charAt(i));
            }
        }
        return stack;
    }

    /**
     * time complexity: O(m + n)
     * space complexity: O(1)
     */
    public static boolean backspaceCompare_2(String s, String t) {
        if (s == null) {
            return t == null;
        }

        if (t == null) {
            return s == null;
        }

        int m = s.length();
        int n = t.length();

        int i = m - 1, j = n - 1;
        int skip1 = 0, skip2 = 0;

        // back to front
        while (i >= 0 || j >= 0) {
            while (i >= 0) {   // find next valid char in s
                if (s.charAt(i) == BACKSPACE) {
                    skip1++;
                } else if (skip1 > 0) {
                    skip1--;
                } else {
                    break;
                }
                i--;
            }

            while (j >= 0) { // find next valid char in t
                if (t.charAt(j) == BACKSPACE) {
                    skip2++;
                } else if (skip2 > 0) {
                    skip2--;
                } else {
                    break;
                }
                j--;
            }

            if (i >= 0 && j >= 0 && s.charAt(i) != t.charAt(j)) { // find char: compare two chars
                return false;
            }

            if (i < 0 != j < 0) { // one found, the other not found
                return false;
            }
            i--;
            j--;
        }

        return true;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(BackspaceStringCompare.class
                , new HashSet<String>() {
                    {
                        add("backspaceCompare");
                        add("backspaceCompare_2");
                    }
                }, Arrays.asList(
                        Arrays.asList("ab#c", "ad#c"),
                        Arrays.asList("ab##", "c#d#"),
                        Arrays.asList("a##c", "#a#c"),
                        Arrays.asList("a#c", "b"),
                        Arrays.asList("aa#c", "c"),
                        Arrays.asList("xywrrmp", "xywrrmu#p"),
                        Arrays.asList("nzp#o#g", "b#nzp#o#g"),
                        Arrays.asList("nzp#o#g", "nb#nzp#o#g")
                ));
    }
}
