package com.lhrlyn.cn.lhrlynadmin.user.mapper;

import com.lhrlyn.cn.lhrlynadmin.user.entity.Food;
import com.lhrlyn.cn.lhrlynadmin.user.entity.ScheduleHeader;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.base.CurdMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* 食物表
*
* @author emlyn 125587747092qq.com
* @since 1.0.0 2023-07-31
*/
@Mapper
public interface FoodMapper extends CurdMapper<Food>,  com.baomidou.mybatisplus.core.mapper.BaseMapper<Food> {

    List<Food> page(Map<String, Object> params);

    int deleteById(String id);

    int updateById(Food data);

}