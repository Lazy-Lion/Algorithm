package leetcode;

/**
 * leetcode 7
 */
public class ReverseInteger {
    public static int reverse(int x) {
        int result = 0;

        while (x != 0) {
            int pop = x % 10;
            /**
             * Integer.MAX_VALUE以 7 结尾
             * Integer.MIN_VALUE以 8 结尾
             */
            if (Integer.MAX_VALUE / 10 < result
                    || (Integer.MAX_VALUE / 10 == result && pop > 7)) {
                return 0;
            }
            if (Integer.MIN_VALUE / 10 > result
                    || (Integer.MIN_VALUE / 10 == result && pop < -8)) {
                return 0;
            }
            result = result * 10 + pop;
            x = x / 10;
        }
        return result;
    }
}
