package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.entity.KeepTaskPlan;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.KeepTaskPlanMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.KeepTaskPlanService;
import com.lhrlyn.cn.lhrlynadmin.user.util.IdWorker;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 日目标完成表
 *
 * @author emlyn babamu@126.com
 * @since 1.0.0 2023-08-02
 */
@Service
public class KeepTaskPlanServiceImpl extends ServiceImpl<KeepTaskPlanMapper, KeepTaskPlan> implements KeepTaskPlanService {

    @Autowired
    private KeepTaskPlanMapper keepTaskPlanMapper;

    @Override
    public List<KeepTaskPlan> page(PageQuery query) {
        List<KeepTaskPlan> page = keepTaskPlanMapper.page(query);
        return page;
    }

    @Override
    public ObjectRestResponse add(List<KeepTaskPlan> data) {
        for (KeepTaskPlan datum : data) {
            IdWorker idWorker = new IdWorker(1, 1);
            long l = idWorker.nextId();
            datum.setId(System.currentTimeMillis() + DateUtil.format(datum.getCurrentDateToday(), "yyyyMMdd"));
            // 如果数据库村在改日期的数据那么就不经心新增
            List<KeepTaskPlan> list = keepTaskPlanMapper.selectByDate(DateUtil.format(datum.getCurrentDateToday(), "yyyy-MM-dd"));
            if (list.size() > 0) {
                continue;
            }
            int i = keepTaskPlanMapper.insert(datum);
        }
        return ObjectRestResponse.success("成功！");
    }

    @Override
    public ObjectRestResponse edit(KeepTaskPlan data) {


        int i = 0;
        try {
            if (BeanUtil.isNotEmpty(data.getCurrentDateToday())) {
                List<KeepTaskPlan> keepTaskPlans = keepTaskPlanMapper.selectByDate(DateUtil.format(data.getCurrentDateToday(), "yyyy-MM-dd"));
                if (CollUtil.isNotEmpty(keepTaskPlans)) {
                    data.setId(keepTaskPlans.get(0).getId());
                    keepTaskPlans.get(0).setIsPunchClock(data.getIsPunchClock());
                    keepTaskPlans.get(0).setPunchClockTime(data.getPunchClockTime());
                    i = keepTaskPlanMapper.updateByPrimaryKeyWithVersion(keepTaskPlans.get(0));
                }else {
                     return ObjectRestResponse.failed("没有查询到数据，请先编写计划");
                }
            }
        } catch (Exception e) {
            return ObjectRestResponse.failed();
        }
        if (i >= 1) {
            return ObjectRestResponse.success(data);
        } else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ObjectRestResponse delete(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            keepTaskPlanMapper.deleteById(s);
        }
        return ObjectRestResponse.success();
    }

}