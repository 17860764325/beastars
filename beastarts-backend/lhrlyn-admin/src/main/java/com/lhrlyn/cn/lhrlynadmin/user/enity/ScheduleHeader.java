package com.lhrlyn.cn.lhrlynadmin.user.enity;

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
    @Column(name = "date")
    private Date date;
    @Column(name = "is_ok")
    private String isOk;
    @Column(name = "crete_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;






}
