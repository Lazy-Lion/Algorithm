/**
 * sorting algorithm:
 *
 * Comparison sort (比较类排序):
 *   1. bubble sort (冒泡排序): {@link sort.BubbleSort}
 *      stable: true
 *      time complexity:
 *        best: O(n)
 *        worst: O(n^2)
 *        average: O(n^2)
 *      space complexity: O(1)
 *
 *   2. insertion sort (插入排序): {@link sort.InsertionSort}
 *      stable: true
 *      time complexity:
 *        best: O(n)
 *        worst: O(n^2)
 *        average: O(n^2)
 *      space complexity: O(1)
 *
 *   3. selection sort (选择排序): {@link sort.SelectionSort}
 *      stable: false
 *      time complexity:
 *        best: O(n^2)
 *        worst: O(n^2)
 *        average: O(n^2)
 *      space complexity: O(1)
 *
 *   4. shell sort (希尔排序): {@link sort.ShellSort}
 *      stable: false
 *      time complexity:
 *        best:  O(n)
 *        worst: O(n^2)
 *        average:
 *      space complexity: O(1)
 *
 *   5. merge sort (归并排序): {@link sort.MergeSort}
 *      stable: true
 *      time complexity:
 *        best: O(nlogn)
 *        worst: O(nlogn)
 *        average: O(nlogn)
 *      space complexity: O(n)
 *
 *   6. quick sort (快速排序): {@link sort.QuickSort}
 *      stable: false
 *      time complexity:
 *        best: O(nlogn)
 *        worst: O(n^2)
 *        average: O(nlogn)
 *      space complexity: O(1)
 *
 *   7. heap sort (堆排序): {@link heap.HeapSort}
 *      stable:
 *      time complexity:
 *        best:
 *        worst:
 *        average:
 *      space complexity:
 *
 * Non-comparative sort (非比较类排序):
 *   8. bucket sort (桶排序): {@link sort.BucketSort}
 *      stable:
 *      time complexity:
 *        best:
 *        worst:
 *        average:
 *      space complexity:
 *
 *   9. counting sort (计数排序): {@link sort.CountingSort}
 *      stable:
 *      time complexity:
 *        best:
 *        worst:
 *        average:
 *      space complexity:
 *
 *   10. radix sort (基数排序): {@link sort.RadixSort}
 *      stable: true
 *      time complexity:
 *        best:
 *        worst:
 *        average:
 *      space complexity:
 */
package sort;