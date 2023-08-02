package com.lhrlyn.cn.lhrlynadmin.user.mapper;

import com.lhrlyn.cn.lhrlynadmin.user.entity.Page;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.base.CurdMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 页面管理
 *
 * @author lihaoran
 * @date 2022/8/26 11:31
 */
@Mapper
public interface PageMapper extends CurdMapper<Page> {

    Integer getMaxPageCode();

    List<Page> getPagesByRoleId(@Param("roleId") String roleId);
}
