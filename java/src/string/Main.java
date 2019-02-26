package string;

/**
 * test
 */
public class Main {
    public static void main(String[] args){
        BF b = new BF();
        System.out.println(b.match("abcdemfjgk","mfj"));

        RK r = new RK();
        System.out.println(r.match("abcdemfjgk","mfj"));

        BM m = new BM();
        System.out.println(m.match("abcdemfjgk","mfj"));
        System.out.println(m.match("aaaaaaaaaaaaaaaa","baaa"));

        System.out.println(get());  // 0
        System.out.println(get(""));  // 1



    }

    public static int get(String... array){    // java把可变参数当做数组处理, 只能有一个可变参数并位于参数列表最后
        return array.length;
    }
}
