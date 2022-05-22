package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.github.pagehelper.Page;
import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleHeaderDto;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;

import java.util.List;

public interface ScheduleHeaderService {

    List<ScheduleHeaderDto> page(PageQuery query);
}
