package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;

import java.util.List;

public interface UserService {
    ObjectRestResponse<List<UserDto>> list();
}
