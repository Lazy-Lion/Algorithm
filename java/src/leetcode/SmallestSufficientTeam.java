package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 1125. Smallest Sufficient Team
 *   In a project, you have a list of required skills req_skills, and a list of people.  The i-th person people[i]
 * contains a list of skills that person has.
 *   Consider a sufficient team: a set of people such that for every required skill in req_skills, there is at least
 * one person in the team who has that skill.  We can represent these teams by the index of each person: for example,
 * team = [0, 1, 3] represents the people with skills people[0], people[1], and people[3].
 *   Return any sufficient team of the smallest possible size, represented by the index of each person.
 *   You may return the answer in any order.  It is guaranteed an answer exists.
 *
 * Example 1:
 *   Input: req_skills = ["java","nodejs","reactjs"], people = [["java"],["nodejs"],["nodejs","reactjs"]]
 *   Output: [0,2]
 *
 * Example 2:
 *   Input: req_skills = ["algorithms","math","java","reactjs","csharp","aws"],
 *          people = [["algorithms","math","java"],["algorithms","math","reactjs"],["java","csharp","aws"]
 *             ,["reactjs","csharp"],["csharp","math"],["aws","java"]]
 *   Output: [1,2]
 *
 * Constraints:
 *   1 <= req_skills.length <= 16
 *   1 <= people.length <= 60
 *   1 <= people[i].length, req_skills[i].length, people[i][j].length <= 16
 *   Elements of req_skills and people[i] are (respectively) distinct.
 *   req_skills[i][j], people[i][j][k] are lowercase English letters.
 *   Every skill in people[i] is a skill in req_skills.
 *   It is guaranteed a sufficient team exists.
 */
public class SmallestSufficientTeam {
    private static Map<String, Integer> map; // skill整数表示的映射表： key - skill, value - integer
    private static int c; // req_skills列表的整数表示
    private static List<Integer> p; // people技能列表整数表示

    /**
     * 思路：
     *   1. req_skills的长度 <= 16, 二进制思想，使用int型整数的低16位表示该列表：
     *     例： req_skills = ["java", "math"] => 0b01 -> ["java"], 0b10 -> ["math"], 0b11 -> ["java","math"]
     *   2. 同样对于 people, 每个人掌握的技能也可以使用一个int表示
     *   3. 问题转换： 0/1背包问题
     *      people - 物品集合
     *      req_skills - 背包容量
     *      状态方程： dp[i][x|y] = min{dp[i-1][x] + 1, dp[i-1][x|y]}
     *                i-第i个人， x|y - y表示第i个人的技能值, x表示处理第i个前前旧的技能值
     */
    public static int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
        preprocess(req_skills, people);

        int[] dp = new int[c + 1];  // dp[i]：组成技能为i的团队最少的人数
        List[] team = new List[c + 1]; // team[i]：组成技能为i的团队最少需要的人员列表（轨迹）

        dp[0] = 0;
        for (int i = 1; i < dp.length; i++)
            dp[i] = 61;  // people max is 60


        for (int i = 0; i < p.size(); i++) {
            for (int j = 0; j <= c; j++) {
                // 0/1背包实际上需要逆序遍历，以防止重复选择
                // 但是此问题中重复选择不会对结果产生影响 a|a == a， 顺序遍历更容易得到旧的技能值
                int x = j | p.get(i);
                if (dp[x] > dp[j] + 1) {
                    dp[x] = dp[j] + 1;
                    team[x] = team[j] == null ? new ArrayList() : new ArrayList(team[j]);
                    team[x].add(i);
                }
            }
        }

        int[] result = new int[team[c].size()];
        for (int i = 0; i < team[c].size(); i++) {
            result[i] = (Integer) team[c].get(i);
        }
        return result;
    }

    /**
     * 列表转换成二进制表示
     * Elements of req_skills and people[i] are (respectively) distinct.
     * Every skill in people[i] is a skill in req_skills.
     */
    private static void preprocess(String[] req_skills, List<List<String>> people) {
        map = new HashMap<>();
        p = new ArrayList<>();

        int k = 1;
        for (int i = 0; i < req_skills.length; i++) {
            map.put(req_skills[i], k);
            k = k << 1;
        }
        c = k - 1;

        for (int i = 0; i < people.size(); i++) {
            int v = 0;
            for (String s : people.get(i)) {
                int skill = map.get(s);
                v = v | skill;
            }
            p.add(v);
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new String[]{"java", "nodejs", "reactjs"});
        param.add(Arrays.asList(
                Arrays.asList("java"),
                Arrays.asList("nodejs"),
                Arrays.asList("nodejs", "reactjs")));
        params.add(param);


        param = new ArrayList<>();
        param.add(new String[]{"algorithms", "math", "java", "reactjs", "csharp", "aws"});
        param.add(Arrays.asList(
                Arrays.asList("algorithms", "math", "java"),
                Arrays.asList("algorithms", "math", "reactjs"),
                Arrays.asList("java", "csharp", "aws"),
                Arrays.asList("reactjs", "csharp"),
                Arrays.asList("csharp", "math"),
                Arrays.asList("aws", "java")));
        params.add(param);

        Utils.testStaticMethod(SmallestSufficientTeam.class
                , new HashSet<String>() {
                    {
                        add("smallestSufficientTeam");
                    }
                }, params);
    }
}
