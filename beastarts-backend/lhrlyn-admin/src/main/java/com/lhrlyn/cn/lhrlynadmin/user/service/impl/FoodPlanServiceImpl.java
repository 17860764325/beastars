package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.entity.FoodPlan;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.FoodPlanMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.FoodPlanService;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 减脂餐计划
 *
 * @author emlyn 
 * @since 1.0.0 2023-07-31
 */
@Service
public class FoodPlanServiceImpl extends ServiceImpl<FoodPlanMapper, FoodPlan> implements FoodPlanService {

    @Autowired
    private FoodPlanMapper foodPlanMapper;

    @Override
    public List<FoodPlan> page(PageQuery query) {
        List<FoodPlan> page = foodPlanMapper.page(query);
        return page;
    }

    @Override
    public ObjectRestResponse add(FoodPlan data) {
        int insert = foodPlanMapper.insert(data);
        if (insert > 0) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ObjectRestResponse edit(FoodPlan data) {
        int update = foodPlanMapper.updateById(data);
        if (update > 0) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ObjectRestResponse delete(String ids) {
        List<String> idsList = java.util.Arrays.asList(ids.split(","));
        int i = foodPlanMapper.deleteBatchIds(idsList);
        if (i >= 1) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed();
        }
    }

}