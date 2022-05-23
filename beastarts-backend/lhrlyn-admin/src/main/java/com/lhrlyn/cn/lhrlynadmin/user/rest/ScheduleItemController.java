package com.lhrlyn.cn.lhrlynadmin.user.rest;

import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleItemDto;
import com.lhrlyn.cn.lhrlynadmin.user.service.ScheduleItemService;
import com.lhrlyn.cn.lhrlynadmin.user.service.impl.ScheduleItemServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduleItem")
public class ScheduleItemController {

    @Autowired
    private ScheduleItemServiceImpl scheduleItemService;

    @GetMapping("/getInfo/{id}")
    public ObjectRestResponse<ScheduleItemDto> getInfo(@PathVariable("id")String id ){
        ScheduleItemDto scheduleItemDto = scheduleItemService.getInfo(id);
        return ObjectRestResponse.success(scheduleItemDto);
    }

    @PostMapping("/saveInfo")
    public ObjectRestResponse saveInfo(@RequestBody ScheduleItemDto scheduleItemDto){
        return scheduleItemService.saveInfo(scheduleItemDto);
    }



}
