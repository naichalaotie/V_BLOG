package com.xxx.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private boolean success;

    private Integer code;

    private String msg;

    private Object data;


    public static Result success(Object data,String msg) {
        System.out.println(msg+"Result的输出");
        return new Result(true,200,msg,data);

    }

    public static Result success(Object data) {
        return new Result(true,200,"success",data);
    }
    public static Result fail(Integer code, String msg) {
        return new Result(false,code,msg,null);
    }
}