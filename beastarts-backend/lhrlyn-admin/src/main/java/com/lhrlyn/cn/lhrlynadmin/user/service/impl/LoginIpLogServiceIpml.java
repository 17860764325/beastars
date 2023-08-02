package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import com.lhrlyn.cn.lhrlynadmin.user.dto.LoginIpLogDto;
import com.lhrlyn.cn.lhrlynadmin.user.entity.LoginIpLog;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.LoginIpLogMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.LoginIpLogService;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihaoran
 * @date 2022/8/30 16:51
 */
@Service
public class LoginIpLogServiceIpml implements LoginIpLogService {

    @Autowired
    private LoginIpLogMapper loginIpLogMapper;

    @Override
    public List<LoginIpLogDto> page(PageQuery query) {
        List<LoginIpLog> page = loginIpLogMapper.page(query);
        List<LoginIpLogDto> loginIpLogDtos = BeanCopyUtils.listCopy(page, LoginIpLogDto.class);
        return loginIpLogDtos;
    }
}
