package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * leetcode 268
 */
public class MissingNumber {
    /**
     * way 1 : 数学方法
     *
     * time complexity: O(n)
     * space complexity: O(1)
     */
    public static int missingNumber(int[] nums) {
        int n = nums.length;
        long sum = (1 + n) * n / 2;
        long result = 0;
        for (int num : nums) {
            result += num;
        }

        return (int) (sum - result);
    }

    /**
     * way 2 : 异或运算
     *   两个相同的数异或运算结果是 0
     *   0和任意数N异或运算结果是 N
     */
    public static int missingNumber_2(int[] nums) {
        int n = nums.length;

        int v = 0;
        for (int i = 1; i <= n; i++) {
            v ^= i ^ nums[i - 1];
        }
        return v;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(MissingNumber.class, Arrays.asList(
                Arrays.asList(new int[]{3, 0, 1}),
                Arrays.asList(new int[]{0, 1}),
                Arrays.asList(new int[]{9, 6, 4, 2, 3, 5, 7, 0, 1}),
                Arrays.asList(new int[]{0})
        ));
    }

}
