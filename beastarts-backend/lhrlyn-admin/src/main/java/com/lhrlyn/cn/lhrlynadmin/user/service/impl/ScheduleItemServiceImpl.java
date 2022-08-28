package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleItemDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.ScheduleItem;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.ScheduleItemMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.ScheduleItemService;
import com.lhrlyn.cn.lhrlynadmin.user.util.IdWorker;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ScheduleItemServiceImpl implements ScheduleItemService {

    @Autowired
    private ScheduleItemMapper scheduleItemMapper;
    @Override
    public ScheduleItemDto getInfo(String id) {
        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setHeaderId(Long.valueOf(id));
        ScheduleItem scheduleItemResult = scheduleItemMapper.selectOne(scheduleItem);
        ScheduleItemDto scheduleItemDto = BeanCopyUtils.beanCopy(scheduleItemResult, ScheduleItemDto.class);
        return scheduleItemDto;
    }

    @Override
    public ObjectRestResponse saveInfo(ScheduleItemDto scheduleItemDto) {
        ScheduleItem scheduleItem = BeanCopyUtils.beanCopy(scheduleItemDto, ScheduleItem.class);
        scheduleItem.setCreateTime(new Date());

        ScheduleItem select = new ScheduleItem();
        select.setHeaderId(scheduleItem.getHeaderId());
        ScheduleItem selectResult = scheduleItemMapper.selectOne(select);

        int insert ;
        if(selectResult== null){
            ScheduleItem scheduleItem1 = new ScheduleItem();
            IdWorker idWorker = new IdWorker(1,1);
            long id = idWorker.nextId();
            scheduleItem1.setId(id);
            scheduleItem1.setHeaderId(scheduleItem.getHeaderId());
            scheduleItem1.setDataAbout(scheduleItem.getDataAbout());
            scheduleItem1.setCreateTime(new Date());
             insert = scheduleItemMapper.insert(scheduleItem1);
        }else {
            selectResult.setHeaderId(scheduleItem.getHeaderId());
            selectResult.setDataAbout(scheduleItem.getDataAbout());
            selectResult.setUpdateTime(new Date());
            insert  = scheduleItemMapper.updateByPrimaryKey(selectResult);
        }
        if (insert > 0) {
            return ObjectRestResponse.success();
        }else {
            return ObjectRestResponse.failed();
        }

    }
}
