package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * leetcode 76
 */
public class MinimumWindowSubstring {
    /**
     * 滑动窗口
     *
     *   n = s.length(), m = t.length;
     * time complexity: O(n)
     * space complexity: O(m)
     */
    public static String minWindow(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        int left = 0;
        int count = 0, min = s.length() + 1;
        String result = "";
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            if (map.containsKey(ch)) {
                int v1 = map.get(ch);
                if (v1 > 0) {
                    count++;
                }
                map.put(ch, v1 - 1);
            }

            while (count == t.length()) {
                if (map.containsKey(s.charAt(left))) {
                    int v2 = map.get(s.charAt(left));

                    if (v2 >= 0) {
                        count--;
                    }
                    map.put(s.charAt(left), v2 + 1);

                    if (right - left + 1 < min) {
                        min = right - left + 1;
                        result = s.substring(left, right + 1);
                    }
                }
                left++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(minWindow("ADOBECODEBANC", "ABC"));
        System.out.println(minWindow("a", "a"));
    }
}
