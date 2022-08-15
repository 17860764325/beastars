package com.lhrlyn.cn.lhrlynadmin.user.rest;

import com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto;
import com.lhrlyn.cn.lhrlynadmin.user.service.impl.UserServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lhr
 * @version 1.0
 * @description: 用户类
 * @date 2022/8/12 16:03
 */
@RestController
@RequestMapping("/user/sth")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;
    /**
     * @description: 返回用户的数据
     * @author lhr
     * @date 2022/8/12 16:28
     * @version 1.0
     */
    @GetMapping("/list")
    public ObjectRestResponse<List<UserDto>> list(){
        return userServiceImpl.list();
    }
}
