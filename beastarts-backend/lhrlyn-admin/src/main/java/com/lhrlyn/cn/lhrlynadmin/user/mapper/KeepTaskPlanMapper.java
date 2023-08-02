package com.lhrlyn.cn.lhrlynadmin.user.mapper;

import com.lhrlyn.cn.lhrlynadmin.user.entity.KeepTaskPlan;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.base.CurdMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* 日目标完成表
*
* @author emlyn babamu@126.com
* @since 1.0.0 2023-08-02
*/
@Mapper
public interface KeepTaskPlanMapper extends CurdMapper<KeepTaskPlan>,  com.baomidou.mybatisplus.core.mapper.BaseMapper<KeepTaskPlan> {

    List<KeepTaskPlan> page(Map<String, Object> params);

    int deleteById(String id);

}