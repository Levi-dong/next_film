package com.next.demo.film.levi.mylambda;

import lombok.Data;

import java.util.Objects;

/**
 * @Author Clearlove
 * @Date 2019/12/2 23:25
 * @Version 1.0
 */

@Data
public class Employee {

    private String name;
    private Integer age;
    private double salary;

    public Employee(String name, Integer age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}
