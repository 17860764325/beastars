package com.lhrlyn.cn.lhrlynadmin.user.controller;

import com.lhrlyn.cn.lhrlynadmin.user.entity.ElementFoodLog;
import com.lhrlyn.cn.lhrlynadmin.user.service.ElementFoodLogService;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.github.pagehelper.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
* 每日摄取记录表
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2023-08-08
*/
@RestController
@RequestMapping("user/log")
public class ElementFoodLogController extends Controller {

    @Autowired
    private ElementFoodLogService elementFoodLogService;

    @GetMapping("/page")
    public TableResultResponse<List<ElementFoodLog>> page(@RequestParam Map<String, Object> map) {
        PageQuery query = new PageQuery(map, map);
        Page<ElementFoodLog> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<ElementFoodLog> list = elementFoodLogService.page(query);
        TableResultResponse tableResultResponse = new TableResultResponse<>(result.getTotal(), list);
        return tableResultResponse;
    }

    @GetMapping("/getList")
     public ObjectRestResponse<List<ElementFoodLog>> getList(@RequestParam Map<String, Object> map) {
         List<ElementFoodLog> list = elementFoodLogService.page(new PageQuery(map, map));
         return ObjectRestResponse.success(list);
     }

    @PostMapping("/add")
    public ObjectRestResponse add(@RequestBody ElementFoodLog data,HttpServletRequest request){
        return elementFoodLogService.add(data);
    }

    @PostMapping("/edit")
    public ObjectRestResponse edit(@RequestBody ElementFoodLog data) {
         return elementFoodLogService.edit(data);
    }

    @GetMapping("/delete/{ids}")
    public ObjectRestResponse delete(@PathVariable("ids") String ids) {
        return elementFoodLogService.delete(ids);
    }
}