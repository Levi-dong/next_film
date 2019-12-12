package com.next.demo.film.levi.mylambda;



@FunctionalInterface
public interface FunctionLambda<T,R> {

    //public Integer getValue(Integer num);
    //void sayHi();

    //public String getValue(String str);

    public R getValue(T t1,T t2);
}
