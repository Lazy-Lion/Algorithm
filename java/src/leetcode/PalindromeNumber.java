package leetcode;

/**
 * leetcode 9
 */
public class PalindromeNumber {
    /**
     * 翻转一半数字
     *   （同样可以翻转全部，如果逸出 int，则肯定不是回文数）
     *   （同样可以转换成字符串判断，但会引入额外的空间复杂度）
     * time complexity: O(log(n))
     * space complexity: O(1)
     */
    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }

        int count = 0;
        int v1 = x, v2 = 0;
        while (v1 != 0) {
            v1 = v1 / 10;
            count++;
        }

        v1 = x;
        int c = count >> 1;
        while ((c--) > 0) {
            v2 = v2 * 10 + (v1 % 10);
            v1 = v1 / 10;
        }
        for (int i = (count >> 1); i < count; i++) {
            x = x / 10;
        }
        return x == v2;
    }

    /**
     * @see #isPalindrome(int) 与该方法思路一致，代码优化
     */
    public static boolean isPalindrome_2(int x) {
        /**
         * 负数不是回文数
         * 如果个位数是0，需要最高位数也是0，不符合题意，所以这种情况不是回文数
         */
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int revert = 0;
        while (x > revert) {
            revert = revert * 10 + x % 10;
            x = x / 10;
        }

        if (revert == x || revert / 10 == x) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(10));
        System.out.println(isPalindrome(1001));
    }
}
