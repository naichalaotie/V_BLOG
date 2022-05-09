package com.xxx.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.blog.domain.ArticleBody;
import com.xxx.blog.domain.vo.ArticleBodyVo;

public interface ArticleBodyService extends IService<ArticleBody> {

    public ArticleBodyVo selectArticleBodyById(long id);

}
