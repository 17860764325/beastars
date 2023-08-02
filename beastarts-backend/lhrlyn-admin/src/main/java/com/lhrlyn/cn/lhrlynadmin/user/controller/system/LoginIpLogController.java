package com.lhrlyn.cn.lhrlynadmin.user.controller.system;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhrlyn.cn.lhrlynadmin.user.dto.LoginIpLogDto;
import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleHeaderDto;
import com.lhrlyn.cn.lhrlynadmin.user.controller.Controller;
import com.lhrlyn.cn.lhrlynadmin.user.service.impl.LoginIpLogServiceIpml;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 登陆记录表视图层
 *
 * @author lihaoran
 * @date 2022-08-30 16:44:30
 */
@RestController
@RequestMapping("/loginIpLog")
public class LoginIpLogController extends Controller {

    @Autowired
    private LoginIpLogServiceIpml loginIpLogServiceIpml;

    /**
     * @description: 分页查询，登陆记录
     * @param:
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse<com.lhrlyn.cn.lhrlynadmin.user.dto.LoginIpLogDto>
     * @author lhr
     * @date: 2022/8/30 16:55
     */
    @GetMapping("/page")
    public TableResultResponse<LoginIpLogDto> page(@RequestParam Map<String, Object> map){
        PageQuery query = new PageQuery(map,map);
        Page<ScheduleHeaderDto> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<LoginIpLogDto> list = loginIpLogServiceIpml.page(query);
        TableResultResponse tableResultResponse = new TableResultResponse<>(result.getTotal(),list);
        return tableResultResponse;
    }

}