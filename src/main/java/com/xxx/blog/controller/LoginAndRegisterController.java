package com.xxx.blog.controller;

import com.xxx.blog.domain.param.LoginParam;
import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.service.LoginAndRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginAndRegisterController {

    @Autowired
    LoginAndRegisterService loginAndRegisterService;

    @PostMapping("register")
    public Result register(@RequestBody LoginParam loginParam){

        return loginAndRegisterService.register(loginParam.getAccount(),loginParam.getPassword(),loginParam.getNickname());

    }

    @PostMapping("login")
    public Result login(@RequestBody LoginParam loginParam){
        return loginAndRegisterService.login(loginParam.getAccount(),loginParam.getPassword());
    }
}
