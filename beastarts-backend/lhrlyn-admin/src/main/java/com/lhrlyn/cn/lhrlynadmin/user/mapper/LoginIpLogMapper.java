package com.lhrlyn.cn.lhrlynadmin.user.mapper;


import com.lhrlyn.cn.lhrlynadmin.user.enity.LoginIpLog;
import com.lhrlyn.cn.lhrlynadmin.user.enity.ScheduleHeader;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.base.CurdMapper;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 登陆记录表
 *
 * @author lihaoran
 * @date 2022-08-30 16:44:30
 */
@Mapper
public interface LoginIpLogMapper extends CurdMapper<LoginIpLog> {
    List<LoginIpLog> page(PageQuery query);
}
