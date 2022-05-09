package com.xxx.blog.service.Impl;

import com.alibaba.fastjson.JSON;
import com.xxx.blog.domain.SysUser;
import com.xxx.blog.domain.param.PageParams;
import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.service.LoginAndRegisterService;
import com.xxx.blog.service.UserService;
import com.xxx.blog.utils.ErrorCode;
import com.xxx.blog.utils.JWTUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class LoginAndRegisterServiceImpl implements LoginAndRegisterService {

    private static final String salt = " ";

    @Autowired
    private RedisTemplate<String,String> template ;

    @Autowired
    UserService userService;

    @Override
    public Result register(String account, String password, String nickname) {
        if(StringUtils.isBlank(account)||StringUtils.isBlank(password)||StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        String pass = DigestUtils.md5Hex(salt+password);
        SysUser sysUser = userService.selectUserByAccount(account);
        if(sysUser == null){
            SysUser user = new SysUser();
            user.setAccount(account);
            user.setPassword(pass);
            user.setNickname(nickname);
            user.setCreateDate(System.currentTimeMillis());
            user.setLastLogin(System.currentTimeMillis());
            user.setAvatar("/static/user/user_6.png");
            user.setAdmin(0); //1 为true
            user.setDeleted(0); // 0 为false
            user.setSalt("");
            user.setStatus("");
            user.setEmail("");
            userService.creatUser(user);

            //测试事务用 成功回滚
//            int a = 10/0;

            String token = JWTUtils.createToken(user.getId());
            template.opsForValue().set(token,JSON.toJSONString(user),1,TimeUnit.DAYS);

            return Result.success(token);
        }
        return Result.fail(ErrorCode.USER_HAVE.getCode(),ErrorCode.USER_HAVE.getMsg());
    }

    @Override
    public Result login(String account, String password) {
        if(StringUtils.isBlank(account)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        if(StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        String pwd =  DigestUtils.md5Hex(salt+password);
        SysUser sysUser = userService.selectUser(account,pwd);
        if(sysUser==null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),"用户名或密码错误");
        }
        String token = JWTUtils.createToken(sysUser.getId());
        //设置过期时间为一天
        System.out.println("登陆成功");

        template.opsForValue().set(token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public Result logout(String authorization) {
        template.delete(authorization);
        return Result.success(null);
    }

    @Override
    public SysUser checkToken(String token) {

        Map<String,Object> map = JWTUtils.checkToken(token);
        if(map==null){
            return null;
        }
        String userJson = template.opsForValue().get(token);
        if(StringUtils.isBlank(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson,SysUser.class);
        return sysUser;
    }
}
