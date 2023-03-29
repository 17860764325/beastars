package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.lhrlyn.cn.lhrlynadmin.user.dto.DictDto;
import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleHeaderDto;
import com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.*;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.ScheduleHeaderMapper;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.UserMapper;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.UserRoleMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.ScheduleHeaderService;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleHeaderserviceImpl implements ScheduleHeaderService {

    @Autowired
    private ScheduleHeaderMapper scheduleHeaderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper roleMapper;

    @Override
    public List<ScheduleHeaderDto> page(PageQuery query, User user) {
        boolean flag = false;
        // 判断是否是管理员或者是总经理
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserid());
        List<UserRole> select = roleMapper.select(userRole);
        for (UserRole role : select) {
            if (role.getRoleId() == 1 || role.getRoleId() == 2) {
                flag = true;
            }
        }
        if (flag) {
            if (StrUtil.isNotEmpty((String)query.get("startDate"))) {
                query.put("startDate", DateUtil.format(DateUtil.beginOfDay(DateUtil.parse((String) query.get("startDate"))), DatePattern.NORM_DATETIME_FORMAT));

            }
            if (StrUtil.isNotEmpty((String)query.get("endDate"))){
                query.put("endDate", DateUtil.format(DateUtil.endOfDay(DateUtil.parse((String) query.get("endDate"))), DatePattern.NORM_DATETIME_FORMAT));
            }
            List<ScheduleHeader> page = scheduleHeaderMapper.page(query);
            List<ScheduleHeaderDto> scheduleHeaderDtos = BeanCopyUtils.listCopy(page, ScheduleHeaderDto.class);
            return scheduleHeaderDtos;
        } else {
            query.put("userId", user.getUserid());
            List<ScheduleHeader> page = scheduleHeaderMapper.page(query);
            List<ScheduleHeaderDto> scheduleHeaderDtos = BeanCopyUtils.listCopy(page, ScheduleHeaderDto.class);
            return scheduleHeaderDtos;
        }

    }

    @Override
    public ObjectRestResponse add(ScheduleHeaderDto scheduleHeaderDto, User user) {
        scheduleHeaderDto.setIsOk("2");
        scheduleHeaderDto.setCreateTime(new Date());
        scheduleHeaderDto.setUserId(user.getUserid().toString());
        scheduleHeaderDto.setUserName(user.getUsername());
        ScheduleHeader scheduleHeader = BeanCopyUtils.beanCopy(scheduleHeaderDto, ScheduleHeader.class);
        int insert = scheduleHeaderMapper.insert(scheduleHeader);
        if (insert > 0) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ObjectRestResponse delete(String ids) {
        int i = scheduleHeaderMapper.deleteByIds(ids);
        if (i >= 1) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ObjectRestResponse statusOk(String ids) {
        String[] split = ids.split(",");
        boolean b = true;
        for (String s : split) {
            ScheduleHeader scheduleHeader = new ScheduleHeader();
            scheduleHeader.setId(Long.valueOf(s));
            scheduleHeader.setIsOk("1");
            int i = scheduleHeaderMapper.updateByPrimaryKeySelective(scheduleHeader);
            if (i <= 0) {
                b = false;
            }
        }
        if (b) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ObjectRestResponse statusNo(String ids) {
        String[] split = ids.split(",");
        boolean b = true;
        for (String s : split) {
            ScheduleHeader scheduleHeader = new ScheduleHeader();
            scheduleHeader.setId(Long.valueOf(s));
            scheduleHeader.setIsOk("2");
            int i = scheduleHeaderMapper.updateByPrimaryKeySelective(scheduleHeader);
            if (i <= 0) {
                b = false;
            }
        }
        if (b) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ScheduleHeaderDto edit(String id, User user) {
        boolean f = false;
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserid());
        List<UserRole> select = roleMapper.select(userRole);
        for (UserRole role : select) {
            // 如果权限为user普通用户不能进行修改
            if (role.getRoleId() == 4) {
                f = true;
            }

        }
        if (f) {
            return null;
        }
        ScheduleHeader scheduleHeader = new ScheduleHeader();
        scheduleHeader.setId(Long.valueOf(id));
        ScheduleHeader scheduleHeader1 = scheduleHeaderMapper.selectOne(scheduleHeader);
        return BeanCopyUtils.beanCopy(scheduleHeader1, ScheduleHeaderDto.class);
    }

    @Override
    public boolean editDictDate(ScheduleHeaderDto scheduleHeaderDto) {
        ScheduleHeader scheduleHeader = BeanCopyUtils.beanCopy(scheduleHeaderDto, ScheduleHeader.class);
        int i = scheduleHeaderMapper.updateByPrimaryKey(scheduleHeader);
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }
}
