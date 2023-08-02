package com.lhrlyn.cn.lhrlynadmin.user.controller;


import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleHeaderDto;
import com.lhrlyn.cn.lhrlynadmin.user.entity.User;
import com.lhrlyn.cn.lhrlynadmin.user.service.ScheduleHeaderService;
import com.lhrlyn.cn.lhrlynadmin.user.util.redis.RedisUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 日程
 * @author lhr
 * @date  2022/5/5 09:20
 * @z
 * @return:
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController extends Controller {

    @Autowired
    private ScheduleHeaderService scheduleHeaderService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     *  //TODO
     * @author lhr
     * @date  2022/5/5 23:34
     * @param
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.ResultData
     */
    @GetMapping ("/page")
    public TableResultResponse<List<ScheduleHeaderDto>> page(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String token = request.getHeader("token");
        User userInfoByToken = redisUtils.getUserInfoByToken(token);
        return scheduleHeaderService.page(map,userInfoByToken);
    }

    
    @PostMapping("/add")
    public ObjectRestResponse add(@RequestBody ScheduleHeaderDto scheduleHeaderDto,HttpServletRequest request){
        String token = request.getHeader("token");
        User userInfoByToken = redisUtils.getUserInfoByToken(token);
        return scheduleHeaderService.add(scheduleHeaderDto,userInfoByToken);
    }

    @GetMapping("/delete/{ids}")
    public ObjectRestResponse delete(@PathVariable("ids") String ids) {
        return scheduleHeaderService.delete(ids);
    }

    @GetMapping("/statusOk/{ids}")
    public ObjectRestResponse statusOk(@PathVariable("ids") String ids) {
        return scheduleHeaderService.statusOk(ids);
    }

    @GetMapping("/statusNo/{ids}")
    public ObjectRestResponse statusNo(@PathVariable("ids") String ids) {
        return scheduleHeaderService.statusNo(ids);
    }



    /** 
     * @description: 修改前的的权限校验 
     * @param: id
request 
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse<com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleHeaderDto> 
     * @author lhr
     * @date: 2023/3/28 19:42
     */ 
    @GetMapping("/edit/{id}")
    public ObjectRestResponse<ScheduleHeaderDto> edit(@PathVariable("id") String id, HttpServletRequest request) {
        String token = request.getHeader("token");
        User userInfoByToken = redisUtils.getUserInfoByToken(token);
        ScheduleHeaderDto scheduleHeaderDto = scheduleHeaderService.edit(id,userInfoByToken);
        if (scheduleHeaderDto == null){
            return ObjectRestResponse.failed("您的权限  不够不可以进行修改？ ");
        }
        return ObjectRestResponse.success(scheduleHeaderDto);
    }

    @PostMapping("/edit")
    public ObjectRestResponse edit(@RequestBody ScheduleHeaderDto scheduleHeaderDto) {
        boolean add = scheduleHeaderService.editDictDate(scheduleHeaderDto);
        if (add) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed("修改失败！");
        }
    }
}
