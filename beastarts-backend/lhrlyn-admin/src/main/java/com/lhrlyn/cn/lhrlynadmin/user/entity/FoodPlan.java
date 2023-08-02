package com.lhrlyn.cn.lhrlynadmin.user.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 减脂餐计划
 *
 * @author emlyn 
 * @since 1.0.0 2023-07-31
 */
@Data
@TableName("food_plan")
public class FoodPlan {
	@TableId(type = IdType.ID_WORKER,value = "id")
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
	* 当前日期
	*/
	 @TableField(value = "current_date")
	private Date currentDate;

	/**
	* 早餐/breakfast，午餐/launch，晚餐/dinner？餐食类别
	*/
	 @TableField(value = "meals_type")
	private String mealsType;

	/**
	* 食物类别：蛋白质？膳食纤维？维生素？碳水
	*/
	 @TableField(value = "food_type")
	private String foodType;

	/**
	* 摄入数量
	*/
	 @TableField(value = "number")
	private BigDecimal number;

	/**
	* 单位编码
	*/
	 @TableField(value = "unit_code")
	private String unitCode;

	/**
	* 单位名称
	*/
	 @TableField(value = "unit_name")
	private String unitName;

}