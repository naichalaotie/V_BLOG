package com.xxx.blog.controller;

import com.xxx.blog.common.aop.LogAnnotation;
import com.xxx.blog.common.cache.Cache;
import com.xxx.blog.domain.param.ArticleParam;
import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.domain.param.PageParams;
import com.xxx.blog.service.ArchiveService;
import com.xxx.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("articles")

public class ArticleController{

    @Autowired
    ArticleService articleService;

    @Autowired
    ArchiveService archiveService;

    @PostMapping("listArchives")
    public Result listArchive(){
        return archiveService.selectList();
    }

    @PostMapping("new")
//    @Cache(expire = 1 * 60 * 1000,name = "newArticle")
    public Result newArticle(){
        return Result.success(articleService.newArticles(6));
    }

    @PostMapping("/hot")
//    @Cache(expire = 1 * 60 * 1000,name = "hot")
    public Result hot(){
        return Result.success(articleService.hotArticle(6));
    }

    @PostMapping()
    @LogAnnotation(module="文章",operation="获取文章列表")
//    @Cache(expire = 1 * 60 * 1000,name = "articles")
    public Result articles(@RequestBody PageParams pageParam){
        System.out.println("controller层："+pageParam.getPage()+"+"+ pageParam.getPageSize());
         return articleService.listArticlePage(pageParam);
    }

    @PostMapping("view/{id}")
//    @Cache(expire = 1 * 60 * 1000,name = "articleBody")
    public Result selectArticleById(@PathVariable("id")Long id){
        return articleService.selectArticleById(id);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        System.out.println(1);
        return articleService.saveArticle(articleParam);
    }

    @PostMapping("search")
    public Result search(@RequestBody ArticleParam articleParam){
        System.out.println("zhixing====================");
        return articleService.selectSearch(articleParam.getSearch());
    }

    @PostMapping("{id}")
    public Result editSelect(@PathVariable("id") Long id){
        return articleService.selectArticleById(id);
    }

}
