package com.xxx.blog.controller;

import com.xxx.blog.domain.Article;
import com.xxx.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody
@Controller
public class TestController {
    @RequestMapping("test")
    public String test(){
        return "HELLO WORLD";
    }

    @Autowired
    ArticleService articleService;

    @RequestMapping("test2")
    public List<Article> test1(){
        return articleService.list();
    }
}
