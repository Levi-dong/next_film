package com.next.demo.film.controller.common;

import lombok.Data;

public final class TraceUtil {

    private TraceUtil(){}

    private  static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void initThread(String userId){
//        TraceUserInfo traceUserInfo = new TraceUserInfo();
        threadLocal.set(userId);
    }

    public static String getUserId(){
        return threadLocal.get();
    }

    /*@Data
    static class TraceUserInfo{
        private String userId;
        private String userName;
    }*/
}
