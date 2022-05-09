package com.xxx.blog.controller;

import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.service.LoginAndRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    LoginAndRegisterService loginAndRegisterService;
    @GetMapping
    public Result logout(@RequestHeader("Authorization") String authorization){

        return loginAndRegisterService.logout(authorization);
    }
}
