package divideandconquer;

import java.util.Arrays;

/**
 * 逆序对
 *
 * Inversion：In computer science and discrete mathematics a sequence has an inversion where two of its elements are
 *           out of their natural order.
 */
public class Inversion {
    private int num;

    /**
     * 求解逆序对个数:
     *    类似归并排序方式: Merge 完成之后的数组有序
     * @param array
     * @param l inclusive
     * @param r exclusive
     */
    public void inversion(int[] array, int l, int r) {
        if (l >= r - 1) return;

        array = Arrays.copyOf(array, array.length);

        int m = l + (r - l) / 2;
        inversion(array, l, m);
        inversion(array, m, r);
        merge(array, l, m, r);
    }

    private void merge(int[] array, int l, int m, int r) {
        int i = l, j = m, k = 0;
        int[] temp = new int[r - l];

        while (i < m && j < r) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                this.num += (m - i);
                temp[k++] = array[j++];
            }
        }

        while (i < m)
            temp[k++] = array[i++];

        while (j < r)
            temp[k++] = array[j++];

        for (i = 0; i < temp.length; i++) {
            array[l + i] = temp[i];
        }

    }


    public static void main(String[] args) {
        int[] array = new int[]{4, 3, 1, 2};
        Inversion i = new Inversion();
        i.inversion(array, 0, array.length);
        System.out.println(i.num);
    }
}
