package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.entity.KeepTaskPlan;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.KeepTaskPlanMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.KeepTaskPlanService;
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
    public ObjectRestResponse add(KeepTaskPlan data) {
        boolean insert = save(data);
        if (insert) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ObjectRestResponse edit(KeepTaskPlan data) {
        int i = 0;
        try {
         i = keepTaskPlanMapper.updateByPrimaryKeyWithVersion(data);
        }catch (Exception e){
            return ObjectRestResponse.failed();
        }
        if (i >= 1 ) {
            return ObjectRestResponse.success();
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