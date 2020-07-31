package backtrack;


/**
 * Eight Queen puzzle (八皇后问题)：
 *   The eight queens puzzle is the problem of placing eight chess queens on an 8×8 chessboard so that no two queens
 * threaten each other; thus, a solution requires that no two queens share the same row, column, or diagonal.
 */
public class Queen {
	private static final int DEFAULT_R = 8;

	private int R = DEFAULT_R;  // R 皇后问题
	private int[] result = new int[R]; // 结果数组
	private int count = 0;

	public Queen(){}

	public Queen(int r){
		this.R = r;
	}

	public void rQueens(int row){
		if(row == R){
			print((result));
			count ++;
			return;
		}

		for(int i = 0; i < R; i ++){
			if(canPlace(row, i)){
				result[row] = i;
				rQueens(row + 1);
			}
		}
	}

	private boolean canPlace(int row, int column){
		int leftup = column - 1;
		int rightup = column + 1;

		for(int i = row - 1; i >= 0 ; i --){
			if(result[i] == column){  // 判断列
				return false;
			}else{ // 判断对角线
				if(leftup >= 0 && result[i] == leftup)
					return false;
				if(rightup < R && result[i] == rightup)
					return false;
			}
			leftup --;
			rightup ++;
		}
		return true;
	}

	private void print(int[] array){
		for(int i = 0; i < R; i ++){
			for(int j = 0; j < R; j ++){
				if(result[i] == j)
					System.out.print("Q ");
				else
					System.out.print("* ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public int size(){
		return this.count;
	}


	public static void main(String[] args){
		Queen q = new Queen();
		q.rQueens(0);
		System.out.println("八皇后问题解的个数: " + q.size());
	}
}
