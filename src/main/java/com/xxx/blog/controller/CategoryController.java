package com.xxx.blog.controller;

import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public Result listCategory(){
        return Result.success(categoryService.listCategory(false));
    }

//    /categorys/detail
    @GetMapping("detail")
    public Result detail(){
        return Result.success(categoryService.listCategory(true));
    }

//    /category/detail/{id}
    @GetMapping("detail/{id}")
    public Result detailById(@PathVariable("id")Long id){
        return Result.success(categoryService.selectCategoryById(id));
    }

}
