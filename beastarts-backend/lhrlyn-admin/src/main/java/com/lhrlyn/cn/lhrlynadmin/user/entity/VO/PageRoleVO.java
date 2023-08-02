package com.lhrlyn.cn.lhrlynadmin.user.entity.VO;

import lombok.Data;

import java.util.List;

/**
 * @author lihaoran
 * @date 2023/3/31 19:03
 */
@Data
public class PageRoleVO {
    private String roleId;
    private List<String> pageIds;
}
