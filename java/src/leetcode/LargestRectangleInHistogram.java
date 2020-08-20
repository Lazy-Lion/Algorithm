package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 84: Largest Rectangle in Histogram
 *
 *   Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.
 *
 * Example:
 *   Input: [2,1,5,6,2,3]
 *   Output: 10
 */
public class LargestRectangleInHistogram {
    /**
     * worst time complexity: O(n^2)
     * space complexity: O(n)
     */
    public static int largestRectangleArea(int[] heights) {
        if (heights.length == 0) return 0;
        if (heights.length == 1) return heights[0];

        int[] reconcile = new int[heights.length]; // not-strictly increasing
        reconcile[0] = heights[0];
        int max = 0;
        for (int i = 1; i < heights.length; i++) {
            if (heights[i] >= reconcile[i - 1]) {
                reconcile[i] = heights[i];
            } else {
                int k = i - 1;
                while (k >= 0 && heights[i] < reconcile[k]) { // find the first value  reconcile[k] <= heights[i]
                    k--;
                }
                k++;

                for (int j = k; j <= i; j++) { // calculate the max area between k and i
                    max = Math.max(reconcile[j] * (i - j), max);
                }

                for (int j = k; j <= i; j++) { // set the value to height[i]
                    reconcile[j] = heights[i];
                }
            }
        }

        for (int i = 0; i < reconcile.length; i++) {
            max = Math.max(reconcile[i] * (reconcile.length - i), max);
        }

        return max;
    }

    /**
     * 对于一个柱体，向左扩展直到左侧的柱体高低小于当前，向右扩展直到右侧的柱体高度小于当前
     * 维护一个单调递增栈，栈内存储数组下标值
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static int largestRectangleArea_2(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int max = 0;
        int size = heights.length;
        for (int i = 0; i < size; i++) {
            if (stack.isEmpty() || heights[stack.peek()] <= heights[i]) {
                stack.push(i);
            } else {
                while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                    int index = stack.pop();
                    if (stack.isEmpty()) {
                        max = Math.max(i * heights[index], max);
                    } else {
                        max = Math.max((i - stack.peek() - 1) * heights[index], max);
                    }
                }
                stack.push(i);
            }
        }

        while (!stack.isEmpty()) {
            int index = stack.pop();
            if (stack.isEmpty()) {
                max = Math.max(size * heights[index], max);
            } else {
                max = Math.max((size - stack.peek() - 1) * heights[index], max);
            }
        }
        return max;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new int[]{2, 1, 5, 6, 2, 3});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{1, 1});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{2, 1, 2});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{2, 1, 0, 2});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{4, 2, 0, 3, 2, 5});  // 0 0 0 2 2 5
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{5, 4, 1, 2});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{2, 1, 5, 6, 2, 3});
        params.add(param);

        Utils.testStaticMethod(LargestRectangleInHistogram.class
                , new HashSet<String>() {
                    {
                        add("largestRectangleArea");
                        add("largestRectangleArea_2");
                    }
                }, params);
    }
}
