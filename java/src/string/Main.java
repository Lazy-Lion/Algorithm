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
    }
}
