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

        KMP k = new KMP();
        System.out.println(k.match("abcdemfjgk","mfj"));
        System.out.println(k.match("aaaaaaaaaaaaaaaa","baaa"));
        System.out.println(k.match("abadfeababacdadf","ababacd"));

        System.out.println(get());  // 0
        System.out.println(get(""));  // 1


        System.out.println("Trie Tree:");
        Trie t = new Trie();
        t.insert("trie");
        t.insert("tree");
        t.insert("pronounce");
        t.insert("pronouncation");
        t.insert("try");
        System.out.println(t.size());

        System.out.println(t.search("pronouncation"));
        System.out.println(t.search("pronounce"));
        t.delete("pronouncation");
        System.out.println(t.search("pronouncation"));
        System.out.println(t.search("pronounce"));
        System.out.println(t.size());

        System.out.print("trie树所有字符串：");
        for(String s : t.getAllWithPrefix("")){
            System.out.print(s + " ");
        }
        System.out.println();

        System.out.print("tr为前缀的字符串：");
        for(String s : t.getAllWithPrefix("tr")){
            System.out.print(s + " ");
        }
        System.out.println();

        System.out.print("p为前缀的字符串：");
        for(String s : t.getAllWithPrefix("p")){
            System.out.print(s + " ");
        }
        System.out.println();

    }

    public static int get(String... array){    // java把可变参数当做数组处理, 只能有一个可变参数并位于参数列表最后
        return array.length;
    }
}