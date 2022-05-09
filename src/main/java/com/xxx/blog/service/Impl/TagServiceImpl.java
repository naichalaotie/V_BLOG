package com.xxx.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxx.blog.domain.ArticleTag;
import com.xxx.blog.domain.Tag;
import com.xxx.blog.domain.vo.TagVo;
import com.xxx.blog.mapper.ArticleTagMapper;
import com.xxx.blog.mapper.TagMapper;
import com.xxx.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    ArticleTagMapper articleTagMapper;

    @Override
    public List<TagVo> selectTagByArticleId(long articleId) {
         List<Tag> list = tagMapper.selectTagByArticleId(articleId);
        System.out.println(list);
        return copyList(list);
    }

    @Override
    public List<Long> selectHotTagIds(int limit) {
        return tagMapper.selectHotTagIds(limit);
    }

    @Override
    public List<TagVo> selectTagByIds(List<Long> tagIds) {
        return copyList(tagMapper.selectTagByIds(tagIds));
    }

    @Override
    public TagVo findTagById(Long id) {
        return copy(tagMapper.selectById(id));
    }

    @Override
    public List<TagVo> listTag(Boolean avatar) {
        LambdaQueryWrapper<Tag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(avatar){
            lambdaQueryWrapper.select(Tag::getId, Tag::getTagName,Tag::getAvatar);
        }else {
            lambdaQueryWrapper.select(Tag::getId, Tag::getTagName);
        }
        return copyList(tagMapper.selectList(lambdaQueryWrapper));
    }

    private List<TagVo> copyList(List<Tag> tags){
        List<TagVo> list = new ArrayList<>();
        for(Tag tag : tags){
            list.add(copy(tag));
        }
        System.out.println("tagService层的输出："+list);
        return list;
    }

    private TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        tagVo.setId(tag.getId().toString());
        return tagVo;
    }

    @Override
    public List<ArticleTag> selectArticleTagById(Long id) {
        LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleTag::getTagId,id);
        return articleTagMapper.selectList(lambdaQueryWrapper);
    }
}
