package temp;

import greedy.Candy;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * todo: remove from current project
 */
public class TestReference {
//    static int x, y, a, b;
    static volatile int v = 1;
//    static AtomicInteger v1 = new AtomicInteger(1);

    public static void main(String[] args) {
        // java version "1.8.0_181"
        // default gc: ps, po
        // vm options: -Xmx20M -XX:+PrintGCDetails
        // young generation (eden, survivor1, survivor2), old generation
        // note: use G1 garbage collection (-XX:+UseG1GC) can create bigger object
//        SoftReference<byte[]> ref = new SoftReference<>(new byte[1024*1024*10]);
//
////        WeakReference<byte[]> ref = new WeakReference<>(new byte[1024*1024*10]);
//
//        System.out.println("ref: " + ref.get());
//        SoftReference<byte[]> ref2= new SoftReference<>(new byte[1024*1024*10]);
//        System.out.println("ref: " + ref.get());
//        System.out.println("ref2: " + ref2.get());
//
//        Thread cur = Thread.currentThread();
//        System.out.println(cur.getName() + ": " + cur.getContextClassLoader());
//
//        Thread t1  = new Thread( () -> { }, "t1");
//        System.out.println(t1.getName() + ": " + t1.getContextClassLoader());

//        Thread t = new Thread( ()-> {
//            System.out.println(Thread.currentThread().getName());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName() + " terminated");
//        }, "child thread");
//
//        t.start();
//
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(Thread.currentThread().getName());

//        int count = 0; // 执行次数
//        for(;;) {
//            count ++;
//            a = b = x = y = 0;
//            CountDownLatch latch = new CountDownLatch(2);
//            Thread t1 = new Thread(() -> {
//                a = 1;
//                x = b;
//                latch.countDown();
//            }, "thread1");
//
//            Thread t2 = new Thread(() -> {
//                b = 1;
//                y = a;
//                latch.countDown();
//            }, "thread2");
//
//            t1.start();
//            t2.start();
//
//            try {
//                latch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            if (x == 0 && y == 0) { // 重排序 or 可见性
//                System.out.println("第" + count + "次执行，x == 0 && y == 0");
//                break;
//            }
//        }

//        String str = "1";
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 4000000; i ++) {
//            str.intern();
//            str = String.valueOf(i);
//            if ( (i & ((1 << 19) - 1)) == 0) {
//                long end = System.currentTimeMillis();
//                System.out.println(end - start);
//                start = end;
//            }
//        }

//        // 反射动态修改字符串常量
//        String s1 = "abc";
//
//        Class<? extends String> clazz = s1.getClass();
//
//        Field field = null;
//        try {
//            field = clazz.getDeclaredField("value");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        field.setAccessible(true);
//
//        char[] chars = new char[0];
//        try {
//            chars = (char[])field.get(s1);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        chars[0] = 'd';
//
//        System.out.println(s1);
//
//        String s3 = "abc";
//        System.out.print(s3); // 输出 dbc

//        int value = 2;
//
//        byte b1 = (byte)value;
//        byte b0 = (byte)value;
//
////        char c1 = (char)((b1 << 8) | (b0 & 0xff));
//        char c2 = (char)(b0 & 0xff);
////        System.out.println((int)c1);
//        System.out.println((int)c2);
//
//        long v = (long)7.2;
//        System.out.println(v);


//        Random random = new Random();
//        int limit = 1_000_000_000;
//        int count = 100_000_000;
//        int len = (limit >>> 3) + 1;
//        byte[] bitArray = new byte[len];
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < count; i ++) {
//            int temp = random.nextInt(limit) + 1;
//            bitArray[temp >>> 3] |= (0b1000000 >> (temp & 7)); // (temp & 7) equals (temp % 8)
//        }
//        System.out.println(System.currentTimeMillis() - start);
        v ++;
//        v1.addAndGet(1);
    }
}
