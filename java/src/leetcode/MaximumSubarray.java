package leetcode;

/**
 * leetcode 53
 */
public class MaximumSubarray {
    /**
     * dp[i][j] = max(dp[i][j-1] + nums[j], nums[j])
     *
     * way 1: Memory Limit Exceeded
     */
    public static int maxSubArray(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];

        int max = nums[0];
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    dp[i][j] = nums[i];
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1] + nums[j], nums[j]);
                }
                max = Math.max(dp[i][j], max);
            }
        }
        return max;
    }

    /**
     * way 2 : 优化内存空间
     *
     * dp[i][j] = max(dp[i][j-1] + nums[j], nums[j])
     * 数组只需要维护一行的状态 => 一维数组
     *
     * time complexity: O(n^2)
     * space complexity: O(n)
     */
    public static int maxSubArray_2(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        dp[0] = nums[0];
        int max = nums[0];
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    dp[j] = nums[i];
                } else {
                    dp[j] = Math.max(dp[j - 1] + nums[j], nums[j]);
                }
                max = Math.max(dp[j], max);
            }
        }
        return max;
    }

    /**
     * way 3: 空间再优化
     *
     * dp[i][j] = max(dp[i][j-1] + nums[j], nums[j])
     *
     * 实际上只需要维护包含 j-1 时最大的子数组和。
     *
     * time complexity: O(n)
     * space complexity: O(1)
     */
    public static int maxSubArray_3(int[] nums) {
        int max, prev;
        max = nums[0];
        prev = 0;

        for (int num : nums) {
            prev = Math.max(prev + num, num);
            max = Math.max(max, prev);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(maxSubArray_2(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(maxSubArray_3(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }
}
