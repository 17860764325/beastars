package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.entity.ElementFoodLog;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.ElementFoodLogMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.ElementFoodLogService;
import com.lhrlyn.cn.lhrlynadmin.user.util.IdWorker;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 每日摄取记录表
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2023-08-08
 */
@Service
public class ElementFoodLogServiceImpl extends ServiceImpl<ElementFoodLogMapper, ElementFoodLog> implements ElementFoodLogService {

    @Autowired
    private ElementFoodLogMapper elementFoodLogMapper;

    @Override
    public List<ElementFoodLog> page(PageQuery query) {
        List<ElementFoodLog> page = elementFoodLogMapper.page(query);
        return page;
    }

    @Override
    public ObjectRestResponse add(ElementFoodLog data) {
          IdWorker idWorker = new IdWorker(1, 1);
            long l = idWorker.nextId();
            data.setId(String.valueOf(l));
        int insert = elementFoodLogMapper.insert(data);
        if (insert >= 1) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ObjectRestResponse edit(ElementFoodLog data) {
        int i = 0;
        try {
         i = elementFoodLogMapper.updateByPrimaryKeyWithVersion(data);
        }catch (Exception e){
            return ObjectRestResponse.failed();
        }
        if (i >= 1 ) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed();
        }
    }

    @Override
    public ObjectRestResponse delete(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            elementFoodLogMapper.deleteById(s);
        }
        return ObjectRestResponse.success();
    }

}