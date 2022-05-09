package com.xxx.blog.test;

import org.apache.commons.codec.digest.DigestUtils;

public class testMD5 {
    private static final String salt = " ";

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex(salt+"123"));
    }
}
