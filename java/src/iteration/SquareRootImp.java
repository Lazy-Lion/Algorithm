package iteration;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class SquareRootImp {

    /**
     * 计算大于1的正整数的平方根
     * 二分实现
     * @param n 待求的数 (n > 1)
     * @param deltaThreshold 误差阈
     * @param maxTry，迭代最大次数，防止死循环
     * @return Square Root
     */
    public static double getSquareRoot(int n, double deltaThreshold, int maxTry){
        if(n <= 1){
            return -1.0;
        }

        double min = 1.0;
        double max = n * 1.0;

        for(int i = 0; i < maxTry; i ++){
            double middle = (min + max) / 2;
            double square = middle * middle;

            if(Math.abs(square/n - 1) <= deltaThreshold){
                return middle;
            }else if(square > n){
                max = middle;
            }else{
                min = middle;
            }
        }

        return -2.0;
    }


    /**
     * 指定返回浮点数，小数点位数
     * way 1: DecimalFormat
     * 不管传入值是多少，均保留两位小数，并符合四舍五入
     * @param decimal
     * @return
     */
    public static String getFormatDoubleByDecimalFormat1(double decimal){
        DecimalFormat df = new DecimalFormat("0.00");

        return df.format(decimal);
    }

    /**
     * 指定返回浮点数，小数点位数
     * way 2: DecimalFormat
     * 保留小数点后不为0的两位小数，保证小数点后最后一位不为0，符合四舍五入
     * @param decimal
     * @return
     */
    public static String getFormatDoubleByDecimalFormat2(double decimal){
        DecimalFormat df = new DecimalFormat("#.##");

        return df.format(decimal);
    }

    /**
     * 指定返回浮点数，小数点位数
     * way 3: String.Format
     * 不管传入值是多少，均保留两位小数，并符合四舍五入
     * @param decimal
     * @return
     */
    public static String getFormatDoubleByStringFormat(double decimal){
        String result = String.format("%.2f", decimal);

        return result;
    }

    /**
     * 指定返回浮点数，小数点位数
     * way 4: BigDecimal
     * 若小数点后均为0，保留一位小数，其他情况保留两位小数，舍入方式可自行选择
     * @param decimal
     * @return
     */
    public static String getFormatDoubleByBigDecimal(double decimal){
        BigDecimal bd = new BigDecimal(decimal);

        double d = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        return Double.toString(d);
    }
}
