package com.xxx.blog.service;

import com.xxx.blog.domain.param.CommentParam;
import com.xxx.blog.domain.vo.CommentVo;
import com.xxx.blog.domain.vo.Result;

import java.util.List;

public interface CommentService {
    public Result selectComment(Long id);

    public List<CommentVo> listCommentVo(Long id);

    public Result creatComment(CommentParam commentParam);
}
