package com.lhrlyn.cn.lhrlynadmin.user.enity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "schedule-item")
@Data
public class ScheduleItem {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "header_id")
    private Long headerId;
    @Column(name = "date")
    private Date date;
    @Column(name = "name")
    private String name;
    @Column(name = "about")
    private String about;
    @Column(name = "time")
    private String time;
    @Column(name = "is_ok")
    private String isOk;
    @Column(name = "other_name")
    private String otherName;
    @Column(name = "other_about")
    private String otherAbout;
    @Column(name = "other_time")
    private String otherTime;
    @Column(name = "other_is_ok")
    private String otherIdOk;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "crete_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;











}
