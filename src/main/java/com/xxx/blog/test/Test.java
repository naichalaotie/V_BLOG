package com.xxx.blog.test;

import com.xxx.blog.domain.param.PageParams;
import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.service.ArticleService;
import com.xxx.blog.service.Impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class Test {

    @Autowired
    ArticleService articleService = new ArticleServiceImpl();

    public Result getArticles(){
        return articleService.listArticlePage(new PageParams());
    }


}
class test2{
    public static void main(String[] args) {
        Test a = new Test();
        if(a.articleService==null){
            System.out.println("null");
        }
        System.out.println(a.getArticles());

        }
}
