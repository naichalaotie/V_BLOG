package com.xxx.blog.controller;

import com.xxx.blog.domain.Comment;
import com.xxx.blog.domain.param.CommentParam;
import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    CommentService commentService;

//    /comments/create/change
    @PostMapping("create/change")
    public Result creatComment(@RequestBody CommentParam commentParam){
        return commentService.creatComment(commentParam);
    }

    @GetMapping("article/{id}")
    public Result selectComment(@PathVariable("id") Long id){

        return commentService.selectComment(id);
    }
}
