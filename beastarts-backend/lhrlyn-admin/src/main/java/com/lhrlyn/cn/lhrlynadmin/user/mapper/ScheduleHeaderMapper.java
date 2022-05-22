package com.lhrlyn.cn.lhrlynadmin.user.mapper;

import com.lhrlyn.cn.lhrlynadmin.user.enity.ScheduleHeader;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.base.CurdMapper;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleHeaderMapper extends CurdMapper<ScheduleHeader> {

    List<ScheduleHeader> page(PageQuery query);
}
