package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.github.pagehelper.Page;
import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleHeaderDto;
import com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.User;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;

import java.util.List;

public interface ScheduleHeaderService {

    List<ScheduleHeaderDto> page(PageQuery query, User userDto);

    ObjectRestResponse add(ScheduleHeaderDto scheduleHeaderDto,User user);

    ObjectRestResponse delete(String ids);

    ScheduleHeaderDto edit(String id,User user);

    boolean editDictDate(ScheduleHeaderDto scheduleHeaderDto);

    ObjectRestResponse statusOk(String ids);

    ObjectRestResponse statusNo(String ids);
}
