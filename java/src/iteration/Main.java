package iteration;

/**
 * test
 */
public class Main {

    public static void main(String[] args) {

        // test Format Decimal
        int number = 10;

        double squareRoot = SquareRootImp.getSquareRoot(number, 0.0000001, 10000);

        System.out.println(squareRoot);


        System.out.println(SquareRootImp.getFormatDoubleByBigDecimal(1));
        System.out.println(SquareRootImp.getFormatDoubleByBigDecimal(1.0));
        System.out.println(SquareRootImp.getFormatDoubleByBigDecimal(1.00));
        System.out.println(SquareRootImp.getFormatDoubleByBigDecimal(1.06));
        System.out.println(SquareRootImp.getFormatDoubleByBigDecimal(1.066));

        // test Binary Search
        String[] array = {"am","authors","geekbang","i","in","of","one","the"};
        System.out.println(BinarySearchImp.binarySearch(array,"i"));

        String[] array2 = {"0","1","2","3","4","5","6","7"};
        System.out.println(BinarySearchImp.binarySearch(array2,"0"));
    }
}
