package leetcode;

import util.Utils;

import java.util.PriorityQueue;

/**
 * leetcode 215: Kth Largest Element in an Array
 *    Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order,
 *  not the kth distinct element.
 *
 * Example:
 *   Input: [3,2,1,5,6,4] and k = 2
 *   Output: 5
 *
 * Note:
 *   You may assume k is always valid, 1 ≤ k ≤ array's length.
 */
public class KthLargestElementInAnArray {

    /**
     * 递归方式：
     *      快排思想
     *
     * time complexity: O(n)  : T(n) = n + n/2 + n/4 + n/8 + ... + 1 = 2*n - 1 = O(n)
     * space complexity: O(1) -- ignore recursion cost O(logn)
     *
     */
    public static int findKthLargest(int[] nums, int k) {
        return findKthLargest(nums, 0, nums.length - 1, k);
    }

    /**
     * 递归：
     *    递推公式：
     *              recursion(l,r,k) = {
     *                recursion(l,p - 1,k)  if p - l + 1 > k;
     *                recursion(p + 1,r, k - (p-l+1))   if p - l + 1 < k;
     *              }
     *    终止条件：
     *             p - l + 1 == k
     */
    private static int findKthLargest(int[] array, int start, int end, int k) {
        int partition = partitioning(array, start, end);

        int count = partition - start + 1; // include pivot, so need plus one
        if (count == k) {
            return array[partition];
        } else if (count < k) {
            return findKthLargest(array, partition + 1, end, k - count);
        } else {
            return findKthLargest(array, start, partition - 1, k);
        }
    }

    // 3-way-partitioning
    private static int partitioning(int[] array, int start, int end) {
        int pivot = pivot(array, start, end);

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
        return p1;
    }

    // 三数中值法
    private static int pivot(int[] array, int start, int end) {
        int middle = start + ((end - start) >>> 1);

        if (array[middle] >= array[start] && array[middle] >= array[end]) {
            return Math.max(array[start], array[end]);
        } else if (array[middle] <= array[start] && array[middle] <= array[end]) {
            return Math.min(array[start], array[end]);
        } else {
            return array[middle];
        }
    }

    /**
     * 堆方式：维护一个 大小为k的最小堆
     *
     * time complexity: O(nlogk)
     * space complexity: O(k)
     */
    public int findKthLargest_2(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(); // min heap

        for (int num : nums) {
            if (heap.size() == k) {
                if (num > heap.peek()) {
                    heap.poll();
                    heap.offer(num);
                }
            } else {
                heap.offer(num);
            }
        }

        return heap.peek();
    }
}
