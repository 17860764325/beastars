package com.lhrlyn.cn.lhrlynadmin.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDto implements Serializable {
    private Long userid;
    private String username;
    private String name ;
    private String phone;
    private String email;
    private String password;
    private String about;
    private String isVoid;
    private String token;
    private List<String> role;
    private String backagegroundImg;
    private String userHeadImg;
}
