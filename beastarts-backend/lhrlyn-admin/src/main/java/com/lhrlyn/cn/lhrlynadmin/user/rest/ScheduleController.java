package com.lhrlyn.cn.lhrlynadmin.user.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleHeaderDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.ScheduleHeader;
import com.lhrlyn.cn.lhrlynadmin.user.service.ScheduleHeaderService;
import com.lhrlyn.cn.lhrlynadmin.user.util.ResultData;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *      日程
 * @author lhr
 * @date  2022/5/5 09:20
 * @param
 * @return:
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController extends Controller {

    @Autowired
    private ScheduleHeaderService scheduleHeaderService;

    /**
     *  //TODO
     * @author lhr
     * @date  2022/5/5 23:34
     * @param
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.ResultData
     */
    @GetMapping ("/page")
    public TableResultResponse<List<ScheduleHeaderDto>> page(@RequestParam Map<String, Object> map) {
        PageQuery query = new PageQuery(map,map);
        Page<ScheduleHeaderDto> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<ScheduleHeaderDto> list = scheduleHeaderService.page(query);
        TableResultResponse tableResultResponse = new TableResultResponse<>(result.getTotal(),list);
        return tableResultResponse;
    }
}
