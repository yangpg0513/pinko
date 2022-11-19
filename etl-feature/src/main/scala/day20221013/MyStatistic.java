package day20221013;



import com.alibaba.fastjson2.JSON;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 统计返回文件中包含的出现频率最高的3个Java关键字
 *
 * @author lishuzhen
 * @date 2021/4/15
 */
public class MyStatistic {

    /**
     * 定义正则表达式匹配单词
     */
    private static Pattern PATTERN = Pattern.compile("[a-zA-Z]+");

    /**
     * Java常用关键字数组
     */
    private static String[] JAVA_KEYWORD_ARRAY = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while"};


    /**
     * 返回结果为文件中包含的出现频率最高的3个Java关键字
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public String[] topJavaWords(String filePath) throws IOException {

        // 读取文件
        String str = readFileToString(filePath);

        // 使用正则表达式，判断是否是一个单词
        Matcher matcher = PATTERN.matcher(str);

        // 统计关键字出现的次数
        Map<String, Integer> map = timesKeyWord(matcher);
        System.out.println("统计结果 ->" + map);

        // 对统计结果排序
        List<Map.Entry<String, Integer>> sortList = sort(map);

        return top(sortList, 3);
    }

    /**
     * 读取文件
     *
     * @return
     */
    public static String readFileToString(String filePath) {
        String str = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            // 整个文件的内容 转成 String 类型
            str = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 判断是否是Java关键字
     *
     * @param keyword
     * @return
     */
    public static boolean isJavaKeyWord(String keyword) {
        return (Arrays.binarySearch(JAVA_KEYWORD_ARRAY, keyword) >= 0);
    }

    /**
     * 统计关键字出现的次数
     *
     * @param matcher
     * @return
     */
    public static Map<String, Integer> timesKeyWord(Matcher matcher) {
        // 用一个集合存放统计结果 key = 关键字 value = 出现的次数
        Map<String, Integer> map = new TreeMap<>();

        String word = "";
        // 出现次数 默认第一次
        int times = 1;
        while (matcher.find()) {
            word = matcher.group();
            System.out.println("拿到单词 -> " + word);
            // 判断是否使java关键字
            if (isJavaKeyWord(word)) {
                System.out.println(word + " 是关键字");
                // 如果包含该键，单词出现过
                if (map.containsKey(word)) {
                    // 得到单词出现的次数
                    times = map.get(word);
                    // 出现的次数 + 1
                    map.put(word, times + 1);
                } else {
                    // 否则单词第一次出现，添加到集合中，出现次数 = 1
                    map.put(word, times);
                }
            }
        }
        return map;
    }

    /**
     * 排序
     *
     * @param map
     * @return
     */
    public static List<Map.Entry<String, Integer>> sort(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            //降序排列
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return list;
    }

    /**
     * 获取出现次数高的关键字数组
     *
     * @param list
     * @param topNum
     * @return
     */
    public static String[] top(List<Map.Entry<String, Integer>> list, int topNum) {
        String[] result = new String[topNum];
        for (int i = 0; i < result.length && i < list.size(); i++) {
            // 取降序排列后的前三个 就是出现次数最多的三个关键字
            result[i] = list.get(i).getKey();
            System.out.println(list.get(i).getKey() + "出现了 " + list.get(i).getValue() + " 次");
        }
        return result;
    }


    public static void main(String[] args) {
        MyStatistic myStatistic = new MyStatistic();
        try {
            // 参数是任意一个文件的全路径，我这里写的是当前这个文件
            String[] strArray = myStatistic.topJavaWords("D:\\workspace\\pinko\\etl-feature\\src\\main\\scala\\day20221013\\keyword.txt");
            System.out.println("topJavaWords 执行结果 " + JSON.toJSONString(strArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}