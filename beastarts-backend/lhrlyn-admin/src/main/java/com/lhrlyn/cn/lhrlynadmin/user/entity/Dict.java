package com.lhrlyn.cn.lhrlynadmin.user.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "dict")
public class Dict {

    @Id
    @Column(name = "dict_code")
    private Long dictCode;

    @Column(name = "dict_label")
    private String dictLabel;

    @Column(name = "dict_value")
    private String dictValue;

    @Column(name = "dict_sort")
    private String dictSort;

    @Column(name = "dict_type")
    private String dictType;

    @Column(name = "is_default")
    private String isDefault;

    @Column(name = "status")
    private String status;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "remark")
    private String remark;


}
