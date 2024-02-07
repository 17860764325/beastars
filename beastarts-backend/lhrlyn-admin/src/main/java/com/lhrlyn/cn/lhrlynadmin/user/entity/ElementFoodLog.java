package com.lhrlyn.cn.lhrlynadmin.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 每日摄取记录表
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2023-08-08
 */
@Data
@TableName("element_food_log")
public class ElementFoodLog implements Serializable {
	@Id
	@TableId(type = IdType.ID_WORKER,value = "id")

	@TableField(value = "id")
	@Column(name = "id")
	private String id;


	@TableField(value = "create_by")
	@Column(name = "create_by")
	private String createBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

	@TableField(value = "create_time")
	@Column(name = "create_time")
	private Date createTime;


	@TableField(value = "update_by")
	@Column(name = "update_by")
	private String updateBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

	@TableField(value = "update_time")
	@Column(name = "update_time")
	private Date updateTime;

	/**
	* 日期
	*/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")

	@TableField(value = "current_date_today")
	@Column(name = "current_date_today")
	private Date currentDateToday;

	/**
	* 热量
	*/

	@TableField(value = "quantity_of_heat")
	@Column(name = "quantity_of_heat")
	private BigDecimal quantityOfHeat;

	/**
	* 蛋白质
	*/

	@TableField(value = "protein")
	@Column(name = "protein")
	private BigDecimal protein;

	/**
	* 脂肪
	*/

	@TableField(value = "fat")
	@Column(name = "fat")
	private BigDecimal fat;

	/**
	* 膳食纤维
	*/

	@TableField(value = "dietary_fiber")
	@Column(name = "dietary_fiber")
	private BigDecimal dietaryFiber;

	/**
	* 碳水
	*/

	@TableField(value = "carbon_water")
	@Column(name = "carbon_water")
	private BigDecimal carbonWater;

	/**
	* 体重
	*/

	@TableField(value = "heavy")
	@Column(name = "heavy")
	private BigDecimal heavy;

	/**
	* 用户
	*/

	@TableField(value = "user_id_or_name")
	@Column(name = "user_id_or_name")
	private String userIdOrName;

}