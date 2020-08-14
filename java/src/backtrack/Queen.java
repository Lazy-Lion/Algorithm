package backtrack;


/**
 * Eight Queen puzzle (八皇后问题)：
 *   The eight queens puzzle is the problem of placing eight chess queens on an 8×8 chessboard so that no two queens
 * threaten each other; thus, a solution requires that no two queens share the same row, column, or diagonal.
 *
 * @see leetcode.NQueens
 */
public class Queen {
    private int n;  // n 皇后问题
    private int[] result; // 结果数组
    private int count = 0;

    public Queen(int n) {
        this.n = n;
        result = new int[n];
    }

    public void nQueens(int row) {
        if (row == n) {
            print();
            count++;
            return;
        }

        for (int i = 0; i < n; i++) {
            if (canPlace(row, i)) {
                result[row] = i;
                nQueens(row + 1);
                // no manual reset required, because it will automatically reset by next set
            }
        }
    }

    private boolean canPlace(int row, int column) {
        int upperLeft = column - 1;
        int upperRight = column + 1;

        for (int i = row - 1; i >= 0; i--) {
            if (result[i] == column) {  // column
                return false;
            } else { // diagonal
                if (upperLeft >= 0 && result[i] == upperLeft)
                    return false;
                if (upperRight < n && result[i] == upperRight)
                    return false;
            }
            upperLeft--;
            upperRight++;
        }
        return true;
    }

    private void print() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (result[i] == j)
                    System.out.print("Q ");
                else
                    System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int count() {
        return this.count;
    }


    public static void main(String[] args) {
        Queen q = new Queen(8);
        q.nQueens(0);
        System.out.println("N皇后问题解的个数: " + q.count());
    }
}
