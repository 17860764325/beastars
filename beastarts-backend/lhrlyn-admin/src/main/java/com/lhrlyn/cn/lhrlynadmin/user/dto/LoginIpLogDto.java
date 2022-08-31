package com.lhrlyn.cn.lhrlynadmin.user.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
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
public class LoginIpLogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 登陆用户
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 登陆ip
     */
    private String userIp;

    private String address;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;
}
