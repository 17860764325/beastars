package com.lhrlyn.cn.lhrlynadmin.user.mapper;

import com.lhrlyn.cn.lhrlynadmin.user.entity.ElementFoodLog;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.base.CurdMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* 每日摄取记录表
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2023-08-08
*/
@Mapper
public interface ElementFoodLogMapper extends CurdMapper<ElementFoodLog>,  com.baomidou.mybatisplus.core.mapper.BaseMapper<ElementFoodLog> {

    List<ElementFoodLog> page(Map<String, Object> params);

    int deleteById(String id);

}