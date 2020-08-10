package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * {@link sort.Utils}存在重复实现，后续用该类替代
 * 工具类
 */
public class Utils {
    /**
     * 数组指定index内容交换
     */
    public static void swap(int[] a, int index1, int index2) {
        if (index1 < 0 || index1 >= a.length || index2 < 0 || index2 >= a.length) return;

        if (index1 == index2) return;

        int temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }

    /**
     * list 指定index交换
     */
    public static <T> void swap(List<T> list, int idx1, int idx2) {
        assert list != null;
        if (idx1 < 0 || idx1 >= list.size() || idx2 < 0 || idx2 >= list.size() || idx1 == idx2) {
            return;
        }

        T temp = list.get(idx1);
        list.set(idx1, list.get(idx2));
        list.set(idx2, temp);
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
                print(method.invoke(null, args == null ? null : args.toArray()));
            }
        }
    }

    public static void testStaticMethod(Class<?> clazz, Set<String> methodNameSet, List<List<Object>> argsList) throws InvocationTargetException, IllegalAccessException {
        assert !argsList.isEmpty();

        Method[] methods = clazz.getDeclaredMethods();

        assert methods.length > 0;

        Arrays.sort(methods, Comparator.comparing(Method::getName)); // order by name
        for (Method method : methods) {
            if (methodNameSet.contains(method.getName())) {
                System.out.println("------" + method.getName() + " ------");

                for (List<Object> args : argsList) {
                    print(method.invoke(null, args == null ? null : args.toArray()));
                }
            }
        }
    }

    private static void print(Object obj) {
        if (obj.getClass().isArray()) {
            if (obj instanceof byte[]) {
                System.out.println(Arrays.toString((byte[]) obj));
            } else if (obj instanceof short[]) {
                System.out.println(Arrays.toString((short[]) obj));
            } else if (obj instanceof char[]) {
                System.out.println(Arrays.toString((char[]) obj));
            } else if (obj instanceof int[]) {
                System.out.println(Arrays.toString((int[]) obj));
            } else if (obj instanceof long[]) {
                System.out.println(Arrays.toString((long[]) obj));
            } else if (obj instanceof float[]) {
                System.out.println(Arrays.toString((float[]) obj));
            } else if (obj instanceof double[]) {
                System.out.println(Arrays.toString((double[]) obj));
            } else {
                System.out.println(Arrays.toString((Object[]) obj));
            }
        } else {
            System.out.println(obj);
        }
    }

}
