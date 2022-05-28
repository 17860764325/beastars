package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import com.lhrlyn.cn.lhrlynadmin.user.dto.DictDto;
import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleHeaderDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.Dict;
import com.lhrlyn.cn.lhrlynadmin.user.enity.ScheduleHeader;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.ScheduleHeaderMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.ScheduleHeaderService;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public ObjectRestResponse add(ScheduleHeaderDto scheduleHeaderDto) {
        scheduleHeaderDto.setIsOk("2");
        scheduleHeaderDto.setCreateTime(new Date());
        ScheduleHeader scheduleHeader = BeanCopyUtils.beanCopy(scheduleHeaderDto, ScheduleHeader.class);
        int insert = scheduleHeaderMapper.insert(scheduleHeader);
        if (insert > 0){
            return ObjectRestResponse.success();
        }else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ObjectRestResponse delete(String ids) {
        int i = scheduleHeaderMapper.deleteByIds(ids);
        if (i>=1){
            return ObjectRestResponse.success();
        }else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ScheduleHeaderDto edit(String id) {
        ScheduleHeader scheduleHeader = new ScheduleHeader();
        scheduleHeader.setId(Long.valueOf(id));
        ScheduleHeader scheduleHeader1 = scheduleHeaderMapper.selectOne(scheduleHeader);
        return BeanCopyUtils.beanCopy(scheduleHeader1, ScheduleHeaderDto.class);
    }

    @Override
    public boolean editDictDate(ScheduleHeaderDto scheduleHeaderDto) {
        ScheduleHeader scheduleHeader = BeanCopyUtils.beanCopy(scheduleHeaderDto,ScheduleHeader.class);
        int i = scheduleHeaderMapper.updateByPrimaryKey(scheduleHeader);
        if (i > 0) {
            return true;
        }else {
            return false;
        }
    }
}
