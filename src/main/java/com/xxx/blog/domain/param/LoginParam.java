package com.xxx.blog.domain.param;

import lombok.Data;

@Data
public class LoginParam {
    private String account;
    private String password;
    private String nickname;
}
