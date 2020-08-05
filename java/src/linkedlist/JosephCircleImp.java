package linkedlist;

/**
 * 约瑟夫环
 * question: n个人（编号0~(n-1))，从0开始报数，报到(m-1)的退出，剩下的人继续从0开始报数。求胜利者的编号。
 */
public class JosephCircleImp {


    /**
     * simulation process
     *
     * time complexity: O(m*n)
     * space complexity: O(1)
     */
    public static int simulate(int n, int m) {
        assert n > 0 && m > 0;
        Node starter = createJosephCircle(n);

        for (int i = 0; i < n - 1; i++) { // there is only one winner
            Node node = starter;
            for (int j = 1; j < m - 1; j++) {
                node = node.next;
            }
            node.next = node.next.next;
            starter = node.next;
        }

        return starter.getData();
    }


    /**
     * 数学方法
     * 0 1 2 3 ... m-1 m ... n-1    (n阶序列，m-1出列)
     *
     * m m+1 m+2 ...  n-1   0       1    2        ...   m-2                 (新的 n-1 序列)
     * 0  1   2  ... n-m-1 n-m   n-m+1  n-m+2     ... n-m+(m-2)=(n-2)         (重新编号)
     * f(x,m) 表示问题规模为 x 时的下标编号：f(x,m) = (f(x-1) + m) mod x
     * 且  f(1,m) 为最终的下标序列，只有 1 个结果且为 0
     *
     * time complexity: O(n)
     * space complexity: O(1)
     */
    public static int josephCircle(int n, int m) {
        assert n > 0 && m > 0;

        int result = 0;
        for (int i = 2; i <= n; i++) {
            result = (result + m) % i;
        }

        return result;
    }


    /**
     * 递归实现: f(x,m) = (f(x-1) + m) mod x
     * 时间复杂度： O(n)，可以使用递归树方法判断
     */
    public static int josephCircleByRecursion(int n, int m) {
        if (m < 1) return -1;
        if (n == 1) return 0;

        return (josephCircleByRecursion(n - 1, m) + m) % n;
    }

    /**
     * 创建约瑟夫环，编号从0开始
     */
    private static Node createJosephCircle(int n) {
        assert n > 0;

        Node head = new Node(0, null);
        Node tail = head;

        for (int i = 1; i < n; i++) {
            Node node = new Node(i, null);
            tail.next = node;
            tail = node;
        }

        tail.next = head;

        return head;
    }

}
