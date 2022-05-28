package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.github.pagehelper.Page;
import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleHeaderDto;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;

import java.util.List;

public interface ScheduleHeaderService {

    List<ScheduleHeaderDto> page(PageQuery query);

    ObjectRestResponse add(ScheduleHeaderDto scheduleHeaderDto);

    ObjectRestResponse delete(String ids);

    ScheduleHeaderDto edit(String id);

    boolean editDictDate(ScheduleHeaderDto scheduleHeaderDto);
}
