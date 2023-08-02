package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhrlyn.cn.lhrlynadmin.user.entity.Food;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;

import java.util.List;
import java.util.Map;

/**
 * 食物表
 *
 * @author emlyn 125587747092qq.com
 * @since 1.0.0 2023-07-31
 */
public interface FoodService extends IService<Food> {

    List<Food> page(PageQuery query);

    ObjectRestResponse add(Food data);

    ObjectRestResponse edit(Food data)  ;

    ObjectRestResponse delete(String ids);
}