package ssf.day12.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// Constants file for GenerateController
public class GenerateConstants {
    public static final String ATTR_NAME = "name";
    public static final String ATTR_COUNT = "count";
    public static final String ATTR_VALUES = "numFileList";
    public static final String ATTR_LIST = "list";

    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static List<Integer> toList(String csv) {
        // String[] -> List[int]
        /*
         * String[] nums = csv.split(",");
         * List<Integer> intList = new LinkedList<>();
         * for (String val: nums)
         * intList.add(Integer.parseInt(val));
         */
        return Arrays.asList(csv.split(","))
                .stream()
                .map(str -> Integer.parseInt(str))
                .toList();
    }

    public static List<Integer> generateRandom(int count) {
        List<Integer> nums = new LinkedList<>();
        for (int i = 0; i < 31; i++)
            nums.add(i);
        Collections.shuffle(nums);
        return nums.subList(0, count);
    }
}
