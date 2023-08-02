package com.lhrlyn.cn.lhrlynadmin.user.mapper;

import com.lhrlyn.cn.lhrlynadmin.user.entity.Role;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.base.CurdMapper;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper extends CurdMapper<Role> {
        List<Role> page(PageQuery query);

        List<Role>  getCharacterByUserId(@Param("id") String id);
}
