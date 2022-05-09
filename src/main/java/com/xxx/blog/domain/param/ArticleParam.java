package com.xxx.blog.domain.param;

import com.xxx.blog.domain.vo.CategoryVo;
import com.xxx.blog.domain.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;

    private String search;
}
