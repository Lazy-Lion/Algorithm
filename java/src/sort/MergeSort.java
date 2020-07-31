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
     *  终止条件：r - l == 1
     *  note: 关于数组index, 左闭右开[l,r)
     * @param a 待排序数组
     * @param n 数组长度
     */
    public void mergeSort(int[] a, int n){
        if(n <= 1) return;

        mergeSort_recursion(a, 0, n);
    }

    private void mergeSort_recursion(int[] a, int left, int right){
        if(right <= left + 1) return;

        int middle = left + (right - 1 - left) / 2 + 1;

        mergeSort_recursion(a, left, middle);
        mergeSort_recursion(a, middle, right);

        merge(a, left, middle, right);
    }

    private void merge(int[] a, int left, int middle, int right){
        int[] temp = new int[right - left];  // 合并操作使用了额外内存

        int i = left;
        int j = middle;
        int idx = 0;

        while( i < middle && j < right){
            if(a[i] > a[j]){            // 保证稳定性
                temp[idx ++] = a[j ++];
            }else{
                temp[idx ++] = a[i ++];
            }
        }

        while( i < middle){
            temp[idx ++] = a[i ++];
        }

        while( j < right){
            temp[idx ++] = a[j ++];
        }


        for( i = 0; i < temp.length; i ++){
            a[left + i] = temp[i];
        }
    }
}
