package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * {@link sort.Utils}存在重复实现，后续用该类替代
 * 工具类
 */
public class Utils {
    /**
     * 数组指定index内容交换
     * @param a
     * @param index1
     * @param index2
     */
    public static void swap(int[] a, int index1, int index2) {
        if (index1 < 0 || index1 >= a.length || index2 < 0 || index2 > a.length) return;

        if (index1 == index2) return;

        int temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }

    public static void testStaticMethod(Class<?> clazz, List<List<Object>> argsList) throws InvocationTargetException, IllegalAccessException {
        assert !argsList.isEmpty();

        Method[] methods = clazz.getDeclaredMethods();

        assert methods.length > 0;

        Arrays.sort(methods, Comparator.comparing(Method::getName)); // order by name
        for (Method method : methods) {
            if (method.getName().equals("main")) {
                continue;
            }

            System.out.println("------" + method.getName() + " ------");

            for (List<Object> args : argsList) {
                System.out.println(method.invoke(null, args == null ? null : args.toArray()));
            }
        }
    }
}
