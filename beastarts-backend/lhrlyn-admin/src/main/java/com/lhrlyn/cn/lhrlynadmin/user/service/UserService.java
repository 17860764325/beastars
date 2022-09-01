package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.User;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;

import java.util.List;

public interface UserService {
    ObjectRestResponse<List<UserDto>> list();

    ObjectRestResponse saveUserHeadImg(String userHeadImg, User user);

    ObjectRestResponse saveUserBackageImg(String userBackGroundImg, User user);

    UserDto getUserInfo(UserDto userDto1);
}
