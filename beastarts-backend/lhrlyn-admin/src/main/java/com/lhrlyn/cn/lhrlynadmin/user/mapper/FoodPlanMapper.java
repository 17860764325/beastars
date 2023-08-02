package com.lhrlyn.cn.lhrlynadmin.user.mapper;

import com.lhrlyn.cn.lhrlynadmin.user.entity.FoodPlan;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.base.CurdMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* 减脂餐计划
*
* @author emlyn 
* @since 1.0.0 2023-07-31
*/
@Mapper
public interface FoodPlanMapper extends  com.baomidou.mybatisplus.core.mapper.BaseMapper<FoodPlan> {

    List<FoodPlan> page(Map<String, Object> params);

}