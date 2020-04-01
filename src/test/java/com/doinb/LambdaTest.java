package com.doinb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class LambdaTest {


    public static void main(String[] args) {
        lambdaTest();
    }


    public static void lambdaTest() {
        LambdaTest lambdaTest = new LambdaTest();
        List<Student> list = lambdaTest.getStudents();
        List<Demo> demos = new ArrayList<>();
        // 原始数据
        System.out.println("原始数据 组装list<demo>*******************");
        demos = list.stream().map(student -> new Demo(student.getName(), student.getAge(), student.getSex())).collect(Collectors.toList());
        demos.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + "  性别 " + demo.getSex() + ",");
        });

        // 只取sex为0
        System.out.println("只取sex为0**************** 去重");
        List<Demo> demorm = demos.stream().filter(demo -> demo.getSex() == 0).distinct().collect(Collectors.toList());
        demorm.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + "  性别 " + demo.getSex() + ",");
        });
        System.out.println("只取sex为0**************** 非去重");
        demos.stream().filter(demo -> demo.getSex() == 0).collect(Collectors.toList())
                .forEach(demo -> System.out.println("年龄 " + demo.getAge() + "  性别 " + demo.getSex() + ","));

        // 筛选年龄大于12岁
        System.out.println("筛选年龄大于12岁的*************");
        List<Demo> demoFilter = demos.stream().filter(demo -> Integer.valueOf(demo.getAge()) > 12).collect(Collectors.toList());
        demoFilter.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + "  性别 " + demo.getSex() + ",");
        });

        // 排序
        System.out.println("年龄排序******************");
        // jdk1.8 特性 Comparator.comparing(Demo::getAge)是静态方法直接引用; Demo::getAge代表调用getAge方法
        List<Demo> demoSort = demos.stream().sorted(Comparator.comparing(Demo::getAge)).collect(Collectors.toList());
        demoSort.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + ",");
        });

        // 倒序
        System.out.println("年龄倒序****************");
        ArrayList<Demo> demoArray = (ArrayList<Demo>) demos;

        Comparator<Demo> comparator = Comparator.comparing(Demo::getAge);
        demos.sort(comparator.reversed());
        //or
        demos.sort(Comparator.comparing(Demo::getAge).reversed());

        demos.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + ",");
        });

        // 多条件正序
        System.out.println("多条件排序正序****************");
        demoArray.sort(Comparator.comparing(Demo::getSex).thenComparing(Demo::getAge));
        demoArray.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + "  性别 " + demo.getSex() + ",");
        });

        // 多条件倒序
        System.out.println("多条件排序倒序 sex  倒序****************");
        demoArray.sort(Comparator.comparing(Demo::getSex).reversed().thenComparing(Demo::getAge));
        demoArray.forEach(demo -> {
            System.out.println("年龄 " + demo.getAge() + "  性别 " + demo.getSex() + ",");
        });

        // 按照年龄分组
        System.out.println("根据age分组结果为Map****************");
        Map<String, List<Demo>> demoOder = demos.stream().collect(Collectors.groupingBy(Demo::getAge));
        System.out.println(demoOder);

        // 收集为map
        System.out.println("收集为map**************");
        // toMap有个重载方法，可以传入一个合并的函数来解决key冲突问题：(key1, key2) -> key2), 只是简单的使用后者覆盖前者来解决key重复问题。
        // toMap还有另一个重载方法，可以指定一个Map的具体实现，来收集数据：(key1, key2) -> key2, LinkedHashMap::new)
        // demos.stream().collect(Collectors.toMap(Demo::getAge, Function.identity(), (k1, k2) -> k2, HashMap::new))
        demos.stream().collect(Collectors.toMap(Demo::getName,Function.identity(), (k1, k2) -> k2, HashMap::new))
                .forEach((key, value) -> System.out.println(key + "=" + value));
    }

    private List<Student> getStudents() {
        List<Student> list = new ArrayList<>();
        list.add(new Student().setAge("12").setSex(0).setName("张三丰"));
        list.add(new Student().setAge("13").setSex(2).setName("赵无极"));
        list.add(new Student().setAge("11").setSex(1).setName("冯绍峰"));
        list.add(new Student().setAge("18").setSex(1).setName("杨幂"));
        list.add(new Student().setAge("20").setSex(0).setName("tfboy"));
        list.add(new Student().setAge("18").setSex(2).setName("李逵"));
        list.add(new Student().setAge("18").setSex(2).setName("黑人"));
        list.add(new Student().setAge("23").setSex(3).setName("tfboy"));
        return list;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    static class Demo {
        private String name;
        private String age;
        private Integer sex;
    }

    @Data
    @Accessors(chain = true)
    static class Student {
        private String name;
        private String age;
        private Integer sex;
    }
}
