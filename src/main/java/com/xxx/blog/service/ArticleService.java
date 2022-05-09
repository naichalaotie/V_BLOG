package com.xxx.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.blog.domain.Article;
import com.xxx.blog.domain.param.ArticleParam;
import com.xxx.blog.domain.vo.ArticleVo;
import com.xxx.blog.domain.param.PageParams;
import com.xxx.blog.domain.vo.Result;

import java.util.List;


public interface ArticleService extends IService<Article> {

    public Result listArticlePage(PageParams pageParam);

    public List<ArticleVo> hotArticle(int limit);

    public List<ArticleVo> newArticles(int limit);

    public Result selectArticleById(Long id);

    public void commentCountAdd(Long articleId);

    public Result saveArticle(ArticleParam articleParam);

    public Result selectSearch(String search);
}
