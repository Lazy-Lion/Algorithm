package sort;

import java.util.PriorityQueue;

/**
 * leetcode 215
 * 问题描述：
 *  Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order,
 *  not the kth distinct element.
 *
 *  Example:
 * Input: [3,2,1,5,6,4] and k = 2
 * Output: 5
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ array's length.
 */
public class KthLargest {

    /**
     * 递归方式：
     *      快排思想
     *
     * 时间复杂度：O(n)  : T(n) = n + n/2 + n/4 + n/8 + ... + 1 = 2*n - 1 = O(n)
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {

        return  KthLargest_recursion(nums, 0, nums.length, k);
    }

    /**
     * 递归：
     *    递推公式：
     *              KthLargest_recursion(l,r,k) = {
     *                KthLargest_recursion(l,p,k)  if p - l + 1 > k;
     *                KthLargest_recursion(p+1,r, k - (p-l+1))   if p - l + 1 < k;
     *              }
     *    终止条件：
     *             p - l + 1 == k
     * @param nums
     * @param left
     * @param right
     * @param k
     * @return
     */
    private int KthLargest_recursion(int[] nums, int left, int right, int k){

        int partition = partition(nums, left, right);

        int count = partition - left + 1;

        if( count == k ){
            return nums[partition];
        }else if(count > k ){
            return KthLargest_recursion(nums, left, partition, k);
        }else{
            return KthLargest_recursion(nums, partition + 1, right, k - count);
        }
    }

    private int partition(int[] nums, int left, int right){
        Utils.swap(nums, median3(nums, left, right - 1), right - 1);

        int pivot = nums[right - 1];

        int i = left;
        int j = left;

        for(; j < right - 1; j ++){
            if(nums[j] > pivot){
                Utils.swap(nums, i ++, j);
            }
        }
        Utils.swap(nums, i, j);

        return i;
    }

    /**
     * 优化pivot的选择
     * median3: 三数中值法，使得nums[left], nums[right], nums[middle] 按照大小顺序排列
     * @param nums
     * @param left
     * @param right
     * @return
     */
    private int median3(int[] nums, int left, int right){
        int middle = left + (right - left) / 2;

        if(nums[right] < nums[left]) Utils.swap(nums,left,right);

        if(nums[middle] < nums[left]) Utils.swap(nums,left,right);

        if(nums[right] < nums[middle]) Utils.swap(nums,middle,right);

        return middle;
    }

    /**
     * 堆方式：维护一个 k 大小的最小堆
     *
     * 时间复杂度: O(nlogk)
     */
    public int findKthLargest_heap(int[] nums, int k){
        PriorityQueue<Integer> heap = new PriorityQueue<>((n1,n2) -> n1 - n2);

        for(int n : nums){
            if(heap.size() < k) {
                heap.add(n);
            }else{
                if(heap.peek() >= n){
                    continue;
                }else{
                    heap.poll();
                    heap.add(n);
                }
            }
        }
        return heap.poll();
    }
}
