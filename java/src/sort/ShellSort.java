package sort;

/**
 * 希尔排序：也称递减增量排序算法，是插入排序的改进版本，不稳定
 *
 *     希尔排序基于插入排序的以下性质提出改进：
 *          1. 插入排序对几乎已经排好序的数据操作时，效率高，可以达到线性排序的效率
 *          2. 插入排序一般来说是低效的，因为每次只能将数据移动一位
 *
 *     针对以上性质，希尔排序的改进：
 *         1.选取一个较大的初始步长，进行插入排序，保证当前步长下有序
 *         2.减小步长，重复上述操作 (小步长排好序之后，依然能保证大步长有序)
 *         3.最后一步，步长为1，确保排好序 (此时就是简单的插入排序)
 *
 *       如: [ 13 14 94 33 82 25 59 94 65 23 45 27 73 25 39 10 ]
 *         13 14 94 33 82             10 14 73 25 23
 *         25 59 94 65 23      ==>    13 27 94 33 39   ==>  [10 14 73 25 23 13 27 94 33 39 25 59 94 65 82 45]
 *         45 27 73 25 39             25 59 94 65 82
 *         10                         45
 *
 *       希尔排序步长的选择很关键。
 *
 * 时间复杂度：不同步长时间复杂度不同, 以 step = n/(2^i)为例
 *      最坏情况： worse-case time is no worse than quadratic
 *              time = n^2 + 2*(n/2)^2 + 4*(n/4)^2 + ... = n^2 * (1 + 1/2 + 1/4 + 1/8 + ...) <= 2 * n^2
 *      最好情况(有序)： best-case time
 *              time = n + 2*(n/2) + 4*(n/4) + ... <= nlogn
 *
 *
 * 空间复杂度：O(1)
 */
public class ShellSort {

    /**
     * 以 step = n/(2^i) 为例
     */
    public void shellSort(int[] a, int n){

        if(n <= 1) return;

        int step = n / 2;

        while(step >= 1){
            for(int i = step; i < n; i += 1){  // note: i += 1，如上分析，保证每列都排好序
                int value = a[i];

                int j = i - step;
                for(; j >=0; j -= step){     // note: j -= step，如上分析，保证只对当前列排序
                    if(a[j] > value) {
                        a[j + step] = a[j];
                    }else{
                        break;
                    }
                }
                a[j + step] = value;
            }

            step /= 2;
        }
    }
}
