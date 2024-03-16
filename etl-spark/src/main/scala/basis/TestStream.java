package basis;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestStream {
    public static void main(String[] args) {
        int[] arr = {10, 2, 3};
//        String s = largestNumber(arr);
//        System.out.println(s);

        IntStream stream = Arrays.stream(arr);
        Stream<String> stringStream = stream.mapToObj(String::valueOf);

        System.out.println(stringStream);

    }

    public static String largestNumber(int[] nums) {
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(nums)
                .mapToObj(String::valueOf)
                .sorted((s1, s2) -> (s2 + s1).compareTo(s1 + s2))
                .forEach(stringBuilder::append);
        return stringBuilder.charAt(0) == '0' ? "0" : stringBuilder.toString();
    }
}
