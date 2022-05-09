package com.xxx.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxx.blog.domain.Article;
import com.xxx.blog.mapper.ArticleMapper;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TreadService {
    @Async("taskExecutor")
    public static void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        Article article1 = new Article();
        article1.setViewCounts(article.getViewCounts()+1);

        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getId,article.getId());
        lambdaQueryWrapper.eq(Article::getViewCounts,article.getViewCounts());

        articleMapper.update(article1,lambdaQueryWrapper);
    }
}
