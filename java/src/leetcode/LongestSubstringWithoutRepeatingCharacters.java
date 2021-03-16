package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * leetcode 3
 */
public class LongestSubstringWithoutRepeatingCharacters {
    /**
     * 滑动窗口
     *
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int max = 0;
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            if (set.contains(ch)) {
                max = Math.max(max, set.size());

                while (s.charAt(left) != ch) {
                    set.remove(s.charAt(left));
                    left++;
                }
                left++;
            } else {
                set.add(ch);
            }
        }
        max = Math.max(max, set.size());
        return max;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring(" "));
    }
}
