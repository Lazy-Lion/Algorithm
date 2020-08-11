package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 862: Shortest Subarray with Sum at Least K
 *
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
public class ShortestSubarrayWithSumAtLeastK {
    /**
     * time complexity: O(n^2)  -- time limit exceeded
     * space complexity: O(1)
     */
    public static int shortestSubarray(int[] array, int k) {
        for (int c = 1; c <= array.length; c++) { // c - length of sub array
            int sum = 0;
            int i = 0;
            while (i < c) {
                sum += array[i++];
            }
            if (sum >= k) {
                return c;
            }
            for (int j = 1; j <= array.length - c; j++) {
                sum = sum - array[j - 1] + array[j + c - 1];
                if (sum >= k) {
                    return c;
                }
            }
        }
        return -1;
    }


    /**
     * time complexity: O(n^2) -- time limit exceeded
     * space complexity: O(1)
     */
    public static int shortestSubarray_2(int[] array, int k) {
        int minCount = Integer.MAX_VALUE;

        int start = 0;
        int sum = 0;

        for (int i = 0; i < array.length; i++) {
            sum += array[i];

            if (sum >= k) {
                int count = i - start + 1;
                minCount = Math.min(count, minCount);

                start = start + 1;
                i = start;
                sum = 0;
            } else if (sum <= 0) {
                start = start + 1;
                i = start;
                sum = 0;
            }
        }

        return minCount == Integer.MAX_VALUE ? -1 : minCount;
    }


    /**
     * Sliding Window, Monotonic Queue
     *
     * time complexity: O(n)
     * space complexity: O(n)
     *
     * Monotonic Queue : <a href="https://medium.com/@gregsh9.5/monotonic-queue-notes-980a019d5793"></a>
     *      单调队列：队列头到队列尾严格单调递增(递减)
     *   ex: 1 3 2
     *      1   1队尾入队               1
     *      3   3队尾入队               1 3
     *      2   3队尾出队(丢弃)，2入队   1 2     <由于需要队尾出队，所以使用双端队列 Deque>
     *
     *   A = [3, 1, 4, 3, 8] => monotonic queue is like [3], [1], [1,4], [1,3], [1,3,8]
     */
    public static int shortestSubarray_3(int[] array, int k) {
        int length = array.length;

        if (length == 0) return -1;

        Deque<Integer> monoQueue = new LinkedList<>();

        int minCount = length + 1;

        long[] sum = new long[length + 1];
        for (int i = 0; i < length; i++) {
            sum[i + 1] = sum[i] + array[i];
        }

        for (int i = 0; i < sum.length; i++) {
            //sum[i] <= sum[monoq.getLast() indicates that sum(A[left:right]) <= 0
            while (!monoQueue.isEmpty() && sum[i] <= sum[monoQueue.getLast()]) {
                monoQueue.removeLast();
            }

            // sum[i] >= sum[monoq.getFirst()] + K indicates that sum(A[left:right]) >= k
            while (!monoQueue.isEmpty() && sum[i] >= sum[monoQueue.getFirst()] + k) {
                minCount = Math.min(minCount, i - monoQueue.removeFirst());
            }

            monoQueue.addLast(i);
        }

        return minCount == length + 1 ? -1 : minCount;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new int[]{1});
        param.add(1);
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{1, 2});
        param.add(4);
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{2, -1, 2});
        param.add(3);
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{48, 99, 37, 4, -31});
        param.add(140);
        params.add(param);

        Utils.testStaticMethod(ShortestSubarrayWithSumAtLeastK.class
                , new HashSet<String>() {
                    {
                        add("shortestSubarray");
                        add("shortestSubarray_2");
                        add("shortestSubarray_3");
                    }
                }, params);
    }
}
