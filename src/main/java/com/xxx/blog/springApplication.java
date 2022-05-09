package com.xxx.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xxx.blog.mapper")
public class springApplication {
    public static void main(String[] args) {
        SpringApplication.run(springApplication.class,args);
        System.out.println("spring boot启动啦");
    }
}
