package com.xxx.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.blog.domain.Article;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    public void addArticleCommentCount(Long id);

    public IPage<Article> listArticle(
                                    Page<Article> page,
                                    Long categoryId,
                                    Long tagId,
                                    String year,
                                    String month
    );

}
