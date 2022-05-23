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
    private String dataAbout;
    private Date createTime;
    private Date updateTime;

    // pageSize 从多少页开始
    private Integer page;

    // startSize 从多少条数据开始
    private Integer limit;


}
