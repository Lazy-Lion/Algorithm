package leetcode;

/**
 * leetcode 4
 */
public class MedianOfTwoSortedArrays {
    /**
     * 归并排序合并思路
     * time complexity: O(m+n)
     * space complexity: O(1)
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;

        int sum = n + m;
        /**
         * v1, v2 中位数数值
         */
        int v1 = 0, v2 = 0;

        int idx1 = 0, idx2 = 0;
        for (int i = 0; i <= (sum >> 1); i++) {
            v1 = v2;
            if ((idx1 < n && idx2 < m)) {
                if (nums1[idx1] < nums2[idx2]) {
                    v2 = nums1[idx1];
                    idx1++;
                } else {
                    v2 = nums2[idx2];
                    idx2++;
                }
            } else if (idx1 < n) {
                v2 = nums1[idx1];
                idx1++;
            } else {
                v2 = nums2[idx2];
                idx2++;
            }
        }
        return (sum & 1) == 1 ? v2 : (v1 + v2) / 2.0;
    }

    /**
     * 查找第 k 小的数
     *   偶数： k 为 (n+m)/2, (n+m)/2 + 1
     *   奇数： k 为 (n+m)/2
     * time complexity: O(log(m+n))
     * space complexity: O(1)
     */
    public static double findMedianSortedArrays_2(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int mid = (n + m) >> 1;

        if (((n + m) & 1) == 0) {
            return (getKthElement(nums1, nums2, mid) + getKthElement(nums1, nums2, mid + 1)) / 2.0;
        } else {
            return getKthElement(nums1, nums2, mid + 1);
        }
    }

    private static int getKthElement(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        int m = nums2.length;

        int idx1 = 0, idx2 = 0;
        while (true) {
            if (idx1 == n) {
                return nums2[idx2 + k - 1];
            }
            if (idx2 == m) {
                return nums1[idx1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[idx1], nums2[idx2]);
            }

            int move = k >> 1;
            int idx1_temp = Math.min(idx1 + move, n) - 1;
            int idx2_temp = Math.min(idx2 + move, m) - 1;
            if (nums1[idx1_temp] <= nums2[idx2_temp]) {
                k -= (idx1_temp - idx1 + 1);
                idx1 = idx1_temp + 1;
            } else {
                k -= (idx2_temp - idx2 + 1);
                idx2 = idx2_temp + 1;
            }
        }
    }

    public static void main(String[] args) {
        findMedianSortedArrays_2(new int[]{2}, new int[]{});
    }
}
