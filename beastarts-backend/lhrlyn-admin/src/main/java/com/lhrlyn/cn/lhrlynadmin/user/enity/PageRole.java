package com.lhrlyn.cn.lhrlynadmin.user.enity;


import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 页面角色关联表
 *
 * @author lihaoran
 * @date 2022-08-26 13:54:53
 */
@Table(name = "page_role")
@Data
public class PageRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 页面编码
     */
    @Column(name = "page_code")
    private String pageCode;

    /**
     * 权限编码
     */
    @Column(name = "role_code")
    private String roleCode;
}
