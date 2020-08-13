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
 *      stable: false
 *      time complexity:
 *        best: O(nlogn)
 *        worst: O(nlogn)
 *        average: O(nlogn)
 *      space complexity: O(1)
 *
 * Non-comparative sort (非比较类排序):
 *   8. bucket sort (桶排序): {@link sort.Bucket}
 *      stable: rely on the stability of sorting in each bucket
 *      time complexity: (use quick sort in each bucket)
 *        best: O(n)
 *        worst: O(n^2)
 *        average: O( n*log(n/m) )
 *      space complexity: O(n+m)
 *
 *   9. counting sort (计数排序): {@link sort.CountingSort}
 *      stable: true
 *      time complexity:
 *        best: O(n+m)
 *        worst: O(n+m)
 *        average: O(n+m)
 *      space complexity: O(n+m)
 *
 *   10. radix sort (基数排序): {@link sort.RadixSort}
 *      stable: true (using counting sort for each bit)
 *      time complexity: (using counting sort for each bit)
 *        best: O(k*n)
 *        worst: O(k*n)
 *        average: O(k*n)
 *      space complexity: O(n+m)
 */
package sort;