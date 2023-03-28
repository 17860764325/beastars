package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleItemDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.ScheduleItem;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.ScheduleItemMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.ScheduleItemService;
import com.lhrlyn.cn.lhrlynadmin.user.util.IdWorker;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class ScheduleItemServiceImpl implements ScheduleItemService {

    @Autowired
    private ScheduleItemMapper scheduleItemMapper;

    @Value("${upload.imagePath}")
    private String uploadImagePath;

    @Override
    public ScheduleItemDto getInfo(String id) {
        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setHeaderId(Long.valueOf(id));
        ScheduleItem scheduleItemResult = scheduleItemMapper.selectOne(scheduleItem);
        ScheduleItemDto scheduleItemDto = BeanCopyUtils.beanCopy(scheduleItemResult, ScheduleItemDto.class);
        return scheduleItemDto;
    }

    /**
     * @description: 图片文件上传到指定的路径上
     * @param: file
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse
     * @author lhr
     * @date: 2023/3/28 11:27
     */
    public ObjectRestResponse imageUpload(MultipartFile file) {
        String fileName = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATETIME_FORMAT);
        if (file.getOriginalFilename().contains("jpg")) {
            fileName += ".jpg";
        } else if (file.getOriginalFilename().contains("png")) {
            fileName += ".png";

        } else if (file.getOriginalFilename().contains("jpeg")) {
            fileName += ".jpeg";

        }
        // 将文件保存到上传目录
        Path path = Paths.get(uploadImagePath + "/" + fileName);
        try {
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回文件路径
        return ObjectRestResponse.success(path.toString());
    }

    /**
     * @description: 详情保存方法
     * @param: scheduleItemDto
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse
     * @author lhr
     * @date: 2023/3/28 11:26
     */
    @Override
    public ObjectRestResponse saveInfo(ScheduleItemDto scheduleItemDto) {
        ScheduleItem scheduleItem = BeanCopyUtils.beanCopy(scheduleItemDto, ScheduleItem.class);
        scheduleItem.setCreateTime(new Date());

        ScheduleItem select = new ScheduleItem();
        select.setHeaderId(scheduleItem.getHeaderId());
        ScheduleItem selectResult = scheduleItemMapper.selectOne(select);

        int insert;
        if (selectResult == null) {
            ScheduleItem scheduleItem1 = new ScheduleItem();
            IdWorker idWorker = new IdWorker(1, 1);
            long id = idWorker.nextId();
            scheduleItem1.setId(id);
            scheduleItem1.setHeaderId(scheduleItem.getHeaderId());
            scheduleItem1.setDataAbout(scheduleItem.getDataAbout());
            scheduleItem1.setCreateTime(new Date());
            insert = scheduleItemMapper.insert(scheduleItem1);
        } else {
            selectResult.setHeaderId(scheduleItem.getHeaderId());
            selectResult.setDataAbout(scheduleItem.getDataAbout());
            selectResult.setUpdateTime(new Date());
            insert = scheduleItemMapper.updateByPrimaryKey(selectResult);
        }
        if (insert > 0) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed();
        }

    }
}
