package sort;

/**
 * 归并排序：稳定的排序算法
 *   核心思想：Divide and Conquer,将待排序数组从中间分成前后两个部分，然后对前后两部分分别排序，
 *          最后将排好序的两部分合并在一起。
 *
 * 时间复杂度：最好、最坏、平均情况都是O(nlogn)，与待排数组有序程度无关   (使用递归树求解: 递推公式T(n) = 2(T(n/2) + n ))
 *
 * 空间复杂度：O(n) :  递归代码的空间复杂度不能像时间复杂度那样累加。
 *                   尽管每次合并操作都需要申请额外的内存空间，但合并完成之后，临时开辟的内存空间就被释放掉了，在任意时刻，cpu
 *                   只会有一个函数在执行，也就是只会有一个临时的内存空间在使用，临时内存空间最大不会超过 n
 */
public class MergeSort {
    /**
     * 递归：
     *  递推公式：mergeSort_recursion(l,r) = merge(mergeSort_recursion(l,m), mergeSort_recursion(m+1, r))
     *  终止条件：r == l
     *  note: 关于数组index, 左右均包含[l,r]
     *
     * @param array 待排序数组
     * @param n     数组长度
     */
    public static void mergeSort(int[] array, int n) {
        recursion(array, 0, n - 1);
    }

    private static void recursion(int[] array, int start, int end) {
        if (end <= start) {
            return;
        }

        int middle = start + ((end - start) >> 1);
        recursion(array, start, middle); // left
        recursion(array, middle + 1, end); // right
        merge(array, start, end, middle); // merge
    }

    private static void merge(int[] array, int start, int end, int middle) {
        int[] temp = new int[end - start + 1];  // 合并操作使用了额外内存

        int i = start;
        int j = middle + 1;
        int k = 0;
        while (i <= middle || j <= end) {
            if (i <= middle && j <= end) {
                if (array[i] <= array[j]) { // 保证稳定性
                    temp[k++] = array[i++];
                } else {
                    temp[k++] = array[j++];
                }
            } else if (i <= middle) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        for (i = 0; i < temp.length; i++) {
            array[start++] = temp[i];
        }
    }
}
