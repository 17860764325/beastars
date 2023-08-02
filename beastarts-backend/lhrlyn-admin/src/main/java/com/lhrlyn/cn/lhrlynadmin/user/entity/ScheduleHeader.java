package com.lhrlyn.cn.lhrlynadmin.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "schedule_header")
@Data
public class ScheduleHeader {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "date")
    private Date date;
    @Column(name = "is_ok")
    private String isOk;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;

    @Column(name = "remarks")
    private String remarks;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;






}
