package com.xxx.blog.controller;

import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.domain.vo.TagVo;
import com.xxx.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("detail/{id}")
    public Result detailById(@PathVariable("id") Long id){
        return Result.success(tagService.findTagById(id));
    }

    @GetMapping("detail")
    public Result detail(){
        return Result.success(tagService.listTag(true));
    }

    @GetMapping
    public Result returnTags(){
        return Result.success(tagService.listTag(false));
    }

    @GetMapping("/hot")
    public Result tags(){
        List<Long> tagIds = tagService.selectHotTagIds(6);
//        System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[L是空么 ："+tagIds);
        List<TagVo> list = tagService.selectTagByIds(tagIds);
        return Result.success(list);
    }
}
