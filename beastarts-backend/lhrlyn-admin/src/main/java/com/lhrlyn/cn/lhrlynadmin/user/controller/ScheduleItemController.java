package com.lhrlyn.cn.lhrlynadmin.user.controller;

import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleItemDto;
import com.lhrlyn.cn.lhrlynadmin.user.service.impl.ScheduleItemServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    /**
     * @description: 上传图片将图片上传到值钉的文件加，然后将图片的路径保存到数据库中
     * @param: file
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse
     * @author lhr
     * @date: 2023/3/28 11:35
     */
    @PostMapping("/uploadImage")
    public ObjectRestResponse uploadImage(@RequestParam("img") MultipartFile file){
        return scheduleItemService.imageUpload(file);
    }


    @PostMapping("/saveInfo")
    public ObjectRestResponse saveInfo(@RequestBody ScheduleItemDto scheduleItemDto){
        return scheduleItemService.saveInfo(scheduleItemDto);
    }



}
