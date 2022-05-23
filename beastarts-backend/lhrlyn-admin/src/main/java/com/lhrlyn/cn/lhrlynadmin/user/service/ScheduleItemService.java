package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleItemDto;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.stereotype.Service;


public interface ScheduleItemService {
    ScheduleItemDto getInfo(String id);

    ObjectRestResponse saveInfo(ScheduleItemDto scheduleItemDto);
}
