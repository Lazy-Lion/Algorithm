package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 959: Regions Cut By Slashes
 *
 * In a N x N grid composed of 1 x 1 squares, each 1 x 1 square consists of a /, \, or blank space.  These characters divide the square into contiguous regions.
 *
 * (Note that backslash characters are escaped, so a \ is represented as "\\".)
 *
 * Return the number of regions.
 *
 * Example 1:
 *   Input:
 *   [
 *     " /",
 *     "/ "
 *   ]
 *   Output: 2
 *
 * Example 2:
 *   Input:
 *   [
 *     " /",
 *     "  "
 *   ]
 *   Output: 1
 *
 * Example 3:
 *   Input:
 *   [
 *     "\\/",
 *     "/\\"
 *   ]
 *   Output: 4
 *
 * Example 4:
 *   Input:
 *   [
 *     "/\\",
 *     "\\/"
 *   ]
 *   Output: 5
 *
 * Example 5:
 *   Input:
 *   [
 *     "//",
 *     "/ "
 *   ]
 *   Output: 3
 *
 * Note:
 *   1 <= grid.length == grid[0].length <= 30
 *   grid[i][j] is either '/', '\', or ' '.
 */
public class RegionsCutBySlashes {
    private static final char LEFT_SLASH = '/';
    private static final char RIGHT_SLASH = '\\';
    private static final char BLANK_SLASH = ' ';

    public static int regionsBySlashes(String[] grid) {
        return 0;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new String[]{" /", "/ "});
        params.add(param);

        param = new ArrayList<>();
        param.add(new String[]{" /", "  "});
        params.add(param);

        param = new ArrayList<>();
        param.add(new String[]{"\\/", "/\\"});
        params.add(param);

        param = new ArrayList<>();
        param.add(new String[]{"/\\", "\\/"});
        params.add(param);

        param = new ArrayList<>();
        param.add(new String[]{"//", "/ "});
        params.add(param);

        param = new ArrayList<>();
        param.add(new String[]{"\\/\\ ", " /\\/", " \\/ ", "/ / "});
        params.add(param);

        Utils.testStaticMethod(RegionsCutBySlashes.class
                , new HashSet<String>() {
                    {
                        add("regionsBySlashes");
                    }
                }, params);
    }
}
