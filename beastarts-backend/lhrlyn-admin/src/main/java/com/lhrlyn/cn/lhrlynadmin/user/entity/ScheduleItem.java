package com.lhrlyn.cn.lhrlynadmin.user.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Table(name = "schedule_item")
@Data
public class ScheduleItem {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "header_id")
    private Long headerId;

    @Lob
    @Basic(fetch = LAZY)
    @Column(name = "data_about")
    private String dataAbout;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;


}
