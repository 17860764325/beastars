package com.lhrlyn.cn.lhrlynadmin.user.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
public class ScheduleHeaderDto {

    private Long id;

    private String name;

    private Date date;

    private String isOk;

    private String UserId;

    private String userName;

    private Date createTime;

    private Date updateTime;






}
