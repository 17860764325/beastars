package com.lhrlyn.cn.lhrlynadmin.user.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 食物表
 *
 * @author emlyn 125587747092qq.com
 * @since 1.0.0 2023-07-31
 */
@Data
@TableName("food")
@Table(name =  "food")
public class Food {
    @Id
    @TableId(type = IdType.UUID, value = "id")
    @TableField(value = "id")
    private String id;

    @TableField(value = "create_by")
    private String createBy;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_by")
    private String updateBy;

    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 食物名称
     */
    @TableField(value = "food_name")
    private String foodName;

    /**
     * 食物编码
     */
    @TableField(value = "food_code")
    private String foodCode;

    /**
     * 食物类型
     */
    @TableField(value = "food_type")
    private String foodType;

    /**
     * 热量
     */
    @TableField(value = "quantity_of_heat")
    private BigDecimal quantityOfHeat;

    /**
     * 蛋白质
     */
    @TableField(value = "protein")
    private BigDecimal protein;

    /**
     * 脂肪
     */
    @TableField(value = "fat")
    private BigDecimal fat;


    /**
     * 数量（g）
     */
    @TableField(value = "number")
    private BigDecimal number;

    /**
     * 膳食纤维
     */
    @TableField(value = "dietary_fiber")
    private BigDecimal dietaryFiber;

    /**
     * 碳水
     */
    @TableField(value = "carbon_water")
    private BigDecimal carbonWater;/**
     * 碳水
     */
    @TableField(value = "starch")
    private BigDecimal starch;



}