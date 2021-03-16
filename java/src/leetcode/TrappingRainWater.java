package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * leetcode 42
 */
public class TrappingRainWater {
    /**
     * way 1: 栈
     * 思路：使用栈来跟踪储水的位置
     * 从左向右遍历：
     *   1. if 栈为空：入栈
     *   2. if 栈不为空：如果当前值小于等于栈底，入栈；否则表示可以计算当前位置到栈底位置的储水量（出栈计算，计算完成之后将当前值入栈）
     * 遍历完成之后，如果栈中元素个数大于1，表示栈底值为栈中元素的最大值，从左到右无法处理，可将这一部分从右到左再处理一次，计算储水量。
     *
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static int trap(int[] height) {
        Deque<Integer> stack = new ArrayDeque<>();

        int result = 0;
        for (int i = 0; i < height.length; i++) {
            result = calculate(height, stack, i, result);
        }

        if (!stack.isEmpty()) {
            int size = stack.size();
            stack.clear();
            for (int i = height.length - 1; i > height.length - 1 - size; i--) {
                result = calculate(height, stack, i, result);
            }
        }

        return result;
    }

    private static int calculate(int[] height, Deque<Integer> stack, int index, int result) {
        if (stack.isEmpty()) {
            stack.push(height[index]);
        } else {
            if (height[index] >= stack.peekLast()) {
                int size = 0, count = 0, top = 0;
                while (!stack.isEmpty()) {
                    top = stack.pop();
                    size++;
                    count += top;
                }
                result += (top * size) - count;
                stack.push(height[index]);
            } else {
                stack.push(height[index]);
            }
        }
        return result;
    }

    /**
     * 栈：单调递减栈 stack
     *
     * 按层累加结果
     */
    public static int trap_2(int[] height) {
        int result = 0;
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int left = stack.peek();
                result += (i - left - 1) * (Math.min(height[i], height[left]) - height[top]);
            }
            stack.push(i);
        }
        return result;
    }

    /**
     * way 3：
     * 从左向右遍历，分别计算每个位置的储水量 v
     *      当前位置高度为  current, 左侧最大高度为 left_max, 右侧最大高度 right_max
     *      min = min(left_max, right_max)
     *      if  min <= current, v = 0
     *      else ,              v = min - current
     *
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static int trap_3(int[] height) {
        int length = height.length;

        if (length == 0) {
            return 0;
        }

        int[] leftMax = new int[length];
        int[] rightMax = new int[length];

        leftMax[0] = 0;
        for (int i = 1; i < length; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i - 1]);
        }
        rightMax[0] = 0;
        for (int i = length - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i + 1]);
        }

        int result = 0, min;
        for (int i = 1; i < length - 1; i++) {
            min = Math.min(leftMax[i], rightMax[i]);
            if (min > height[i]) {
                result += min - height[i];
            }
        }
        return result;
    }

    /**
     * 双指针
     *
     * 思路：
     *    双指针分别指向数组的首(left)尾(right)
     *    1) if height[left] < height[right] , 则当前位置可以装的雨水最大高度取决于左侧
     *       如果 height[left] > leftMax, 当前位置没法装水， leftMax = height[left]
     *       否则, 当前位置装的水取决于 leftMax的高度，装水量 leftMax - height[left]
     *    2) if height[left] > height[right], 则当前位置可以装的雨水最大高度取决于右侧
     *       如果 height[right] > rightMax, 当前位置没法装水， rightMax = height[right]
     *       否则，当前位置装的水取决于 rightMax的高度，装水量 rightMax - height[right]
     *
     *
     * time complexity: O(n)
     * space complexity: O(1)
     */
    public static int trap_4(int[] height) {
        if (height.length <= 1) {
            return 0;
        }

        int left = 0, right = height.length - 1, ans = 0;

        int leftMax = height[0];
        int rightMax = height[right];

        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] > leftMax) {
                    leftMax = height[left];
                } else {
                    ans += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] > rightMax) {
                    rightMax = height[right];
                } else {
                    ans += rightMax - height[right];
                }
                right--;
            }
        }
        return ans;


    }

    public static void main(String[] args) {
//        System.out.println(trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
//        System.out.println(trap(new int[]{4, 1, 2, 3}));
//
//        System.out.println(trap_2(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        System.out.println(trap_2(new int[]{4, 1, 2, 3}));

        System.out.println(trap_3(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        System.out.println(trap_3(new int[]{4, 1, 2, 3}));
        System.out.println(trap_3(new int[]{}));

        System.out.println(trap_4(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        System.out.println(trap_4(new int[]{4, 1, 2, 3}));
        System.out.println(trap_4(new int[]{}));
    }
}
