package com.xxx.blog.service;

import com.xxx.blog.domain.SysUser;
import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.domain.vo.UserVo;

public interface UserService {

    public UserVo selectById(long authorId);

    public SysUser selectUser(String account,String password);

    public SysUser selectUserByAccount(String account);

    public Result currentUser(String token);

    public void creatUser(SysUser sysUser);

    public UserVo selectUserVoById(Long id);

}
