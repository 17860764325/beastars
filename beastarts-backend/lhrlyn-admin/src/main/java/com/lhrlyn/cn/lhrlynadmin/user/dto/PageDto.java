package com.lhrlyn.cn.lhrlynadmin.user.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author lihaoran
 * @date 2023/4/2 23:02
 */
@Data
public class PageDto {
     
    private Long id;

    // 页面编码（纯编号）
     
    private String pageCode;

    // 页面的名称，title
     
    private String pageName;

    // 页面图标
     
    private String icon;

    // 路径（简）
     
    private String path;

    // 名称（简单）
     
    private String name;

    // 父节点
     
    private String pageParentCode;

    // 路由地址
     
    private String pagePath;

    // 路由参数
     
    private String pageQuery;

    // 权限标识
     
    private String pagePerms;

    // 页面类型 （M：目录，C：页面）
     
    private String pageType;

    // 页面状态（1：启用，0：禁用）
     
    private String pageStatus;

     
    private String createBy;

     
    private Date createTime;

     
    private String updateBy;

     
    private Date updateTime;

}
