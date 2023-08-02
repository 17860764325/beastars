package com.lhrlyn.cn.lhrlynadmin.user.controller.system;

import com.lhrlyn.cn.lhrlynadmin.user.dto.PageDto;
import com.lhrlyn.cn.lhrlynadmin.user.dto.TreeDateDto;
import com.lhrlyn.cn.lhrlynadmin.user.entity.Page;
import com.lhrlyn.cn.lhrlynadmin.user.entity.User;
import com.lhrlyn.cn.lhrlynadmin.user.entity.VO.PageRoleVO;
import com.lhrlyn.cn.lhrlynadmin.user.controller.Controller;
import com.lhrlyn.cn.lhrlynadmin.user.service.impl.PageServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 页面管理
 *
 * @author lihaoran
 * @date 2022/8/26 11:25
 */
@RestController
@RequestMapping("/page")
public class PageController extends Controller {

    @Autowired
    private PageServiceImpl pageService;

     @Autowired
    private RedisTemplate redisTemplate;



    /**
     * @description: 获取所有页面的树型列表
     * @param:
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse<java.util.List<com.lhrlyn.cn.lhrlynadmin.user.dto.TreeDateDto>>
     * @author lhr
     * @date: 2022/8/26 16:13
     */
    @GetMapping("/treeList")
    public ObjectRestResponse<List<TreeDateDto>> getPageListTreeData(){
        return pageService.getPageListTreeData();
    }

    /**
     * @description: 根据code获取页面
     * @param:
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse<com.lhrlyn.cn.lhrlynadmin.user.enity.Page>
     * @author lhr
     * @date: 2022/8/26 17:55
     */
    @GetMapping("/getPageInfoByCode/{code}")
    public ObjectRestResponse<Page> getPageInfoByCode(@PathVariable("code") String code){
        return pageService.getPageInfoByCode(code);
    }

    /**
     * @description:  返回最大ID
     * @param:
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse<java.lang.Integer>
     * @author lhr
     * @date: 2022/8/26 20:14
     */
    @GetMapping("/getMaxPageCode")
    public ObjectRestResponse<Integer> getMaxPageCode(){
        return pageService.getMaxPageCode();
    }


    @GetMapping("/getPagesByRoleId/{roleId}")
    public ObjectRestResponse getPagesByRoleId(@PathVariable("roleId") String roleId){
        return pageService.getPagesByRoleId(roleId);
    }

    @GetMapping("/getAllPages")
    public ObjectRestResponse getAllPages(){
        return pageService.getAllPages();
    }

    @PostMapping("/saveRolePages")
    public ObjectRestResponse saveRolePages(@RequestBody PageRoleVO pageRoleVO,HttpServletRequest request){
         String token = request.getHeader("token");
        Object userDto = redisTemplate.opsForValue().get(token);
        User userDto1 = BeanCopyUtils.beanCopy(userDto, User.class);
        return pageService.saveRolePages(pageRoleVO,userDto1);
    }

    /**
     * @description: 新增页面
     * @param: pageDto
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse
     * @author lhr
     * @date: 2023/4/2 23:08
     */
    @PostMapping("/add")
    public ObjectRestResponse add(@RequestBody PageDto pageDto){
        return pageService.add(pageDto);
    }


    /**
     * @description: 修改原有页面配置
     * @param: pageDto
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse
     * @author lhr
     * @date: 2023/4/2 23:09
     */
     @PostMapping("/edit")
    public ObjectRestResponse edit(@RequestBody PageDto pageDto){
        return pageService.edit(pageDto);
    }


}
