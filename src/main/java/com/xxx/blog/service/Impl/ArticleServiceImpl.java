package com.xxx.blog.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.sun.org.apache.xpath.internal.objects.XNull;
import com.xxx.blog.domain.*;
import com.xxx.blog.domain.param.ArticleParam;
import com.xxx.blog.domain.param.PageParams;
import com.xxx.blog.domain.vo.ArticleVo;
import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.domain.vo.TagVo;
import com.xxx.blog.mapper.ArticleMapper;
import com.xxx.blog.mapper.ArticleTagMapper;
import com.xxx.blog.service.*;
import com.xxx.blog.utils.UserThreadLocal;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//@Transactional
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    TagService tagService;

    @Autowired
    UserService userService;

    @Autowired
    ArticleBodyService articleBodyService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ArticleTagMapper articleTagMapper;



    @Override
    public Result listArticlePage(PageParams pageParam) {
        Page<Article> page = new Page<>(pageParam.getPage(),pageParam.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(page,pageParam.getCategoryId(),pageParam.getTagId(),pageParam.getYear(),pageParam.getMonth());
        List<Article> articles = articleIPage.getRecords();
        List<Article> l = articleMapper.selectList(null);
        return Result.success(copyList(articles,true,true,false,false),String.valueOf(l.size()));
    }



    @Override
    public List<ArticleVo> newArticles(int limit) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getCreateDate);
        lambdaQueryWrapper.select(Article::getId,Article::getTitle);
        lambdaQueryWrapper.last("limit "+limit);
        List<Article> list = articleMapper.selectList(lambdaQueryWrapper);
        return copyList(list,false,false,false,false);
    }

    @Override
    public List<ArticleVo> hotArticle(int limit) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getViewCounts);
        lambdaQueryWrapper.select(Article::getId,Article::getTitle);
        lambdaQueryWrapper.last("limit "+limit);
        List<Article> list = articleMapper.selectList(lambdaQueryWrapper);
        return copyList(list,false,false,false,false);
    }

//    @Override
//    public Result listArticlePage(PageParams pageParam) {
//        System.out.println("service层"+pageParam.getPage()+"+"+pageParam.getPageSize());
//        Page<Article> page = new Page<>(pageParam.getPage(),pageParam.getPageSize());
//        //查询条件
//        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper();
//        lambdaQueryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//        Long category = pageParam.getCategoryId();
//        if(category!=null){
//            lambdaQueryWrapper.eq(Article::getCategoryId,category);
//        }
//        Long tag = pageParam.getTagId();
//        if(tag!=null){
//            List<Long> list = new ArrayList<>();
//            List<ArticleTag> articleTags = tagService.selectArticleTagById(tag);
//                for(ArticleTag articleTag : articleTags){
//                    list.add(articleTag.getArticleId());
//                }
//            if(list.size()>0){
//                lambdaQueryWrapper.in(Article::getId,list);
//            }
//
//        }
//        Page<Article> reArticle = articleMapper.selectPage(page,lambdaQueryWrapper);
//        List<Article> list =  reArticle.getRecords();
//        List<ArticleVo> resultList = copyList(list, true, true,false,false);
//        List<Article> l = articleMapper.selectList(null);
//        return Result.success(resultList,String.valueOf(l.size()));
////        return Result.success(resultList);
//    }

    @Override
    public void commentCountAdd(Long articleId) {
        articleMapper.addArticleCommentCount(articleId);
    }

    private List<ArticleVo> copyList(List<Article> list, boolean tag, boolean author, boolean articleBody, boolean category){
        List<ArticleVo> voList = new ArrayList<>();
        for (Article article : list){
            voList.add(copy(article,tag,author,articleBody,category));
        }
        return voList;
    }


    private ArticleVo copy(Article article,boolean tag,boolean author,boolean articleBody,boolean category){
        ArticleVo articleVo = new ArticleVo();


        if(tag){
            long articleId = article.getId();
            articleVo.setTags(tagService.selectTagByArticleId(articleId));
        }
        if(author){
            long authorId = article.getAuthorId();
            articleVo.setAuthor(userService.selectById(authorId));
        }
        if(articleBody){
            articleVo.setBody( articleBodyService.selectArticleBodyById(article.getId()));
        }
//        if(category){
//            articleVo.setCategorys(categoryService.selectCategoryById(article.getCategoryId()));
//        }
        if(category){
            articleVo.setCategory(categoryService.selectCategoryById(article.getCategoryId()));
        }
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setId(String.valueOf(article.getId()));
//        articleVo.setId(article.getId());;
//        articleVo.setAuthorId(article.getAuthorId().toString());
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        return articleVo;
    }

    @Override
    public Result selectArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        ArticleVo articlevo ;
        articlevo = copy(article,true,true,true,true);
        TreadService.updateArticleViewCount(articleMapper,article);
        return Result.success(articlevo);
    }

    @Override
    public Result selectSearch(String search) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Article::getId,Article::getTitle);
        lambdaQueryWrapper.orderByDesc(Article::getCommentCounts);
        lambdaQueryWrapper.like(Article::getTitle,search);
        List<Article> lists = articleMapper.selectList(lambdaQueryWrapper);
        return Result.success(copyList(lists,false,false,false,false));
    }

    @Override
    public Result saveArticle(ArticleParam articleParam) {
        boolean edit = false;
        Article article = new Article();

        if(articleParam.getId()!=null){
            edit = true;
//            article = new Article();
            article.setId(articleParam.getId());
            article.setTitle(articleParam.getTitle());
            article.setSummary(articleParam.getSummary());
            article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
            articleMapper.updateById(article);
        }else {
            SysUser sysUser = UserThreadLocal.get();

            article.setAuthorId(sysUser.getId());
            article.setSummary(articleParam.getSummary());
            article.setTitle(articleParam.getTitle());
            article.setCommentCounts(Article.Article_Common);
            article.setViewCounts(0);
            article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
            article.setWeight(0);
            article.setCreateDate(System.currentTimeMillis());
            articleMapper.insert(article);
        }

        List<TagVo> tagVos = articleParam.getTags();
        if(tagVos!=null){
            if(edit){
                //测试 更新操作会不会给
                LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(ArticleTag::getArticleId,articleParam.getId());
                articleTagMapper.delete(lambdaQueryWrapper);
            }
            for(TagVo tagVo : tagVos){
                ArticleTag tag = new ArticleTag();
                tag.setArticleId(article.getId());
                tag.setTagId(Long.parseLong(tagVo.getId()));
                articleTagMapper.insert(tag);
            }
        }

        ArticleBody articleBody = new ArticleBody();
        if(edit){
            articleBody.setArticleId(article.getId());
            articleBody.setContent(articleParam.getBody().getContent());
            articleBody.setContentHtml(articleParam.getBody().getContentHtml());
            System.out.println("-.-.-.-.-....---.-..--..-.-.-.--.-..--.-.-..-.-.-.-.-.-.-"+articleParam.getBody().getContentHtml());
            LambdaQueryWrapper<ArticleBody> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ArticleBody::getArticleId,article.getId());
            articleBodyService.update(articleBody,lambdaQueryWrapper);
        }else {
            articleBody.setArticleId(article.getId());
            articleBody.setContent(articleParam.getBody().getContent());
            articleBody.setContentHtml(articleParam.getBody().getContentHtml());
            articleBodyService.save(articleBody);
        }
        article.setBodyId(articleBody.getId());
        if(!edit){
            articleMapper.updateById(article);
        }
        Map<String,String> m = new HashMap<>();
        m.put("id",article.getId().toString());
        return Result.success(m);
    }
}
