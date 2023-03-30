package com.lhrlyn.cn.lhrlynadmin.user.enity.VO;

import lombok.Data;

import java.util.List;

/**
 * @author lihaoran
 * @date 2023/3/29 17:17
 */
@Data
public class UserRolesVO {
    private String userId;
    private List<String> roles;
}
