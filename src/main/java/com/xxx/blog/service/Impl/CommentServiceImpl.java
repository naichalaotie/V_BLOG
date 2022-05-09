package com.xxx.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxx.blog.domain.Comment;
import com.xxx.blog.domain.SysUser;
import com.xxx.blog.domain.param.CommentParam;
import com.xxx.blog.domain.vo.CommentVo;
import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.domain.vo.UserVo;
import com.xxx.blog.mapper.CommentMapper;
import com.xxx.blog.service.ArticleService;
import com.xxx.blog.service.CommentService;
import com.xxx.blog.service.UserService;
import com.xxx.blog.utils.UserThreadLocal;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserService userService ;

    @Autowired
    ArticleService articleService;

    @Override
    public Result selectComment(Long id) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getArticleId,id);
        lambdaQueryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(lambdaQueryWrapper);
        return Result.success(copyList(comments));
    }


    @Override
    public List<CommentVo> listCommentVo(Long id) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getParentId,id);
        List<Comment> list =  commentMapper.selectList(lambdaQueryWrapper);
        if(list.size()==0) return null;
        return copyList(list);
    }


    @Override
    public Result creatComment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
//        ==============================================2022.05.02=============================
//        System.out.println("creatComment"+commentParam.getToUserId());
        comment.setAuthorId(sysUser.getId());

        Long l = commentParam.getParent();
        if(l==null||l==0){
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
//        if(l!=null){
//            comment.setParentId(commentParam.getParent());
//        }
        comment.setParentId(l == null ? 0 : l);

        Long l1 = commentParam.getToUserId();
//        if(l1!=null){
//            comment.setToUid(l1);
//        }
        comment.setToUid(l1 == null ? 0 : l1);
        System.out.println(commentParam.getArticleId()+"```````````````````````````````````````````````````");
        commentMapper.insert(comment);
        //成功添加评论后 评论数+1
        articleService.commentCountAdd(commentParam.getArticleId());
//        测试事务用
//        int a = 10/0;
        return Result.success(copy(comment));
    }

    private List<CommentVo> copyList(List<Comment> list){
        List<CommentVo> commentVos = new ArrayList<>();
        for(Comment comment : list){
            commentVos.add(copy(comment));
        }
        return commentVos;
    }


    private CommentVo copy(Comment comment){
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
//        commentVo.setId(String.valueOf(comment.getId()));
        commentVo.setId(String.valueOf(comment.getId()));

        //时间格式化
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        UserVo userVo = userService.selectUserVoById(comment.getAuthorId());
        commentVo.setAuthor(userVo);
        int l =  comment.getLevel();
        //评论是可能会有评论的
        //评论只有两级
        if(1==l) {
            List<CommentVo> commentVos = listCommentVo(comment.getId());
            if(commentVos!=null) {
                commentVo.setChildrens(commentVos);
            }
        }
        if(l>1){
            UserVo toUserVo = userService.selectUserVoById(comment.getToUid());
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }
}
