package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * leetcode 135. Candy
 * There are N children standing in a line. Each child is assigned a rating value.
 * You are giving candies to these children subjected to the following requirements:
 *   1.Each child must have at least one candy.
 *   2.Children with a higher rating get more candies than their neighbors.
 * What is the minimum candies you must give?
 *
 * Example 1:
 *   Input: [1,0,2]
 *   Output: 5
 *   Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
 *
 * Example 2:
 *   Input: [1,2,2]
 *   Output: 4
 *   Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
 *              The third child gets 1 candy because it satisfies the above two conditions.
 */
public class Candy {

    /**
     * 暴力贪心解法:
     *   时间复杂度 O(n^2)
     *   空间复杂度 O(n)
     */
    public static int candy(int[] ratings) {
        int n = ratings.length;
        int[] candy = new int[n];

        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int minIdx = min(ratings, set);
            set.add(minIdx);

            int left = minIdx - 1;
            int right = minIdx + 1;
            if (left < 0 && right >= n) {
                candy[minIdx] = 1;
            } else if (left < 0 && right < n) {
                if (ratings[minIdx] == ratings[right])
                    candy[minIdx] = 1;
                else
                    candy[minIdx] = candy[right] + 1;
            } else if (left >= 0 && right >= n) {
                if (ratings[minIdx] == ratings[left])
                    candy[minIdx] = 1;
                else
                    candy[minIdx] = candy[left] + 1;
            } else if (left >= 0 && right < n) {
                if (ratings[minIdx] == ratings[left] && ratings[minIdx] == ratings[right]) {
                    candy[minIdx] = 1;
                } else if (ratings[minIdx] == ratings[left] && ratings[minIdx] != ratings[right]) {
                    candy[minIdx] = candy[right] + 1;
                } else if (ratings[minIdx] != ratings[left] && ratings[minIdx] == ratings[right]) {
                    candy[minIdx] = candy[left] + 1;
                } else if (ratings[minIdx] != ratings[left] && ratings[minIdx] != ratings[right]) {
                    candy[minIdx] = Math.max(candy[left], candy[right]) + 1;
                }
            }
        }

        int count = 0;
        for (int i : candy)
            count += i;

        return count;
    }

    private static int min(int[] ratings, HashSet<Integer> set) {
        int n = ratings.length;

        int i = 0;
        while (set.contains(i)) {
            i++;
        }
        int min = ratings[i];
        int minIdx = i++;
        for (; i < n; i++) {
            if (set.contains(i)) continue;
            if (ratings[i] < min) {
                min = ratings[i];
                minIdx = i;
            }
        }

        return minIdx;
    }


    /**
     * 两次遍历:
     *
     * time complexity： O(n)
     * space complexity:  O(n)
     */
    public static int candy_2(int[] ratings) {
        int n = ratings.length;
        int[] candy = new int[n];
        Arrays.fill(candy, 1);

        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1])
                candy[i] = candy[i - 1] + 1;
        }

        int count = candy[candy.length - 1];
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1])
                candy[i] = Math.max(candy[i + 1] + 1, candy[i]);

            count += candy[i];
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/candy/discuss/135698/Simple-solution-with-one-pass-using-O(1)-space
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     */
    public static int candy_3(int[] ratings) {
        // first child get a candy
        int result = 1;
        int up = 0;
        int down = 0;
        int peak = 0;

        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                peak = ++up;
                down = 0;
                result += up + 1;
            } else if (ratings[i] < ratings[i - 1]) {
                up = 0;
                down++;
                result += down + 1 + (peak >= down ? -1 : 0);
            } else {
                peak = up = down = 0;
                result += 1;
            }
        }

        return result;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(Candy.class
                , new HashSet<String>() {
                    {
                        add("candy_4");
                        add("candy_3");
                        add("candy_2");
                        add("candy");
                    }
                }
                , Arrays.asList(
                        Arrays.asList(new int[]{0}),
                        Arrays.asList(new int[]{1, 0, 2}),
                        Arrays.asList(new int[]{1, 2, 2}),
                        Arrays.asList(new int[]{29, 51, 87, 87, 72, 12})
                ));
    }
}
