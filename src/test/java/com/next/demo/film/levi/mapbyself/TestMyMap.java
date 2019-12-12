package com.next.demo.film.levi.mapbyself;

import net.minidev.json.JSONUtil;
import org.w3c.dom.ls.LSOutput;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * @Author Clearlove
 * @Date 2019/12/1 15:12
 * @Version 1.0
 */

public class TestMyMap {
    public static void main(String[] args) {
        SxtHashMap shm = new SxtHashMap();
        shm.put(10,"aa");
        shm.put(20,"bb");
        shm.put(30,"cc");
        shm.put(20,"sss");
        shm.put(36,"111");
        shm.put(52,"222");
        shm.put(68,"333");

        System.out.println(shm.toString());
        System.out.println(shm.get(68));

        System.out.println(Integer.highestOneBit(2));
        System.out.println(Integer.highestOneBit(3));
        System.out.println(Integer.highestOneBit(4));
        System.out.println(Integer.highestOneBit(5));
        System.out.println(Integer.highestOneBit(6));
        System.out.println(Integer.highestOneBit(7));
        System.out.println(Integer.highestOneBit(8));

//        for (int i = 0; i <100 ; i++) {
//            System.out.println(i+"---"+myHash(i,16));
//        }

        HashMap map = new HashMap(32);
//        String a = "Aa";
//        String b = "BB";
//        System.out.println(a.hashCode());
//        System.out.println(b.hashCode());
//
//        map.put(a,"sss");
//        map.put(b,"111");
//        map.put(52,"222");
//        map.put(68,"333");
//
//        System.out.println(map);

        int a = 65;
        System.out.println(a<<1);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world"+a);
            }
        };

        r.run();

        Runnable lambdar = () -> System.out.println("hello lambda"+a);

        lambdar.run();

        Consumer<String> com = x -> System.out.println(x);
        com.accept("hello lambda");

        Consumer<String> com2 = (x) -> System.out.println(x);
        com2.accept("hello lambda2");
    }
}
