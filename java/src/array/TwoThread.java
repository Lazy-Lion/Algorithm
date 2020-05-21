package array;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 两个线程交替打印 1 ~100
 *
 *
 */
public class TwoThread {
	// TODO: 不放入此项目中
	// 相关： 多线程、java8 stream、String format、BigInteger
	private static int num = 1;  // 下一个要打印的数

	private static volatile boolean even = false; // 下一个打印的是否是偶数

	private static Lock lock = new ReentrantLock();

	private static ExecutorService threadpool = Executors.newFixedThreadPool(1);

	// 1~100
	public static void print() {
		Thread evenThread = new EvenThread();
		Thread oddThread = new OddThread();

		evenThread.start();
		oddThread.start();
	}

	public static void main(String[] args) {
//		print();
//		Long l = 100L;
//		System.out.println(Long.toString(l));
//		Thread t = new Thread(() -> {
//			try {
//				try {
//					System.out.println(1);
//					Integer.parseInt("12,,");
//				} catch (Exception e) {
//					throw e;
//				} finally {
//
//				}
//			} catch (Exception e) {
//				throw e;
//			} finally {
//
//			}
//		});
//		t.start();
//		int i = 123;
//		int q = (i * 52429) >>> (16+3);
//		int r = i - ((q << 3) + (q << 1));
//		System.out.println(r);
//
//		System.out.println(Double.toString(1234567));
//
//		double num = 12345678;
//		// 如下4种方法，前三种都转化成了科学计数法
//		System.out.println(Double.toString(num));
//		System.out.println(String.valueOf(num));
//		System.out.println(num + "");
//		System.out.println(String.format("%.1f", num));
//
//		System.out.println(Long.toString(1234567890123456789L));
//
//		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
//		List<Integer> result = list.stream().map(val -> val - 1).collect(Collectors.toList());
//		System.out.println(list.toString());
//		System.out.println(result.toString());
//
//		List<Integer> list1, list2, list3;
//		list1 = Arrays.asList(1, 2, 3);
//		list2 = Arrays.asList(4, 5, 6);
//		list3 = Arrays.asList(7, 8, 9);
//		List<List<Integer>> listList = Arrays.asList(list1, list2, list3);
//		List<Integer> result2 = listList.stream().flatMap(val -> val.stream()).collect(Collectors.toList());
//		System.out.println(result2.toString());
//
//		List<List<Integer>> listList1 = Arrays.asList(list3, list2, list1);
//		listList1.stream().flatMap(v -> v.stream()).sorted().forEach(v -> System.out.print(v + " "));
//		System.out.println();
//
//		// map(): one to one, str.stream().map() return Stream<String[]>
//		// flatMap(): one to many, flat the stream, .flatMap() return Stream<String>
//		List<String> result3 = Stream.of("test", "map", "and", "flatMap")
//				.map(v1 -> v1.split(""))
//				.flatMap(v2 -> Arrays.stream(v2))
//				.collect(Collectors.toList());
//		System.out.println(result3);
//
//		System.out.println(listList.stream()
//				.flatMap(v -> v.stream())
//				.mapToInt(v -> v)
//				.sum());
//
//		System.out.println(listList.stream()
//				.flatMap(v -> v.stream())
//				.mapToInt(v -> v)
//				.average()
//				.orElse(0.0));
//
//		// reduce(biOperator)
//		System.out.println(listList.stream()
//				.flatMap(v -> v.stream())
//				.reduce((v1, v2) -> v1 + v2)
//				.orElse(0));
//
//		// reduce(identity, biOperator)
//		System.out.println(listList.stream()
//				.flatMap(v -> v.stream())
//				.reduce(5, (v1, v2) -> v1 + v2)
//		);
//
//		// reduce(identity, biOperator, combinator)
//		System.out.println(listList.stream()
//				.flatMap(v -> v.stream()).parallel()
//				.reduce(1, (v1, v2) -> v1 + v2, Integer::sum)
//		);
//
//		IntStream.range(1,3).forEach(v -> System.out.print(v + " "));
//		System.out.println();
//
//		IntStream.rangeClosed(1,3).forEach(v -> System.out.print(v + " "));
//		System.out.println();
//
//		String text = null;
//		System.out.println(Optional.ofNullable(text).map(String::length).orElse(-1));
//
//		List<Integer> list5 = new ArrayList<>();
//		for(int k = 0; k < 10; k ++) {
//			list5.add(k);
//		}
//
//		// 比较如下三段代码差异
//		// 1.
//		List<Integer> result5 = list5.stream().map(v -> {
//			System.out.print(v + " ");
//			return v;
//		}).sorted().limit(5).collect(Collectors.toList());
//		System.out.println();
//		System.out.println(result5);
//		// 2.
//		List<Integer> result6 = list5.stream().map(v -> {
//			System.out.print(v + " ");
//			return v;
//		}).limit(5).collect(Collectors.toList());
//		System.out.println();
//		System.out.println(result6);
//		// 3.
//		List<Integer> result7 = list5.stream().map(v -> {
//			System.out.print(v + " ");
//			return v;
//		}).limit(5).sorted().collect(Collectors.toList());
//		System.out.println();
//		System.out.println(result7);
//
//		Stream.generate(() -> new Random().nextInt(10)).limit(10).forEach(System.out::print);
//
//		Consumer<Integer> b = (s) -> new Integer(s);
//		new Integer(10);
//		System.out.println("------");
//		System.out.println(i = 10);
//
//		System.out.println(func("6", Integer::parseInt));
////		Integer val = new Integer(100);
////		System.out.println(func("6", val::parseInt));
//
//		String[] stringArray = { "Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda" };
//		Arrays.sort(stringArray, String::compareToIgnoreCase);
//		System.out.println(func("str", String::length));
//
//		System.out.println(fun3(ArrayList::removeAll));
//
//		Supplier<String> s1 = String::new;
//		s1 = () -> new String();
//		Function<String, String> s2 = String::new;
//		s2 = s -> new String(s);
//
//		IntUnaryOperator o1 = x -> x + 2;
//		IntUnaryOperator o2 = x -> x * 2;
//		System.out.println(o1.andThen(o2).applyAsInt(1));
//		System.out.println(o1.compose(o2).applyAsInt(1));
//
//		int[] array = {1, 2, 3, 4, 5};
//		Arrays.stream(array)
//				.map(v -> v * v)
//				.forEach(v -> System.out.print(v + " "));
//		System.out.println();
//
//		Integer[] a1 = {1, 2, 3};
//		Integer[] a2 = {3, 4};
//		Arrays.stream(a1)  // a1如果声明为int[], Arrays.stream(a1) 会返回 IntStream
//				.flatMap(v -> Arrays.stream(a2).map(v2 -> new int[]{v, v2}))
//				.collect(Collectors.toList())
//				.forEach(v -> System.out.println(Arrays.toString(v)));
//		System.out.println("--------");
//		Arrays.stream(a1)
//				.flatMap(v -> Arrays.stream(a2)
//						.filter(v2 -> (v + v2) % 3 == 0)
//						.map(v2 -> new int[] {v, v2}))
//				.collect(Collectors.toList())
//				.forEach(v -> System.out.println(Arrays.toString(v)));



//		Trader raoul = new Trader("Rauol", "Cambridge");
//		Trader mario = new Trader("Mario", "Milan");
//		Trader alan = new Trader("Alan", "Cambridge");
//		Trader brain = new Trader("Brain", "Cambridge");
//
//		List<Transaction> transactions = Arrays.asList(
//				new Transaction(brain, 2011, 300),
//				new Transaction(raoul, 2012, 1000),
//				new Transaction(raoul, 2011, 400),
//				new Transaction(mario, 2012, 710),
//				new Transaction(mario, 2012, 700),
//				new Transaction(alan, 2012,950)
//		);

//		// 1
//		List<Transaction> r1 = transactions.stream()
//				.filter(t -> t.getYear() == 2011)
//				.sorted(Comparator.comparingInt(Transaction::getValue))
//				.collect(Collectors.toList());
//		// 2
//		List<String> r2 = transactions.stream()
//				.map(v -> v.getTrader().getCity())
//				.distinct()
//				.collect(Collectors.toList());
//
//		// 3
//		List<Trader> r3 = transactions.stream()
//				.map(v -> v.getTrader())
//				.filter(v -> v.getCity().equals("Cambridge"))
//				.distinct()
//				.sorted(Comparator.comparing(Trader::getName))
//				.collect(Collectors.toList());
//
//		// 4
//		String r4 = transactions.stream()
//				.map(v -> v.getTrader().getName())
//				.distinct()
//				.sorted()
//				.collect(Collectors.joining());
//
//		// 5
//		boolean r5 = transactions.stream()
//				.anyMatch(v -> v.getTrader().getCity().equals("Milan"));
//
//		// 6
//		List<Transaction> r6 = transactions.stream()
//				.filter(v -> v.getTrader().getCity().equals("Cambridge"))
//				.collect(Collectors.toList());
//
//		// 7
//		OptionalInt r7 = transactions.stream().mapToInt(v -> v.getValue()).max();
//
//
//		// 8
//		Optional<Transaction> r8 = transactions.stream()
//				.reduce((v1, v2) -> v1.getValue() > v2.getValue() ? v2 : v1);
//
//		r8 = transactions.stream()
//				.min(Comparator.comparing(Transaction::getValue));
//
//		System.out.println("--------------");
//		System.out.println(5.1 % 1); // 判断一个数是不是整数，n % 1 == 0 => n 是整数
//		System.out.println(5 % 1);

//		// 生成勾股数
//		IntStream.rangeClosed(1, 100)
//				.boxed()
//				.flatMap(a -> IntStream.rangeClosed(a, 100)
//						.mapToObj(b -> new double[] {a, b, Math.sqrt(a * a + b * b)})
//						.filter(v -> v[2] % 1 == 0))
//				.limit(5)
//				.forEach(v -> System.out.println(Arrays.toString(v)));
//
//		// 生成斐波拉契数
//		Stream.iterate(new int[] {0, 1}, v -> new int[] {v[0] + v[1], v[0] + v[1] + v[1]})
//				.flatMap(v -> Arrays.stream(v).boxed()).limit(10).forEach(v -> System.out.print(v + " "));
//
//		System.out.println();
//		Stream.iterate(new int[] {0, 1}, v -> new int[] {v[ 1], v[0] + v[1]})
//				.mapToInt(v -> v[0]).limit(10).forEach(v -> System.out.print(v + " "));
//
//		System.out.println();
//
//		// 无论对应key中是否有数据，partitioningBy返回的map都会有false、true两个key
//		// 而 groupingBy只会返回不为空的key
//		Map<Boolean, List<int[]>> map = Arrays.stream(new int[][] {}).collect(Collectors.partitioningBy(v -> v.length > 0));
//		System.out.println(map);
//
//
//		// 将数字分为质数和非质数
//		Map<Boolean, List<Integer>> map2 = IntStream.rangeClosed(3, 10).boxed().collect(Collectors.partitioningBy(
//			v -> IntStream.range(2, (int) Math.sqrt(v)).boxed().noneMatch(i -> v % i == 0)
//		));
//		System.out.println(map2);
//
//		System.out.println(-1 % 8);
//		System.out.println(-9 % 8);
//
//		int minValue = Integer.MIN_VALUE; // the most negative number in two's complement
//		System.out.println(minValue);
//		System.out.println(-minValue);
//		System.out.println(Math.abs(minValue));
//		System.out.println(-1 * minValue);
//		System.out.println(minValue / (-1));
//		System.out.println(minValue % (-1));

//      // Calendar and SimpleDateFormat
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR, 2019);
//		System.out.println(dateFormat.format(cal.getTime()));


//		Calendar cal = Calendar.getInstance();
//
//		cal.set(Calendar.YEAR, 2018);
//		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
//		cal.set(Calendar.DATE, 31);
//		cal.set(Calendar.HOUR_OF_DAY, 23);
//		cal.set(Calendar.MINUTE, 59);
//		cal.set(Calendar.SECOND, 59);
//		System.out.println(cal.getActualMaximum(Calendar.DATE));
//		System.out.println(dateFormat.format(cal.getTime()));
//
//		SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
//
//		System.out.println(format.format(getStartTimeOfDay("2019-12-01")));
//		System.out.println(format.format(getEndTimeOfDay("2019-12-01")));

//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR, 2018);
//		System.out.println(cal.getTime());
//
//		Class<?> clz = Array.newInstance(String.class, 0).getClass();
//		System.out.println(clz);
//
//		try {
//			Class<?> clz1 = Class.forName("[Ljava.lang.String;");
//			System.out.println(clz1 == clz);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		Class<?> clz2 = null;
//		try {
//			clz = Class.forName("java.lang.StringBuilder");
//			clz2 = Class.forName("java.lang.StringBuilder");
//		} catch (ClassNotFoundException e) {
//			System.out.println(e);
//		}
//		System.out.println(clz);
//		System.out.println(clz == clz2);
//
//		try {
//			// Class.forName can not be used for primitive types
//			clz = Class.forName("array.TwoThread$ClassLoadTest");  //编译之后 TwoThread.ClassLoadTest类名为 TwoThread$ClassLoadTest
//		} catch (ClassNotFoundException e) {
//			System.out.println(e);
//		}
//
//		String[] array = new String[1];
//		System.out.println(array.getClass().getName());
//
//		double[] array2 = new double[1];
//		System.out.println(array2.getClass().getName());
//
//		Object[] array3 = new Object[1];
//		System.out.println(array3.getClass().getName());
//
//		System.out.println(Object.class.getName());
//
//		try {
//			clz = Class.forName("[Ljava.lang.String;"); // ;不能省略
//		} catch (ClassNotFoundException e) {
//			System.out.println(e);
//		}
//
//		int x = -1;
//		int y = -2;
//		System.out.println(Integer.MIN_VALUE);
//		System.out.println(x + Integer.MIN_VALUE); // overflow
//		System.out.println(y + Integer.MIN_VALUE); // overflow
//		System.out.println(Integer.compareUnsigned(x, y));
//
//		System.out.println(0b11);
//		System.out.println(0x11);
//
//		System.out.println(0xA0A0A0A0);
//		System.out.println(0xA0A0A0A0L);
//
//		int val = (int) (8 & 0xffL);  // int & long -> long
//
//		System.out.println("---------------");
//		int v = -8;
//		System.out.println(v >> 1);
//		System.out.println(v);  // 移位运算不修改原值
//
//		System.out.println((long)-8);  // int -> long
//		System.out.println(-8 & 0xffffffffL); // int的32位转换成long的低32位
//
//		long l = -8L;
//		System.out.println(l >> 63);
//		System.out.println(-l >>> 63);
//		System.out.println( (l >> 63) | (-l >>> 63) ); // Long.signum(long i) 方法的实现
//
//		// BigInteger
//		System.out.println(new BigInteger(new byte[] {0, -96, -96, -96, -96, -96}).longValue()); // byte array to BigInteger
//
//		BigInteger bi = BigInteger.valueOf(160);
//		System.out.println(bi.intValue());
//		System.out.println(Arrays.toString(bi.toByteArray())); // BigInteger to byte array
//		System.out.println(new BigInteger(new byte[] {0, -96, -96}).intValue()); // byte array to BigInteger
//
//		String url = "http://www.baidu.com";
//		String[] ss = url.split("/");
//		System.out.println(Arrays.toString(ss));
//		System.out.println(ss[2]);

//		// switch
////		String str = null; // switch 中的string 不能为null, java.lang.NullPointerException
//		String str = "";
//		System.out.println("start switch ...");
//		switch (str) {
//			case "":
//				System.out.println("switch empty string"); break;
////			case null: // constant expression required
////				System.out.println("switch null string"); break;
//			case "null":
//				System.out.println("switch \"null\" string"); break;
//			default:
//				System.out.println("switch default");
//
//		}
//		System.out.println("end switch ...");
//
//		System.out.println(new Date().getTime()); // 获取毫秒，使用 System.currentTimeMillis() 效率较高
//		System.out.println(System.currentTimeMillis());
//		System.out.println(System.nanoTime());
//
//		String pattern1 = "yyyy-MM-dd HH:mm:ss";
//		String pattern2 = "YYYY-MM-dd HH:mm:ss";  // YYYY 表示日期所在周所属的年份（一周从周日开始到周六结束）
//		SimpleDateFormat format1 = new SimpleDateFormat(pattern1);
//		SimpleDateFormat format2 = new SimpleDateFormat(pattern2);
//		String text = "2019-12-31 00:00:00";
//
//		Date date1 = null, date2 = null;
//		try {
//			date1 = format1.parse(text);
//			date2 = format2.parse(text);  // 解析出的日期是 2018 年
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		System.out.println(date1);
//		System.out.println(date2);
//
//		System.out.println(format1.format(date1));
//		System.out.println(format2.format(date1)); // 输出的日期是实际date的下一年
//		System.out.println(format1.format(date2));
//		System.out.println(format2.format(date2));
//
//
//		// 协变(covariant)和逆变(contravariant)
//		// 协变： if A is a subtype of B, then M<A> is a subtype of M<B>, vice versa
//		//      java: if A is a subtype of B, then List<A> is a subtype of List<? extend B>
//		// 逆变： if A is a subtype of B, then M<B> is a subtype of M<A>, vice versa
//		//      java: if A is a subtype of B, then List<? super B> is a subtype of List<A>
//		// java 通过 extends, super 两个关键词实现协变和逆变
//
//		Object[] arr;
//		arr = new Integer[5];  // java数组支持协变，但是实际运行时会有问题
////		arr[0] = new Object(); // 运行时抛出异常 java.lang.ArrayStoreException
//
//		List<Object> list3;
//		List<String> list4 = new ArrayList<>();
////		list3 = list4; // java 容器不支持直接的协变，通过关键字 extends 实现
//
//		List<Integer> list1 = new ArrayList<>();
//		List<Object> list2 = new ArrayList<>();
//		func(list1, list2, list1);
//		func(list1, list2, list2);
//
//		System.out.println("--------------------");
//		int m = 0;
//		System.out.println(m = m + 3); // java中赋值语句是有返回值的,返回值为赋值语句右侧结果
//		System.out.println(m);
//		System.out.println("--------------------");
//
//		System.out.println(func());
//		System.out.println(func2());

//		String s = "a,b,c, ,,";
//		String[] r = s.split(","); // split方法不保留空值
//		System.out.println(Arrays.toString(r));
//
//		// TimSort: 利用现实数据有部分有序数据，算法时间复杂度实际上小于 O(nlogn)
//
//
//		mergeFile("C:/Users/huangjian/Desktop/test/1.txt", "C:/Users/huangjian/Desktop/test/2.txt", "C:/Users/huangjian/Desktop/test/3.txt" );



//		threadpool.execute(() -> {
//			throw new RuntimeException("execute thread task");
//		});
//
//		threadpool.submit(() -> {
//			throw new RuntimeException("submit thread task");
//		});


//		String dateStr = "2020-05-09 14:07:30";
//		ZoneId zoneSH = ZoneId.of("Asia/Shanghai");
//		// 纽约与上海分别为为西五区、东八区，相差13个时区，05-09 纽约时区实行夏令时，实际时间差12个小时
//		ZoneId zoneNY = ZoneId.of("America/New_York");
//		ZoneId zoneTY = ZoneId.of("Asia/Tokyo");
//
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse(dateStr, formatter), zoneSH);
//		System.out.println(zoneSH.getId() + " " + formatter.withZone(zoneSH).format(zonedDateTime));
//		System.out.println(zoneNY.getId() + " " + formatter.withZone(zoneNY).format(zonedDateTime));
//		System.out.println(zoneTY.getId() + " " + formatter.withZone(zoneTY).format(zonedDateTime));

		// 以下运行结果未经标注默认使用jdk 8
		String s1 = new String("1");
		String s3 = s1.intern();
		String s2 = "1";
		System.out.println("s1 == s2: " + (s1 == s2));  // false
		System.out.println("s1.intern() == s2: " + (s3 == s2)); // true

		String s4 = new String("1") + new String("1");
		s4.intern();
		String s5 = "11";
		System.out.println("s4 == s5: " + (s4 == s5)); // true, (jdk 7 之前(不包括jdk 7)返回false)

		String s6 = new String("a") + new String("b");
		String s7 = "ab";
		s6.intern();
		System.out.println("s6 == s7: " + (s6 == s7)); // false

		s1 = new String("123").intern();
		s2 = "123";
		System.out.println("s1 == s2: " + (s1 == s2)); // true

		s1 = "45" + "6"; // jvm 优化
		s2 = "456";
		System.out.println("s1 == s2: " + (s1 == s2)); // true

		String temp = "78";
		s1 = temp + "9";
		s2 = "789";
		System.out.println("s1 == s2: " + (s1 == s2)); // false
	}

	static void fun3() {
		try {
			throw new RuntimeException();
		} catch (Exception ex) {
			throw ex;
		} finally {

		}
	}

	static int func() {
		String text = "2019-12-20 12:00:00";
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			Date date = format.parse(text);
			System.out.println("try ...");
			return 0;
		} catch (Exception e) {
			System.out.println("exception ...");
			return 1;
		} finally {
			System.out.println("finally ...");
			return 2;  // 无论是否有异常，返回值都是 2， try catch 中的返回点被丢弃
		}
	}

	static int func2() {
		String text = "2019-12-20 12:00:00";
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		int x = 0;
		try {
			Date date = format.parse(text);
			System.out.println("try ...");
			return x = x + 1;
		} catch (Exception e) {
			System.out.println("exception ...");
			return x = x + 2;
		} finally {
			System.out.println("finally ...");
			return x = x + 3;  // 异常时返回5，不异常时返回4
		}
	}

	static void func(List<? extends Object> list1, List<? super Number> list2, List<?> list3) {
	}

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static Date getStartTimeOfDay(String date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		Date result;
		try {
			result = format.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException();
		}
		return result;
	}

	private static Date getEndTimeOfDay(String date) {
		Date startTime = getStartTimeOfDay(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}


	static int func(String val, ToIntFunction<String> f) {
		return f.applyAsInt(val);
	}

	static int func2(IntSupplier f) {
		return f.getAsInt();
	}

	static boolean fun3(BiFunction<ArrayList, Collection<?>, Boolean> f) {
		return f.apply(new ArrayList<>(Arrays.asList(1,2,3)), new ArrayList<>(Arrays.asList(1)));
	}

//	public void run() {
//		func();
//	}
//
//	private static void func() {
//		try {
//			try {
//				System.out.println(1);
//				Integer.parseInt("12,,");
//			} catch (Exception e) {
//				throw e;
//			} finally {
//
//			}
//		} catch (Exception e) {
//
//		} finally {
//
//		}
//	}

	T t = () -> {
		try {
			System.out.println(10 / 0);
		} catch (Exception e) {
			throw new IOException(e);
		}
	};

	private static void mergeFile(String path1, String path2, String destPath) {
		File src1 = new File(path1);
		File src2 = new File(path2);

		File dest = new File(destPath);
		FileOutputStream foss = null;
		FileChannel channel33 = null;


		try(FileInputStream fis1 = new FileInputStream(src1);
			FileInputStream fis2 = new FileInputStream(src2);
			FileOutputStream fos = new FileOutputStream(dest);
			FileChannel channel1 = fis1.getChannel();
			FileChannel channel2 = fis2.getChannel();
			FileChannel channel3 = fos.getChannel()) {

			foss = fos;
			channel33 = channel3;


			channel3.transferFrom(channel1, 0, channel1.size());
			channel3.transferFrom(channel2, channel3.size(), channel2.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println(channel33.isOpen());
				foss.write(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}



	static class EvenThread extends Thread {
		@Override
		public void run() {
			while(num <= 100) {
				try {
					lock.lock();
					if(even && num <= 100) {
						System.out.println("even " + num);
						num ++;
						even = false;
					}
				} finally {
					lock.unlock();
				}
			}
		}
	}

	static class OddThread extends Thread {
		@Override
		public void run() {
			while(num <= 100) {
				try {
					lock.lock();
					if(!even && num <= 100) {
						System.out.println("odd  " + num);
						num ++;
						even = true;
					}
				} finally {
					lock.unlock();
				}
			}
		}

		public int f(String s) {
			return s.length();
		}
	}

	static class Trader {
		private final String name;
		private final String city;

		Trader(String name, String city) {
			this.name = name;
			this.city = city;
		}

		public String getName() {
			return this.name;
		}

		public String getCity() {
			return this.city;
		}

		public String toString() {
			return "Trader: " + this.name + " in " + this.city;
		}
	}

	static class Transaction {
		private final Trader trader;
		private final int year;
		private final int value;

		Transaction(Trader trader, int year, int value) {
			this.trader = trader;
			this.year = year;
			this.value = value;
		}

		public Trader getTrader() {
			return trader;
		}

		public int getYear() {
			return year;
		}

		public int getValue() {
			return value;
		}

		public String toString() {
			return "{ " + this.trader + " , year: " + this.year + ", value: " + this.value + " }";
		}
	}

	static class ClassLoadTest {
		static {
			System.out.println("load class");
		}
	}

	class T1 {
		void func() {
			System.out.println(TwoThread.this); // qualified this, java se specification 15.8.4
		}

		/**
		static void func2() {  // inner classes can not have static declarations

		}
		 */
	}

	static class T2 {
		void func() {
//			System.out.println(TwoThread.this); // can not be referenced from a static context
		}
	}
}
	interface T {
		void run() throws IOException;
	}