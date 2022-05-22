package com.lhrlyn.cn.lhrlynadmin.user.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
public class DictDto implements Serializable {

    @Id
    private Long dictCode;

    private String dictLabel;

    private String dictValue;

    private String dictSort;

    private String dictType;

    private String isDefault;

    private String status;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String remark;


}
