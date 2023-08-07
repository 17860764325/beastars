package com.lhrlyn.cn.lhrlynadmin.user.controller;

import com.lhrlyn.cn.lhrlynadmin.user.entity.KeepTaskPlan;
import com.lhrlyn.cn.lhrlynadmin.user.service.KeepTaskPlanService;
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
* 日目标完成表
*
* @author emlyn babamu@126.com
* @since 1.0.0 2023-08-02
*/
@RestController
@RequestMapping("user/plan")
public class KeepTaskPlanController extends Controller {

    @Autowired
    private KeepTaskPlanService keepTaskPlanService;

    @GetMapping("/page")
    public TableResultResponse<List<KeepTaskPlan>> page(@RequestParam Map<String, Object> map) {
        PageQuery query = new PageQuery(map, map);
        Page<KeepTaskPlan> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<KeepTaskPlan> list = keepTaskPlanService.page(query);
        TableResultResponse tableResultResponse = new TableResultResponse<>(result.getTotal(), list);
        return tableResultResponse;
    }

    @GetMapping("/getList")
     public ObjectRestResponse<List<KeepTaskPlan>> getList(@RequestParam Map<String, Object> map) {
         List<KeepTaskPlan> list = keepTaskPlanService.page(new PageQuery(map, map));
         return ObjectRestResponse.success(list);
     }

    @PostMapping("/add")
    public ObjectRestResponse add(@RequestBody List<KeepTaskPlan> data,HttpServletRequest request){
        return keepTaskPlanService.add(data);
    }

    @PostMapping("/edit")
    public ObjectRestResponse edit(@RequestBody KeepTaskPlan data) {
         return keepTaskPlanService.edit(data);
    }

    @GetMapping("/delete/{ids}")
    public ObjectRestResponse delete(@PathVariable("ids") String ids) {
        return keepTaskPlanService.delete(ids);
    }
}