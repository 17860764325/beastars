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
    @Column(name = "date")
    private Date date;
    @Column(name = "is_ok")
    private String isOk;
    @Column(name="is_other")
    private String isOther;
    @Column(name = "other_is_ok")
    private String otherIsOk;
    @Column(name = "crete_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;






}
