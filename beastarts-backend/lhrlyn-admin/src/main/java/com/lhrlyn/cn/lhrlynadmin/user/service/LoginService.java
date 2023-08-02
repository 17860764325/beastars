package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto;
import com.lhrlyn.cn.lhrlynadmin.user.util.ResultData;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    ResultData checkUser(UserDto user, HttpServletRequest request);
    ResultData getUserInfo(String token);

    ResultData regist(UserDto user);
}
