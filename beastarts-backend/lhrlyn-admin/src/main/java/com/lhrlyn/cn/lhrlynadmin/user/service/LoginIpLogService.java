package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.lhrlyn.cn.lhrlynadmin.user.dto.LoginIpLogDto;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;

import java.util.List;

/**
 * @author lihaoran
 * @date 2022/8/30 16:51
 */
public interface LoginIpLogService {
    List<LoginIpLogDto> page(PageQuery query);
}
