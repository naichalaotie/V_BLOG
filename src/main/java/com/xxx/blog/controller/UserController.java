package com.xxx.blog.controller;

import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return userService.currentUser(token);
    }
}
