package sort;

/**
 * 快速排序：不稳定的排序算法
 *      核心思想: Divide and Conquer, 对数组下标从l到r的数据进行排序，选择l~r之间的任一数据作为pivot,
 *               遍历l到r的数据(除了pivot, pivot单独处理)，将小于pivot的放左边，大于等于pivot的放右边，pivot放中间。
 *
 * 时间复杂度：递归树求解
 *      最好情况(分区平衡，即将区间等分): O(nlogn)
 *      最坏情况(划分区间集中在一部分): O(n^2),
 *              优化pivot的选取，可以减少最坏情况出现的概率：
 *                  1. 三数取中法：从区间的首、尾、中间，分别取一个数，取这3个数的中间值作为分区点；(或取更多数的中间值)
 *                  2. 随机法：随机选择一个元素作为分区点
 *      平均情况: O(nlogn)
 *
 * 空间复杂度： O(1) ,  归并排序空间复杂度为O(n), 所以快排更为常用
 *
 *
 * JDK 8 中排序的实现: Arrays.sort() :
 *                      插入排序改进 => pair insertion sort
 *                      快速排序改进 => 双轴快排(pivot1, pivot2 =>  < pivot1, pivot1 <= && <= pivot2, >= pivot2)
 */
public class QuickSort {

    /**
     * 递归：
     *    递推公式： quickSort_recursion(l,r) = { quickSort_recursion(l, p), quickSort_recursion(p + 1, r) }
     *    终止条件： p - l <= 1
     *    数组index: [l,r) 左闭右开
     * @param a 待排数组
     * @param n 数组长度
     */
    public void quickSort(int[] a, int n){
        if(n <= 1) return;

        quickSort_recursion(a, 0, n);
    }

    private void quickSort_recursion(int[] a, int left, int right){

        if(right - left <= 1) return;

        int partition = partition(a, left, right);

        quickSort_recursion(a, left, partition);
        quickSort_recursion(a, partition + 1, right);
    }

    /**
     * 分区函数
     * @param a
     * @param left
     * @param right
     * @return 分区点所在index
     */
    private int partition(int[] a, int left, int right){
        int pivot = a[right - 1];

        int i = left;
        int j = left;
        for(; j < right - 1; j ++){       // thinking: 将数组分为两部分：已处理a[left, i]和未处理a[i, right],遍历未处理部分，
                                          //           满足条件加入到已处理末尾(使用交换的方式)
                                          //     pivot 可以是任意选择的，当不选择最后一个数据时，可以先与最后一个数据交换，
                                          //     再按照此方式进行划分处理。
            if(a[j] < pivot){
                Utils.swap(a, i ++, j);
            }
        }
        Utils.swap(a, i, j);        // pivot 放中间

        return i;
    }
}
