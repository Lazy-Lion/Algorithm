package array;


/**
 * test
 */
public class Main {
    public static void main(String[] args){

        /**
         * java 泛型与数组：
         *
         * Java Language Specification : java 8
         * It is a compile-time error if the component type of the array being initialized is not reifiable.
         *
         * A type is reifiable if and only if one of the following holds:
         *   1. It refers to a non-generic class or interface type declaration.
         *   2. It is a parameterized type in which all type arguments are unbounded wildcards.
         *   3. It is a raw type.
         *   4. It is a primitive type.
         *   5. It is an array type whose element type is reifiable.
         *   6. It is a nested type where, for each type T separated by a ".", T itself i reifiable.
         *     For example, if a generic class X<T> has a generic member class Y<U>,
         *     then the type X<?>.Y<?> is reifiable because X<?> is reifiable and Y<?> is reifiable.
         *     The type X<?>.Y<Object> is not reifiable because Y<Object> is not reifiable.
         *
         *
         *
         * Java 中底层使用数组的泛型容器实现，存储元素的数组用 Object[]
         */


    }
}
