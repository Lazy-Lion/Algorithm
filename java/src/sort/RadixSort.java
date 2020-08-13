package sort;

/**
 * 基数排序：非基于比较的排序算法
 *   核心思想：将待排数组中的每个元素按照位数切割(待排数组中的元素需要先统一长度)，然后按照每个位置分别排序
 *     位次从右向左依次排序，每轮排序的算法需要是稳定的，使得前一位排序完成之后依然可以保证相等时后一位有序。(当然，
 *     采用这种方式的前提是 if p = "1234", q = "1321", since 2 < 3  => p < q)
 *
 * 基数排序需要借助稳定的桶排序或计数排序才能达到 O(n) 线性效率
 *
 * 时间复杂度：O(k*n)   -- k 元素位数，n 元素个数
 *
 * 空间复杂度：O(m + n) -- 桶排序或计数排序的空间复杂度
 */
public class RadixSort {
    /**
     * 电话号码排序
     */
    public static void radixSort(String[] array, int n) {

        if (n <= 1) return;

        // assume: each element has the same length
        for (int i = array[0].length() - 1; i >= 0; i--) {
            countSort(array, n, i);
        }
    }


    // assume :  '0' =< a[i].charAt(bit) <= '9'
    private static void countSort(String[] array, int n, int bit) {

        if (n <= 1) return;

        int[] count = new int[10];   // count array

        // count per bucket
        for (int i = 0; i < n; i++) {
            count[array[i].charAt(bit) - '0']++;
        }

        // accumulation
        for (int i = 1; i < count.length; i++) {
            count[i] = count[i - 1] + count[i];
        }

        // get sorted result
        String[] result = new String[n];

        for (int i = n - 1; i >= 0; i--) {   // for the stability of sorting, traverse from back to front
            int position = array[i].charAt(bit) - '0';
            result[--count[position]] = array[i];
        }

        // copy result to original array
        for (int i = 0; i < n; i++) {
            array[i] = result[i];
        }

    }

    public static void main(String[] args) {
        String[] array = new String[20];

        for (int i = 0; i < 20; i++) {
            array[i] = getPhoneNum();
        }

        System.out.println("原数组");
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }

        RadixSort.radixSort(array, array.length);

        System.out.println("排序后");
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    private static String getPhoneNum() {
        StringBuilder builder = new StringBuilder();

        builder.append(1);
        for (int i = 1; i < 11; i++) {
            builder.append((int) (Math.random() * 10));
        }

        return builder.toString();
    }
}
