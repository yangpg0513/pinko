package day20220116;


import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class Test01 {

    @Test
    public void test01() {
        // 准备测试数据
//        final ArrayList<User> users = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            users.add(User.builder().name(String.valueOf(i)).sex("男").age(11).build());
//        }
////        users.add(User.builder().name(String.valueOf(1)).sex("男").age(11).build());
//        // 得到转换后的Map
//        Map<String, Object> map = users.stream()
//                .collect(Collectors.toMap(k -> k.name + k.sex , v -> v.age));
//
//        // 遍历map
//        map.forEach((k, v) -> {
//            System.out.println(k + " " + v);
//        });
    }

    @Test
    public void test02() {
        // 准备测试数据
        final ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            users.add(User.builder().id(String.valueOf(i)).sex("男").build());
        }
        // 得到转换后的Map
        Map<String, String> map = users.stream().parallel()
                .collect(Collectors.toMap(k -> k.sex, v -> v.id, (x, y) -> {
                    System.out.println("第一个值的value："+x);
                    System.out.println("第二个值的value："+y);
                    System.out.println("===========保留："+y);
                    return y;
                }));

        // 遍历map
        map.forEach((k, v) -> {
            System.out.println("最终结果：" + k + " " + v);
        });
    }

    @Test
    public void test03() {
        // 准备测试数据
        final ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            users.add(User.builder().id(String.valueOf(i)).sex("男").age(i*2).build());
            users.add(User.builder().id(String.valueOf(i)).sex("女").age(i*2+1).build());
        }

        // 一个参数的分组，按照字段值进行分组
        final Map<String, List<User>> map = users.stream().collect(Collectors.groupingBy(User::getSex));
        System.out.println(map);
        // 使用2个参数的分组
        final Map<String, Set<User>> map2 = users.stream().collect(Collectors.groupingBy(User::getSex,Collectors.toSet()));
        System.out.println(map2);
        // 使用3个参数的分组
        final Map<String, Set<User>> map3 = users.stream().collect(Collectors.groupingBy(User::getSex, TreeMap::new,Collectors.toSet()));
        System.out.println(map3);
    }

    @Test
    public void test04() {
        //随意给出一个三位数，打印显示它的个位数，十位数，百位数的值。
        //123
        //百位 123/100 取商1
        //十位 123/100 取余/10 取商
        //个位 123/100 取余/10 取余
        int num = 123;
        System.out.println(123 / 100);
        System.out.println(123 % 100 / 10);
        System.out.println(123 % 100 % 10);
    }

}
