package com.lhrlyn.cn.lhrlynadmin.user.mapper;

import com.lhrlyn.cn.lhrlynadmin.user.entity.ScheduleHeader;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.base.CurdMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScheduleHeaderMapper extends CurdMapper<ScheduleHeader>,  com.baomidou.mybatisplus.core.mapper.BaseMapper<ScheduleHeader> {

    List<ScheduleHeader> page(Map<String, Object> params);
}
