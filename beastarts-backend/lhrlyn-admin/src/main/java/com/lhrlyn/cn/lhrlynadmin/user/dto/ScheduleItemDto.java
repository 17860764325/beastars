package com.lhrlyn.cn.lhrlynadmin.user.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
public class ScheduleItemDto {


    private Long id;
    private Long headerId;
    private Date date;
    private String name;
    private String about;
    private String time;
    private String  isOk;
    private String otherName;
    private String otherAbout;
    private String otherTime;
    private String otherIdOk;
    private String remarks;
    private Date createTime;
    private Date updateTime;

    // pageSize 从多少页开始
    private Integer page;

    // startSize 从多少条数据开始
    private Integer limit;


}
