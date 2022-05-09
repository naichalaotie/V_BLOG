package com.xxx.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.blog.domain.Tag;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    public List<Tag> selectTagByArticleId(long articleId);

    //查询几个最热标签的Id
    public List<Long> selectHotTagIds(int limit);

    //根据查询到的Id查询标签
    public List<Tag> selectTagByIds(@Param(value = "tagIds") List<Long> tagIds);

}
