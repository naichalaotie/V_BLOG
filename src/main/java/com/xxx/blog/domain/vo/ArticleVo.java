package com.xxx.blog.domain.vo;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    //是否置顶
    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    //作者名字
    private UserVo author;

    /**
     * 作者id
     */

//    private String authorId;


    private ArticleBodyVo body;

    //标签
    private List<TagVo> tags;

//    private List<CategoryVo> categorys;
    private CategoryVo category;

}