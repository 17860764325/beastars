package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhrlyn.cn.lhrlynadmin.user.entity.ElementFoodLog;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;

import java.util.List;
import java.util.Map;

/**
 * 每日摄取记录表
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2023-08-08
 */
public interface ElementFoodLogService extends IService<ElementFoodLog> {

    List<ElementFoodLog> page(PageQuery query);

    ObjectRestResponse add(ElementFoodLog data);

    ObjectRestResponse edit(ElementFoodLog data);

    ObjectRestResponse delete(String ids);
}