package com.lhrlyn.cn.lhrlynadmin.user.mapper;

import com.lhrlyn.cn.lhrlynadmin.user.enity.ScheduleHeader;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.base.CurdMapper;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScheduleHeaderMapper extends CurdMapper<ScheduleHeader>, BaseMapper<ScheduleHeader>, com.baomidou.mybatisplus.core.mapper.BaseMapper<ScheduleHeader> {

    List<ScheduleHeader> page(Map<String, Object> params);
}
