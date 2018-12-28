package queue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * leetcode 862
 *
 * 问题描述：
 * Return the length of the shortest, non-empty, contiguous subarray of A with sum at least K.
 * If there is no non-empty subarray with sum at least K, return -1.
 *
 * Input: A = [1], K = 1
 * Output: 1
 *
 * Input: A = [1,2], K = 4
 * Output: -1
 *
 * Input: A = [2,-1,2], K = 3
 * Output: 3
 *
 * Note:
 * 1.1 <= A.length <= 50000
 * 2.-10 ^ 5 <= A[i] <= 10 ^ 5
 * 3.1 <= K <= 10 ^ 9
 */
public class ShortestSubarray {

    // Time Limit Exceeded: O(n^2)
    public int shortestSubarray1(int[] A, int K) {

        if(A.length == 0) return -1;

        int startindex = 0;
        int result = 50001;

        int sum = 0;

        for(int i = 0; i < A.length; i ++){
            sum += A[i];
            if(sum <= 0){
                i = startindex;
                startindex = startindex + 1;
                sum = 0;
            }else{
                if(sum >= K){
                    result = (i - startindex + 1) < result ? (i - startindex + 1) : result;

                    i = startindex;
                    sum = 0;
                    startindex = startindex + 1;
                }
            }
        }

        return result == 50001 ? -1 : result;
    }


    /**
     * Sliding Window
     *
     * 时间复杂度： O(n)
     *
     * Monotonic Queue :https://nishmathcs.wordpress.com/2017/10/08/monoqueue-or-monotonic-queue/
     *      单调队列：队列头到队列尾严格单调递增(递减)
     *   ex: 1 3 2
     *      1   1队尾入队               1
     *      3   3队尾入队               1 3
     *      2   3队尾出队(丢弃)，2入队   1 2     <由于需要队尾出队，所以使用双端队列 Deque>
     *
     *   A = [3, 1, 4, 3, 8] => monotonic queue is like [3], [3, 1], [4], [4, 3], [8]
     * @param A
     * @param K
     * @return
     */
    public int shortestSubarray(int[] A, int K) {
        int len = A.length;

        if(len == 0) return -1;

        Deque<Integer> monoq = new LinkedList<>();

        int result = len + 1;

        long[] sum = new long[len + 1];
        for(int i = 0; i < len; i ++){
            sum[i + 1] = sum[i] + A[i];
        }

        for(int i = 0; i < sum.length; i ++){
            //sum[i] <= sum[monoq.getLast() indicates that sum(A[left:right]) <= 0
            while(!monoq.isEmpty() && sum[i] <= sum[monoq.getLast()]){
                monoq.removeLast();
            }

            // sum[i] >= sum[monoq.getFirst()] + K indicates that sum(A[left:right]) >= k
            while(!monoq.isEmpty() && sum[i] >= sum[monoq.getFirst()] + K){
                result = Math.min(result, i - monoq.removeFirst());
            }

            monoq.addLast(i);
        }

        return result == len + 1 ? -1 : result;
    }
}
