package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.entity.Food;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.FoodMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.FoodService;
import com.lhrlyn.cn.lhrlynadmin.user.util.IdWorker;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 食物表
 *
 * @author emlyn 125587747092qq.com
 * @since 1.0.0 2023-07-31
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {

    @Autowired
    private FoodMapper foodMapper;

    @Override
    public List<Food> page(PageQuery query) {
        List<Food> page = foodMapper.page(query);
        return page;
    }

    @Override
    public ObjectRestResponse add(Food data) {
        IdWorker idWorker = new IdWorker(1, 1);
        long l = idWorker.nextId();
        data.setId(String.valueOf(l));
        data.setCreateTime(new Date());
        boolean save = save(data);
        if (save) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ObjectRestResponse edit(Food data) {
        int i = 0;
        try {
            i = foodMapper.updateByPrimaryKeyWithVersion(data);
        } catch (Exception e) {
            return ObjectRestResponse.failed();
        }
        if (i >= 1) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ObjectRestResponse delete(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            foodMapper.deleteById(s);
        }
        return ObjectRestResponse.success();
    }

}