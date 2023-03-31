package com.lhrlyn.cn.lhrlynadmin.user.mapper;


import com.lhrlyn.cn.lhrlynadmin.user.enity.PageRole;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.base.CurdMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 页面角色关联表
 *
 * @author lihaoran
 * @date 2022-08-26 13:54:53
 */
@Mapper
public interface PageRoleMapper extends CurdMapper<PageRole> {
    void deleteByUserId(@Param("roleId") String roleId);
}
