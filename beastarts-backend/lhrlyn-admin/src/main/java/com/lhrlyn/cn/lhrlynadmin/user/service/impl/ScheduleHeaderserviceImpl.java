package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleHeaderDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.ScheduleHeader;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.ScheduleHeaderMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.ScheduleHeaderService;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleHeaderserviceImpl implements ScheduleHeaderService {

    @Autowired
    private ScheduleHeaderMapper scheduleHeaderMapper;

    @Override
    public List<ScheduleHeaderDto> page(PageQuery query) {
        List<ScheduleHeader> page = scheduleHeaderMapper.page(query);
        List<ScheduleHeaderDto> scheduleHeaderDtos = BeanCopyUtils.listCopy(page, ScheduleHeaderDto.class);
        return scheduleHeaderDtos;
    }
}
