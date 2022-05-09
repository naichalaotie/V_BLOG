package com.xxx.blog.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxx.blog.domain.SysUser;
import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.domain.vo.UserVo;
import com.xxx.blog.mapper.UserMapper;
import com.xxx.blog.service.LoginAndRegisterService;
import com.xxx.blog.service.UserService;
import com.xxx.blog.utils.ErrorCode;
import com.xxx.blog.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,String> template ;
    @Autowired
    LoginAndRegisterService loginAndRegisterService;

    @Override
    public void creatUser(SysUser sysUser) {
        userMapper.insert(sysUser);
    }

    @Override
    public SysUser selectUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(SysUser::getAccount,account);
        lambdaQueryWrapper.last("limit 1");
        return userMapper.selectOne(lambdaQueryWrapper);
    }

    //登陆用
    @Override
    public SysUser selectUser(String account, String password) {

        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(SysUser::getAccount,account);
        lambdaQueryWrapper.eq(SysUser::getPassword,password);
        lambdaQueryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        lambdaQueryWrapper.last("limit 1");

        return userMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public Result currentUser(String token) {
        SysUser sysUser = loginAndRegisterService.checkToken(token);
        if(sysUser!=null)
        return Result.success(sysUser);
        return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
    }

    @Override
    public UserVo selectById(long authorId) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userMapper.selectById(authorId),userVo);
        return userVo;
    }

    @Override
    public UserVo selectUserVoById(Long id) {
        SysUser sysUser =  userMapper.selectById(id);
        UserVo userVo = new UserVo();
//        userVo.setId(sysUser.getId().toString());
        BeanUtils.copyProperties(sysUser,userVo);
        return userVo;
    }
}
