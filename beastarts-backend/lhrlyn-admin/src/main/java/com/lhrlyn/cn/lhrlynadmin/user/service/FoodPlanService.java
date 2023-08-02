package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhrlyn.cn.lhrlynadmin.user.entity.FoodPlan;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;

import java.util.List;
import java.util.Map;

/**
 * 减脂餐计划
 *
 * @author emlyn 
 * @since 1.0.0 2023-07-31
 */
public interface FoodPlanService extends IService<FoodPlan> {

    List<FoodPlan> page(PageQuery query);

    ObjectRestResponse add(FoodPlan data);

    ObjectRestResponse edit(FoodPlan data);

    ObjectRestResponse delete(String ids);
}