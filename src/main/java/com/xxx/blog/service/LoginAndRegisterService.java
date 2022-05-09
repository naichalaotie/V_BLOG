package com.xxx.blog.service;

import com.xxx.blog.domain.SysUser;
import com.xxx.blog.domain.vo.Result;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LoginAndRegisterService {
    public Result login(String account, String password);

    public Result logout(String authorization);

    public Result register(String account,String password,String nickname);

    public SysUser checkToken(String token);
}
