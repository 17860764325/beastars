package com.lhrlyn.cn.lhrlynadmin.user.controller;

import com.lhrlyn.cn.lhrlynadmin.user.entity.FoodPlan;
import com.lhrlyn.cn.lhrlynadmin.user.service.FoodPlanService;
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
* 减脂餐计划
*
* @author emlyn 
* @since 1.0.0 2023-07-31
*/
@RestController
@RequestMapping("user/foodPlan")
public class FoodPlanController extends Controller {

    @Autowired
    private FoodPlanService foodPlanService;

    @GetMapping("/page")
    public TableResultResponse<List<FoodPlan>> page(@RequestParam Map<String, Object> map) {
        PageQuery query = new PageQuery(map, map);
        Page<FoodPlan> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<FoodPlan> list = foodPlanService.page(query);
        TableResultResponse tableResultResponse = new TableResultResponse<>(result.getTotal(), list);
        return tableResultResponse;
    }

    @PostMapping("/add")
    public ObjectRestResponse add(@RequestBody FoodPlan data,HttpServletRequest request){
        return foodPlanService.add(data);
    }

    @PostMapping("/edit")
    public ObjectRestResponse edit(@RequestBody FoodPlan data) {
         return foodPlanService.edit(data);
    }

    @GetMapping("/delete/{ids}")
    public ObjectRestResponse delete(@PathVariable("ids") String ids) {
        return foodPlanService.delete(ids);
    }
}