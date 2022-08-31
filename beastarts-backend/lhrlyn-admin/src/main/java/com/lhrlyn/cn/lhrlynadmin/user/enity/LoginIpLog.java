package com.lhrlyn.cn.lhrlynadmin.user.enity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 登陆记录表
 *
 * @author lihaoran
 * @date 2022-08-30 16:44:30
 */
@Table(name = "login_ip_log")
@Data
public class LoginIpLog implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 登陆用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 登陆ip
     */
    @Column(name = "user_ip")
    private String userIp;

    @Column(name = "address")
    private String address;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private Date updateTime;
}
