package leetcode;

import util.Utils;

import java.util.Arrays;

/**
 * leetcode 324: Wiggle Sort II
 *   Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
 *
 * Example 1:
 *   Input: nums = [1, 5, 1, 1, 6, 4]
 *   Output: One possible answer is [1, 4, 1, 5, 1, 6].
 * Example 2:
 *   Input: nums = [1, 3, 2, 2, 3, 1]
 *   Output: One possible answer is [2, 3, 1, 3, 1, 2].
 *
 * Note:
 *   You may assume all input has valid answer.
 *
 * Follow Up:
 *   Can you do it in O(n) time and/or in-place with O(1) extra space?
 */
public class WiggleSortII {
    /**
     * 时间复杂度 ：依赖于排序的复杂度，O(nlogn)
     * 空间复杂度： O(n)
     */
    public static void wiggleSort(int[] nums) {
        // assume all input has valid answer
        Arrays.sort(nums);

        int[] temp = Arrays.copyOf(nums, nums.length);
        int middle = (nums.length - 1) >>> 1;
        int p1 = middle;
        int p2 = nums.length - 1;
        int idx = 0;
        while (p1 >= 0) {
            temp[idx++] = nums[p1--];
            if (p2 > middle) {
                temp[idx++] = nums[p2--];
            }
        }

        idx = 0;
        while (idx < nums.length) {
            nums[idx] = temp[idx++];
        }
    }

    /**
     * time complexity: O(n)
     * space complexity: O(logn) -- recursion cost O(logn)
     *
     * step 1: 求解中位数，并获得 Dutch National Flag (DNF) 排序结果
     * step 2: 根据 Dutch National Flag (DNF) 得到结果
     */
    public static void wiggleSort_2(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return;
        }

        int middle = median(nums);

        /** L - 大于中位数； M - 等于中位数； S - 小于中位数
         *                          L  L  L  M  M  S  S
         *  计算完中位数的 nums下标  0  1  2  3  4  5  6
         *  符合要求的结果          3  0  4  1  5  2  6
         *
         *  符合要求的结果满足以下条件：
         *     1. 奇数位置优先放置大于中位数的元素
         *     2. 偶数位置优先放置小于中位数的元素
         *     3. 等于中位数的元素间距尽量大
         *
         * 期望的结果： L = {0,1,2}; M = {3,4}; S = {5,6}
         *  L = {0,1,2} -> {1,3,5}
         * 	M = {5,6} -> {4,6}
         * 	S = {3,4}  -> {0,2}
         *
         * 	  从1开始的奇数位置填充L中的元素；从最大偶数索引开始的偶数位置填充S中的元素
         * 	  未处理的空位用M中的元素填充
         *
         * 	=> 结果集按照 1 3 5 0 2 4 6 的索引顺序排列，满足 LMS （获得上述索引顺序参照 {@link WiggleSortII#index(int, int)}）
         *    索引映射问题转换： 原数组 [0(1),1(3),2(5),3(0),4(2),5(4),6(6)],按照LMS排序
         *       => Dutch National Flag (DNF) 荷兰国旗问题 => 3 way partition
         */
        // index(n, i) indicate the position where the next number greater than median is stored
        // index(n, j) indicate the position where the next number less than median is stored
        int i = 0, j = n - 1;
        int k = 0;
        while (k <= j) { // traverse array with index(n,k)
            if (nums[index(n, k)] > middle) {
                Utils.swap(nums, index(n, k++), index(n, i++));
            } else if (nums[index(n, k)] < middle) {
                Utils.swap(nums, index(n, k), index(n, j--));
            } else {
                k++;
            }
        }
    }

    // find the median
    private static int median(int[] nums) {
        int n = nums.length;

        int k = ((n - 1) >>> 1) + 1; // 第k个最小数即为中位数

        return partitioning(nums, 0, n - 1, k);
    }

    private static int index(int n, int i) {
        // 1)若 n 为偶数: n = 6
        //   原索引序列： 0 1 2 3 4 5
        //   映射序列：   1 3 5 0 2 4       映射函数 idx = (2*i+1)%(n+1)
        // 2)若 n 为奇数：n = 7
        //   原索引序列： 0 1 2 3 4 5 6
        //   映射序列：   1 3 5 0 2 4 6     映射函数 idx = (2*i+1)%n
        // => 合并奇偶数 idx = ( 2 * i + 1 ) % (n | 1)
        return (2 * i + 1) % (n | 1);
    }

    private static int partitioning(int[] array, int start, int end, int k) {
        // 3-way-partition
        int pivot = array[start];
        int p1 = start, p2 = end;
        int i = start;
        while (i <= p2) {
            if (array[i] > pivot) {
                Utils.swap(array, i++, p1++);
            } else if (array[i] < pivot) {
                Utils.swap(array, i, p2--);
            } else {
                i++;
            }
        }

        int count = p1 - start + 1; // include pivot, so need plus 1
        if (count == k) {
            return pivot;
        } else if (count > k) {
            return partitioning(array, start, p1 - 1, k);
        } else {
            return partitioning(array, p1 + 1, end, k - count);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 5, 1, 1, 6, 4};
        wiggleSort(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{1, 3, 2, 2, 3, 1};
        wiggleSort(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{2, 1};
        wiggleSort(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{4, 5, 5, 6};
        wiggleSort(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{1, 5, 1, 4, 2};
        wiggleSort(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{1, 3, 2, 2, 2, 1, 1, 3, 1, 1, 2};
        wiggleSort(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{1, 3, 2, 2, 3, 1, 2};
        wiggleSort_2(nums);
        System.out.println(Arrays.toString(nums));

        System.out.println("-----------");
        nums = new int[]{1, 5, 1, 1, 6, 4};
        wiggleSort_2(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{1, 3, 2, 2, 3, 1};
        wiggleSort_2(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{2, 1};
        wiggleSort_2(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{4, 5, 5, 6};
        wiggleSort_2(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{1, 5, 1, 4, 2};
        wiggleSort_2(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{1, 3, 2, 2, 2, 1, 1, 3, 1, 1, 2};
        wiggleSort_2(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{1, 3, 2, 2, 3, 1, 2};
        wiggleSort_2(nums);
        System.out.println(Arrays.toString(nums));


    }
}
