package com.next.demo.film.test;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.next.demo.film.test.bucket.TokenBucket;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TokenBucketTest {

    @Test
    public void bucketTest() throws InterruptedException {
        TokenBucket tokenBucket = new TokenBucket();
        for (int i = 0; i <=20 ; i++) {
            Thread.sleep(10);
            tokenBucket.hasToken();
        }

    }

    @Test
    public void rateLimitTest(){
        RateLimiter rateLimiter = RateLimiter.create(0.5);
        //字节
        //System.out.println("第一次："+rateLimiter.acquire(5));
        //System.out.println("第二次："+rateLimiter.acquire(2));
       // System.out.println("第三次："+rateLimiter.acquire(1));
        //System.out.println("第四次："+rateLimiter.acquire(3));


        Map map = new HashMap();
        System.out.println(map.size());
        System.out.println(map.entrySet().size());

        int h = 21546554;
        int length = 16;

        System.out.println(h&(length-1));
        System.out.println(h%length);

        String msg = "444";
        System.out.println(msg.hashCode());

        String msg1 = "444";
        System.out.println(msg1.hashCode());

        map.put(msg,"2323");
        System.out.println(map);
        map.put(msg1,"1112");
        System.out.println(map);

        System.out.println(msg.hashCode()&15);
        System.out.println(msg1.hashCode()&15);
        Integer a = 22;
        System.out.println(a.hashCode());
    }

}
