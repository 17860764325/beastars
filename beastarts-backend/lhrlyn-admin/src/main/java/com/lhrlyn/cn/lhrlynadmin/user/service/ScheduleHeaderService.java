package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleHeaderDto;
import com.lhrlyn.cn.lhrlynadmin.user.entity.ScheduleHeader;
import com.lhrlyn.cn.lhrlynadmin.user.entity.User;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;

import java.util.List;
import java.util.Map;

public interface ScheduleHeaderService extends IService<ScheduleHeader> {

   TableResultResponse<List<ScheduleHeaderDto>> page(Map<String, Object> params, User userDto);

    ObjectRestResponse add(ScheduleHeaderDto scheduleHeaderDto,User user);

    ObjectRestResponse delete(String ids);

    ScheduleHeaderDto edit(String id,User user);

    boolean editDictDate(ScheduleHeaderDto scheduleHeaderDto);

    ObjectRestResponse statusOk(String ids);

    ObjectRestResponse statusNo(String ids);
}
