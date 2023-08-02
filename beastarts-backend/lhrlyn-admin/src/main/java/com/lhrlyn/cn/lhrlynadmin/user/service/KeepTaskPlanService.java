package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhrlyn.cn.lhrlynadmin.user.entity.KeepTaskPlan;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;

import java.util.List;
import java.util.Map;

/**
 * 日目标完成表
 *
 * @author emlyn babamu@126.com
 * @since 1.0.0 2023-08-02
 */
public interface KeepTaskPlanService extends IService<KeepTaskPlan> {

    List<KeepTaskPlan> page(PageQuery query);

    ObjectRestResponse add(KeepTaskPlan data);

    ObjectRestResponse edit(KeepTaskPlan data);

    ObjectRestResponse delete(String ids);
}