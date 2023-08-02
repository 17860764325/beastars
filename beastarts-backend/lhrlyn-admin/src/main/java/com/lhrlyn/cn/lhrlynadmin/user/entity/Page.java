package com.lhrlyn.cn.lhrlynadmin.user.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 *
 * @author lihaoran
 * @date 2022/8/26 11:33
 */
@Table(name = "page")
@Data
public class Page {

    @Id
    @Column(name = "id")
    private Long id;

    // 页面编码（纯编号）
    @Column(name = "page_code")
    private String pageCode;

    // 页面的名称，title
    @Column(name = "page_name")
    private String pageName;

    // 页面图标
    @Column(name = "icon")
    private String icon;

    // 路径（简）
    @Column(name = "path")
    private String path;

    // 名称（简单）
    @Column(name = "name")
    private String name;

    // 父节点
    @Column(name = "page_parent_code")
    private String pageParentCode;

    // 路由地址
    @Column(name = "page_path")
    private String pagePath;

    // 路由参数
    @Column(name = "page_query")
    private String pageQuery;

    // 权限标识
    @Column(name = "page_perms")
    private String pagePerms;

    // 页面类型 （M：目录，C：页面）
    @Column(name = "page_type")
    private String pageType;

    // 页面状态（1：启用，0：禁用）
    @Column(name = "page_status")
    private String pageStatus;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private Date updateTime;




}
