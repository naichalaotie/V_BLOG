package com.xxx.blog.service;

import com.xxx.blog.domain.ArticleTag;
import com.xxx.blog.domain.Tag;
import com.xxx.blog.domain.vo.TagVo;

import java.util.List;

public interface TagService{
    public List<TagVo> selectTagByArticleId(long articleId);

    public List<Long> selectHotTagIds(int limit);

    public List<TagVo> selectTagByIds(List<Long> tagIds);

    public List<TagVo> listTag(Boolean avatar);

    public TagVo findTagById(Long id);

    public List<ArticleTag> selectArticleTagById(Long id);
}
