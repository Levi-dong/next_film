package com.next.demo.film.levi.mylambda;

import com.google.common.collect.Lists;
import com.next.demo.film.dao.entity.FilmDetailT;
import io.swagger.models.auth.In;
import org.junit.Test;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * @Author Clearlove
 * @Date 2019/12/1 22:25
 * @Version 1.0
 */

public class MyLambdaTest {


//    public static Integer operation(Integer num, FunctionLambda fL){
//        return fL.getValue(num);
//    }

//    public String strHandler(String str, FunctionLambda fL){
//        return fL.getValue(str);
//    }

    public void op(Long l1,Long l2, FunctionLambda<Long,Long> fl){
        System.out.println(fl.getValue(l1,l2));
    }

    @Test
    public void test(){
//        String trimStr = strHandler("\t\t\t\t\t 南大托", (str) -> str.trim());
//        System.out.println(trimStr);
//
//        String upper = strHandler("abcdef", (str) -> str.toUpperCase());
//        System.out.println(upper);
//
//        String isnotnull = strHandler(null, (str) -> str==null?"0":str);
//        System.out.println(isnotnull);

    op(100L,200L,(x,y)-> x + y);
    op(100L,200L,(x,y)-> x * y);

    }

    /*
    内置接口 Consumer<T> void accept(T t)  消费型接口
            Supplier<T> T get() 供给型接口
            Function<T,R> R apply(T t) 函数型接口
            Predicate<T> boolean test(T t) 断言型接口
     */
    @Test
    public void consumerTest(){
        happy(10000,(m)-> System.out.println("输出消费金额"+m+"元"));
    }


    public void happy(double money, Consumer<Double> con){
        con.accept(money);
    }

    @Test
    public void supplierTest(){
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
        for (Integer num:numList) {
            System.out.println(num);
        }
    }

    public List<Integer> getNumList(int num, Supplier<Integer> sup){
        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }
        return list;
    }

    @Test
    public void functionTest(){
        String newstr = strHandler("\t\t\t\t 哄多你", (str) -> str.trim());
        System.out.println(newstr);

        String newstr2 = strHandler("\t\t\t\t 哄多你", (str) -> str.length()+"");
        System.out.println(newstr2);
    }

    public String strHandler(String str, Function<String ,String > fun){
        return fun.apply(str);
    }

    @Test
    public void predicateTest(){
        List<String> list = Arrays.asList("hello","levi","lambda","www","ok");
        List<String> stringList = filterStr(list, (s) -> s.length()==3);
        for (String str : stringList) {
            System.out.println(str);
        }
    }

    public List<String> filterStr(List<String> list , Predicate<String> pre){
        List<String> strList = Lists.newArrayList();
        for (String str:list) {
            if (pre.test(str)){
                strList.add(str);
            }
        }
        return strList;
    }

    /*
        方法引用
     */

    @Test
    public void test2(){
        BiPredicate<String,String > bp = (x,y)-> x.equals(y);
        System.out.println(bp.test("a", "a"));

        BiPredicate<String,String > bp1 = String::equals;
        System.out.println(bp1.test("a", "a"));


        Consumer<String > consumer = (x) -> System.out.println(x);
        consumer.accept("hello");

        Consumer<String > consumer2 = System.out::println;
        consumer2.accept("consumer2");

        FilmDetailT filmDetailT = new FilmDetailT();
        filmDetailT.setFilmEnName("yaoshen");
        filmDetailT.setFilmLength(6);

        Supplier<String> sup = () -> filmDetailT.getFilmEnName();
        System.out.println(sup.get());

        Supplier<Integer> sup2 = filmDetailT::getFilmLength;
        System.out.println(sup2.get());

        Comparator<Integer> com = (x, y)-> Integer.compare(x,y);
        System.out.println(com.compare(2, 1));

        Comparator<Integer> com2 = Integer::compare;
        System.out.println(com2.compare(-1, 2));

        Supplier<FilmDetailT> sup3 = ()-> new FilmDetailT();

        Supplier<FilmDetailT> sup4 = FilmDetailT::new;

        FilmDetailT filmDetailT1 = sup3.get();
        System.out.println(filmDetailT1);

        Function<Integer,String[]> fun = (x) -> new String[x];
        String[] strs = fun.apply(5);
        System.out.println(strs.length);

        Function<Integer,String[]> fun2 = String[]::new;
        String[] apply = fun2.apply(50);
        System.out.println(apply.length);
    }

    /**
     * stream
     */

    @Test
    public void streamTest(){
        List<String > list = Lists.newArrayList();
        Stream<String> stream = list.stream();

        String[] str = new String[5];
        Stream<String> stream1 = Arrays.stream(str);

        Stream<String> stream2 = Stream.of("aa", "bb", "cc");

        Stream<Integer> iterate = Stream.iterate(0, (x) -> x + 3);
        //iterate.limit(10).forEach(System.out::println);

        //Stream.generate(()-> Math.random()*10).limit(10).forEach(System.out::println);


        List<Employee> employees = Arrays.asList(
                new Employee("张三",18,9999.99),
                new Employee("李四",58,5555.55),
                new Employee("王五",26,3333.33),
                new Employee("赵六",36,6666.66),
                new Employee("田七",12,8888.88),
                new Employee("田七",12,8888.88),
                new Employee("田七",12,8888.88)
        );

//        Stream<Employee> employeeStream = employees.stream()
//                .filter((e) ->{
//                    System.out.println("Stream API");
//                    return e.getAge() < 25;
//                });
//        employeeStream.forEach(System.out::println);

//        Stream<Employee> employeeStream1 =  employees.stream()
//                                                     .filter(e->{
//                                                         System.out.println("短路");
//                                                       return e.getSalary()<4300;
//                                                     } )
//                                                     .limit(2);
//        employeeStream1.forEach(System.out::println);
//        System.out.println("===========================");

        employees.stream()
                 .filter(e-> {
                     System.out.println("skip:"+e+":"+(e.getSalary() > 6000));
                    return e.getSalary() > 6000;
                 })
                 .skip(2)
                 .forEach(System.out::println);

        System.out.println("====================");

        employees.stream()
                .filter(e-> e.getSalary()>5000)
                .skip(2)
               // .distinct()
                .forEach(System.out::println);



    }

    @Test
    public void yihuoTest() {
        boolean a = false;
        boolean b = false;
        System.out.println(a^b);
        HashMap map = new HashMap();
    }
}

