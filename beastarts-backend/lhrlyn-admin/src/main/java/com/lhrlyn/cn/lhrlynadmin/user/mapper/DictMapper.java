package com.lhrlyn.cn.lhrlynadmin.user.mapper;

import com.lhrlyn.cn.lhrlynadmin.user.entity.Dict;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.base.CurdMapper;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface DictMapper extends CurdMapper<Dict> {

    List<Dict> selectDictByType(@Param("type") String type);

    List<Dict> page(PageQuery query);

    Dict check(@Param("type") String type, @Param("value") String value);
}
