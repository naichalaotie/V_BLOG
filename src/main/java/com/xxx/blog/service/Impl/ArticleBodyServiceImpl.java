package com.xxx.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.blog.domain.ArticleBody;
import com.xxx.blog.domain.vo.ArticleBodyVo;
import com.xxx.blog.mapper.ArticleBodyMapper;
import com.xxx.blog.service.ArticleBodyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleBodyServiceImpl extends ServiceImpl<ArticleBodyMapper,ArticleBody> implements ArticleBodyService {

    @Autowired
    ArticleBodyMapper articleBodyMapper;


    @Override
    public ArticleBodyVo selectArticleBodyById(long id) {
        LambdaQueryWrapper<ArticleBody> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleBody::getArticleId,id);
        lambdaQueryWrapper.last("limit 1");
        ArticleBody articleBody = articleBodyMapper.selectOne(lambdaQueryWrapper);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
//        articleBodyVo.setContent(articleBody.getContent());
        BeanUtils.copyProperties(articleBody,articleBodyVo);
        return articleBodyVo;
    }
}
