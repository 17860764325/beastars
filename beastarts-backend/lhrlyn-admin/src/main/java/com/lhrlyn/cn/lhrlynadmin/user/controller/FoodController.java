package com.lhrlyn.cn.lhrlynadmin.user.controller;

import com.lhrlyn.cn.lhrlynadmin.user.entity.Food;
import com.lhrlyn.cn.lhrlynadmin.user.service.FoodService;
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
* 食物表
*
* @author emlyn 125587747092qq.com
* @since 1.0.0 2023-07-31
*/
@RestController
@RequestMapping("user/food")
public class FoodController extends Controller {

    @Autowired
    private FoodService foodService;

    @GetMapping("/page")
    public TableResultResponse<List<Food>> page(@RequestParam Map<String, Object> map) {
        PageQuery query = new PageQuery(map, map);
        Page<Food> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<Food> list = foodService.page(query);
        TableResultResponse tableResultResponse = new TableResultResponse<>(result.getTotal(), list);
        return tableResultResponse;
    }

     @GetMapping("/getList")
     public ObjectRestResponse<List<Food>> getList(@RequestParam Map<String, Object> map) {
         List<Food> list = foodService.page(new PageQuery(map, map));
         return ObjectRestResponse.success(list);
     }



    @PostMapping("/add")
    public ObjectRestResponse add(@RequestBody Food data,HttpServletRequest request){
        return foodService.add(data);
    }

    @PostMapping("/edit")
    public ObjectRestResponse edit(@RequestBody Food data) {
         return foodService.edit(data);
    }

    @GetMapping("/delete/{ids}")
    public ObjectRestResponse delete(@PathVariable("ids") String ids) {
        return foodService.delete(ids);
    }
}