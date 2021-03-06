package com.xxx.blog.utils;

import com.xxx.blog.domain.SysUser;
import com.xxx.blog.handler.LoginInterceptor;

public class UserThreadLocal {
    private UserThreadLocal (){}

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}
