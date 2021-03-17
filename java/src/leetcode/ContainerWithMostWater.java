package leetcode;

/**
 * leetcode 11
 */
public class ContainerWithMostWater {
    /**
     * 暴力求解
     *
     * Time Limit Exceeded
     *
     * time complexity: O(n^2)
     * space complexity: O(1)
     */
    public static int maxArea(int[] height) {
        if (height.length < 2) {
            return 0;
        }

        int max = 0;
        for (int i = 0; i < height.length; i++) {
            int maxSize = 0;
            for (int j = 0; j < height.length; j++) {
                if (height[i] <= height[j]) {
                    maxSize = Math.max(Math.abs(i - j), maxSize);
                }
            }
            max = Math.max(max, maxSize * height[i]);
        }
        return max;
    }

    /**
     * 双指针
     * 思路：
     *    初始化双指针分别指向首(left)尾(right)
     *    面积大小： (right - left) * min(height[left], height[right])
     *    下一步该移动哪个指针？指针移动 (right - left)的值会减小，而高度取决于 left和right的较小值，
     *        如果移动较大值的指针，则所得的面积必然小于之前的面积，所以移动较小值的指针。
     *
     * 双指针方式正确性说明：
     *   1) 初始化: left = 0, right = height.length - 1;
     *   2) 假设 height[left] < height[right], 如果移动right,由于right位于最右侧，所以right只能向左移动，两指针距离缩小，高度必然
     *        小于或等于height[left], 所以面积必然小于之前的面积。所以可以忽略以 left为左边界的矩形面积计算。右移left指针。
     *   3) 此时判断子数组 [1,height.length), 重复进行 2)的推导过程进行子问题处理。
     *
     * time complexity: O(n)
     * space complexity: O(1)
     */
    public static int maxArea_2(int[] height) {
        if (height.length < 2) {
            return 0;
        }

        int max = 0, left = 0, right = height.length - 1;
        while (left < right) {
            if (height[left] < height[right]) {
                max = Math.max(max, height[left] * (right - left));
                left++;
            } else {
                max = Math.max(max, height[right] * (right - left));
                right--;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxArea_2(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }
}
