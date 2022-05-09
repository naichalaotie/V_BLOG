package com.xxx.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String exceptionHandler(Exception e){
        e.printStackTrace();
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("erroPage.html");
        System.out.println("这里执行啦");
        return "哎呦~   好像出错啦";
    }
}
